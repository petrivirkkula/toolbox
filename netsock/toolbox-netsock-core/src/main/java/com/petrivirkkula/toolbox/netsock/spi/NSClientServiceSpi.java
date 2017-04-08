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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.netsock.NSClientDestination;
import com.petrivirkkula.toolbox.netsock.NSClientService;
import com.petrivirkkula.toolbox.netsock.NSRuntimeException;


/**
 * Service SPI class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class NSClientServiceSpi extends NSServiceSpi implements NSClientService
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id$";

	/**
	 * Logger
	 */
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSClientServiceSpi.class);

	static {
		LOGGER.loaded(RCSID, NSClientServiceSpi.class);
	}


	private Set<NSConnectionSpi> connections = new HashSet<NSConnectionSpi>();
	
	
	private final EventManager eventManager;

	private final ArrayList<NSClientDestinationSpi> destinations;
	
	public NSClientServiceSpi(EventManager eventManager) {
		this.eventManager = eventManager;
		this.destinations = new ArrayList<NSClientDestinationSpi>();
	}

	
	protected com.petrivirkkula.toolbox.logger.Logger getLogger() {
		return LOGGER;
	}
	
	protected EventManager getEventManager() {
		return eventManager;
	}


	protected Thread createServiceThread(Runnable runnable) {
		return new Thread(runnable, "ClientServiceThread");
	}




	
	protected void executeServiceStateChange() {
		super.executeServiceStateChange();
		wakeupSelector();
	}



	protected void processSelectionKey(Selector selector, SelectionKey key) {
//		getLogger().trace(new SimpleLoggable("processSelectionKey: enter; key=" + key));
		Object attachment = key.attachment();
		NSConnectionSpi conn = (NSConnectionSpi) attachment;
		processSelectionKey(selector, key, conn);
	}


	protected void removeConnection(NSConnectionSpi connection) {
		synchronized(connections) {
			connections.remove(connection);
		}
	}


	protected void registerChannels(Selector selector) throws InterruptedException, IOException {
		activateConnections(selector);
		
	}

	protected void unregisterChannels(Selector selector) throws InterruptedException {
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

	protected NSClientDestinationSpi createDestination(InetSocketAddress destination, InetSocketAddress source) {
		return new NSClientDestinationSpi(this, destination, source);
	}



	@Override
	public NSClientDestination addDestination(String host, int port)
			throws NSRuntimeException {
		return addDestination(new InetSocketAddress(host, port));
	}


	@Override
	public NSClientDestination addDestination(InetAddress inetAddr, int port)
			throws NSRuntimeException {
		return addDestination(new InetSocketAddress(inetAddr, port));
	}


	@Override
	public NSClientDestination addDestination(InetSocketAddress destination)
			throws NSRuntimeException {
		return addDestination(destination, null);
	}


	@Override
	public NSClientDestination addDestination(InetSocketAddress destination, InetSocketAddress source) throws NSRuntimeException {
		NSClientDestinationSpi dest = createDestination(destination, source);
		return addDestination(dest);
	}
	
	@Override
	public NSClientDestination addDestination(NSClientDestination destination) throws NSRuntimeException {
		synchronized(destinations) {
			destinations.add((NSClientDestinationSpi)destination);
		}
		return null;
	}


	@Override
	public boolean removeDestination(InetSocketAddress destination, InetSocketAddress source) throws NSRuntimeException {
		NSClientDestinationSpi dest = createDestination(destination, source);
		return removeDestination(dest);
	}


	@Override
	public boolean removeDestination(NSClientDestination destination) throws NSRuntimeException {
		synchronized(destinations) {
			for (int i = 0; i < destinations.size(); i++) {
				if (destinations.get(i).equals(destination)) {
					destinations.remove(i);
					return true;
				}
			}
		}
		return false;
	}


}
