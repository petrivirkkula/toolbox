/*
 *	Copyright (C) 2010-2016, Petri Virkkula.
 *
 *	This library is free software; you can redistribute
 *	it and/or modify it under the terms of Artistic License
 *	version 2.0. For more details, see the full text
 *	of the license in the file LICENSE.
 *
 *	Disclaimer of Warranty:
 *	THE PACKAGE IS PROVIDED BY THE COPYRIGHT HOLDER AND
 *	CONTRIBUTORS "AS IS' AND WITHOUT ANY EXPRESS OR IMPLIED
 *	WARRANTIES. THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT
 *	ARE DISCLAIMED TO THE EXTENT PERMITTED BY YOUR LOCAL LAW.
 *	UNLESS REQUIRED BY LAW, NO COPYRIGHT HOLDER OR CONTRIBUTOR
 *	WILL BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, OR
 *	CONSEQUENTIAL DAMAGES ARISING IN ANY WAY OUT OF THE USE
 *	OF THE PACKAGE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *	SUCH DAMAGE.
 */

package com.petrivirkkula.toolbox.netsock.spi;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.petrivirkkula.toolbox.logger.SimpleLoggable;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.netsock.NSRuntimeException;
import com.petrivirkkula.toolbox.netsock.NSServerListener;
import com.petrivirkkula.toolbox.netsock.NSServerService;
import com.petrivirkkula.toolbox.netsock.events.NSServerConnectionAcceptedEvent;
import com.petrivirkkula.toolbox.netsock.events.NSServerListenerAddedEvent;
import com.petrivirkkula.toolbox.netsock.events.NSServerListenerFailedEvent;
import com.petrivirkkula.toolbox.netsock.events.NSServerListenerRemovedEvent;


/**
 * Service SPI class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class NSServerServiceSpi extends NSServiceSpi implements NSServerService
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id: NSServerServiceSpi.java 142 2015-08-23 13:34:02Z petri $";

	/**
	 * Logger
	 */
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSServerServiceSpi.class);

	static {
		LOGGER.loaded(RCSID, NSServerServiceSpi.class);
	}

	private final transient Object listenerChangeMutex = new Object();
	
	private ArrayList<ListenerChange> listenerChanges = new ArrayList<ListenerChange>();

	private Map<InetSocketAddress,NSServerListenerSpi> activeListeners = new HashMap<InetSocketAddress,NSServerListenerSpi>();

	private Set<NSConnectionSpi> connections = new HashSet<NSConnectionSpi>();
	
	
	private final EventManager eventManager;


	
	public NSServerServiceSpi(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	
	protected com.petrivirkkula.toolbox.logger.Logger getLogger() {
		return LOGGER;
	}
	
	protected EventManager getEventManager() {
		return eventManager;
	}


	protected Thread createServiceThread(Runnable runnable) {
		return new Thread(runnable, "ServerServiceThread");
	}


	@Override
	public NSServerListener addListener(int port) throws NSRuntimeException {
		InetSocketAddress socketAddr;
		socketAddr = new InetSocketAddress(port);
		return addListener(socketAddr);
	}


	@Override
	public NSServerListener addListener(String host, int port) throws NSRuntimeException {
		InetSocketAddress socketAddr;
		try {
			socketAddr = new InetSocketAddress(InetAddress.getByName(host), port);
		} 
		catch (UnknownHostException e) {
			throw new NSRuntimeException(e);
		}
		return addListener(socketAddr);
	}


	@Override
	public NSServerListener addListener(InetAddress inetAddr, int port) throws NSRuntimeException {
		InetSocketAddress socketAddr = new InetSocketAddress(inetAddr, port);
		return addListener(socketAddr);
	}


	@Override
	public NSServerListener addListener(InetSocketAddress socketAddr) throws NSRuntimeException {
		NSServerListenerSpi listener = createListener(this, socketAddr);
		ListenerChange change = new ListenerChange(ListenerChangeType.ADD, listener);
		getLogger().debug(new SimpleLoggable("scheduling addition of new listener: " + socketAddr));
		synchronized(listenerChangeMutex) {
			listenerChanges.add(change);
			listenerChangeMutex.notifyAll();
		}
		return listener;
	}
	


	
	protected NSServerListenerSpi createListener(NSServerServiceSpi nsServerServiceSpi, InetSocketAddress socketAddr) {
		return new NSServerListenerSpi(this, socketAddr);
	}


	@Override
	public NSServerListener removeListener(int port) throws NSRuntimeException {
		InetSocketAddress socketAddr;
		socketAddr = new InetSocketAddress(port);
		return removeListener(socketAddr);
	}


	@Override
	public NSServerListener removeListener(String host, int port) throws NSRuntimeException {
		InetSocketAddress socketAddr;
		try {
			socketAddr = new InetSocketAddress(InetAddress.getByName(host), port);
		} 
		catch (UnknownHostException e) {
			throw new NSRuntimeException(e);
		}
		return removeListener(socketAddr);
	}


	@Override
	public NSServerListener removeListener(InetAddress inetAddr, int port) throws NSRuntimeException {
		InetSocketAddress socketAddr = new InetSocketAddress(inetAddr, port);
		return removeListener(socketAddr);
	}


	@Override
	public NSServerListener removeListener(InetSocketAddress socketAddr) throws NSRuntimeException {
		NSServerListenerSpi listener = createListener(this, socketAddr);
		ListenerChange change = new ListenerChange(ListenerChangeType.REMOVE, listener);
		getLogger().debug(new SimpleLoggable("scheduling removal of listener: " + socketAddr));
		synchronized(listenerChangeMutex) {
			listenerChanges.add(change);
			listenerChangeMutex.notifyAll();
		}
		return listener;
	}

	@Override
	public NSServerListener removeListener(NSServerListener listener) throws NSRuntimeException {
		ListenerChange change = new ListenerChange(ListenerChangeType.REMOVE, (NSServerListenerSpi)listener);
		getLogger().debug(new SimpleLoggable("scheduling removal of listener: " + listener.getSocketAddress()));
		synchronized(listenerChangeMutex) {
			listenerChanges.add(change);
			listenerChangeMutex.notifyAll();
		}
		return listener;
	}
	

	
	protected void executeServiceStateChange() {
		super.executeServiceStateChange();
		wakeupSelector();
	}



	protected void processSelectionKey(Selector selector, SelectionKey key) {
//		getLogger().trace(new SimpleLoggable("processSelectionKey: enter; key=" + key));
		Object attachment = key.attachment();
		if (attachment instanceof NSServerListenerSpi) {
			NSServerListenerSpi listener = (NSServerListenerSpi) attachment;
			NSConnectionSpi conn = null;
			try {
				SocketChannel socketChannel = listener.accept();
				conn = new NSServerConnectionSpi(this, socketChannel);
				conn.register(selector);
				synchronized(connections) {
					connections.add(conn);
				}
				getLogger().debug(new SimpleLoggable("new connection: " + conn));
				trigger(new NSServerConnectionAcceptedEvent(this, listener, conn));
			}
			catch(Exception ex) {
				getLogger().error(new SimpleLoggable(ex, "exception: " + ex));
				if (conn != null)
					connections.remove(conn);
				if (conn != null)
					try { conn.close(); } catch(Exception ignored) { ignored = null; }
				trigger(new NSServerListenerFailedEvent(this, listener, ex));
			}
		} else if (attachment instanceof NSConnectionSpi) {
			NSConnectionSpi conn = (NSConnectionSpi) attachment;
			processSelectionKey(selector, key, conn);
		}

	}


	protected void removeConnection(NSConnectionSpi connection) {
		synchronized(connections) {
			connections.remove(connection);
		}
	}


	protected void registerChannels(Selector selector) throws InterruptedException, IOException {
		activateRequestedListeners(selector);
		activateListeners(selector);
		activateConnections(selector);
		
	}

	protected void unregisterChannels(Selector selector) throws InterruptedException {
		activateRequestedListeners(selector);
		inactivateListeners(selector);
		inactivateConnections(selector);
	}

	private void activateConnections(Selector selector) throws InterruptedException, IOException {
		synchronized(connections) {
			for (NSConnectionSpi connection : connections) {
				activateConnection(selector, connection);
			}
		}
	}

	private void inactivateConnections(Selector selector) throws InterruptedException {
		synchronized(connections) {
			for (NSConnectionSpi connection : connections) {
				inactivateConnection(selector, connection);
			}
		}
	}


	private void activateRequestedListeners(Selector selector) throws InterruptedException {
//		getLogger().trace(new SimpleLoggable("activateRequestedListeners: enter"));
		synchronized(listenerChangeMutex) {
			while (isRunning() && listenerChanges.isEmpty() && activeListeners.isEmpty()) {
				listenerChangeMutex.wait();
//				break;
			}
			while (!listenerChanges.isEmpty()) {
				final ListenerChange change = listenerChanges.remove(0);
				getLogger().debug(new SimpleLoggable("processing " + change.getChangeType() + " for " + change.getListener()));
				switch(change.getChangeType()) {
				case REMOVE: {
					NSServerListenerSpi listener = activeListeners.get(change.getListener().getSocketAddress());
					if (listener != null) {
						listener.unregister(selector);
						activeListeners.remove(change.getListener().getSocketAddress());
						getLogger().debug(new SimpleLoggable("removed listener: " + change.getListener()));
						trigger(new NSServerListenerRemovedEvent(this, listener));
					} else {
						getLogger().debug(new SimpleLoggable("removal request for non-existing listener: " + change.getListener()));
					}
					break;
				}
				case ADD: {
					NSServerListenerSpi listener = activeListeners.get(change.getListener().getSocketAddress());
					if (listener != null) {
						getLogger().debug(new SimpleLoggable("kept existing listener: " + change.getListener()));
						break;
//						listener.unregister(selector);
//						activeListeners.remove(change.socketAddress);
//						getLogger().debug(new SimpleLoggable("removed listener: " + change.socketAddress));
//						trigger(new NSServerListenerRemovedEvent(this, listener));
					}
					listener = change.getListener();
					try {
						listener.register(selector);
						activeListeners.put(listener.getSocketAddress(), listener);
						getLogger().debug(new SimpleLoggable("added new listener: " + change.getListener().getSocketAddress()));
						trigger(new NSServerListenerAddedEvent(this, listener));
					}
					catch(Exception ex) {
						getLogger().error(new SimpleLoggable(ex, "failed to add listener " + change.getListener() + " : " + ex));
						trigger(new NSServerListenerFailedEvent(this, listener, ex));
					}
					break;
				}
				}
			}
		}
	}


	private void activateListeners(Selector selector) throws InterruptedException {
//		getLogger().trace(new SimpleLoggable("activateRequestedListeners: enter"));
		for (NSServerListenerSpi listener : activeListeners.values()) {
			try {
				listener.register(selector);
			}
			catch(IOException ex) {
				getLogger().error(new SimpleLoggable(ex, "exception: " + ex));
			}
		}
	}

	private void inactivateListeners(Selector selector) throws InterruptedException {
//		getLogger().trace(new SimpleLoggable("activateRequestedListeners: enter"));
		for (NSServerListenerSpi listener : activeListeners.values()) {
			try {
				listener.unregister(selector);
			}
			catch(Exception ex) {
				getLogger().error(new SimpleLoggable(ex, "exception: " + ex));
			}
		}
	}

}
