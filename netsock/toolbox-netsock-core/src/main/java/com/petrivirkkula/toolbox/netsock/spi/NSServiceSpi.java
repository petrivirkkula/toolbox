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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

import com.petrivirkkula.toolbox.logger.SimpleLoggable;
import com.petrivirkkula.toolbox.eventmgr.AbstractEventSource;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.netsock.NSService;
import com.petrivirkkula.toolbox.netsock.events.NSConnectionDisconnectedEvent;
import com.petrivirkkula.toolbox.netsock.events.NSConnectionReadableEvent;
import com.petrivirkkula.toolbox.netsock.events.NSConnectionWriteableEvent;
import com.petrivirkkula.toolbox.netsock.events.NSServiceStartedEvent;
import com.petrivirkkula.toolbox.netsock.events.NSServiceStateChangeEvent;
import com.petrivirkkula.toolbox.netsock.events.NSServiceStoppedEvent;


/**
 * Service SPI class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public abstract class NSServiceSpi extends AbstractEventSource implements NSService
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSServiceSpi.class);

	static {
		logger.loaded(RCSID, NSServiceSpi.class);
	}


	private final Object serviceStateMutex = new Object();

	private ServiceState currentServiceState;
	
	private ServiceState pendingServiceState;

	private Thread serviceThread = null;
	
	private final Object selectorMutex = new Object();
	
	private Selector selector = null;
	
	public NSServiceSpi() {
		this.currentServiceState = ServiceState.STOPPED;
		this.pendingServiceState = ServiceState.STOPPED;
	}

	
	protected abstract com.petrivirkkula.toolbox.logger.Logger getLogger();
	
	protected abstract EventManager getEventManager();

	protected abstract void processSelectionKey(Selector selector, SelectionKey key);
	
	protected abstract void registerChannels(Selector selector) throws InterruptedException, IOException;

	protected abstract void unregisterChannels(Selector selector) throws InterruptedException;
	
	

	@Override
	public void close() throws IOException {
	}

	@Override
	public boolean startService() throws IllegalStateException, InterruptedException {
		return changeServiceState(NSService.ServiceState.RUNNING);
	}

	@Override
	public boolean stopService() throws InterruptedException {
		return changeServiceState(NSService.ServiceState.STOPPED);
	}

	public boolean changeServiceState(NSService.ServiceState newState) throws InterruptedException {
		NSService.ServiceState fromState = null;
		synchronized(serviceStateMutex) {
			while(!currentServiceState.equals(pendingServiceState)) {
				// wait completion of pending service state changes
				serviceStateMutex.wait();
			}
			if (currentServiceState.equals(newState))
				return false;
			getLogger().info(new SimpleLoggable("requesting state change to " + newState));
			fromState = currentServiceState;
			pendingServiceState = newState;
			executeServiceStateChange();
			while(!currentServiceState.equals(newState)) {
				// wait completion of pending service state changes
				serviceStateMutex.wait();
			}
		}
		getLogger().info(new SimpleLoggable("changed state from " + fromState + " to " + newState));
		trigger(new NSServiceStateChangeEvent(NSServiceSpi.this, fromState, newState));
		return true;
	}

	

	protected void executeServiceStateChange() {
		switch(pendingServiceState) {
		case STOPPED:
			wakeupSelector();
			stopServiceThread(serviceThread);
			break;
		case RUNNING:
			startServiceThread();
			break;
		default:
			currentServiceState = pendingServiceState;
			break;
		}
	}

	@Override
	public ServiceState getCurrentServiceState() {
		synchronized(serviceStateMutex) {
			return currentServiceState;
		}
	}

	@Override
	public ServiceState getPendingServiceState() {
		synchronized(serviceStateMutex) {
			return pendingServiceState;
		}
	}

	@Override
	public boolean isRunning() {
		return NSService.ServiceState.RUNNING.equals(getCurrentServiceState());
	}

	@Override
	public boolean isStopped() {
		return NSService.ServiceState.STOPPED.equals(getCurrentServiceState());
	}

	protected Thread createServiceThread(Runnable runnable) {
		return new Thread(runnable, "ServiceThread");
	}
	
	protected Thread startServiceThread() {
		Thread thread = createServiceThread(new Runnable() {
			@Override
			public void run() {
				NSService.ServiceState prevState = null;
				synchronized(serviceStateMutex) {
					if (NSService.ServiceState.RUNNING.equals(currentServiceState))
						return;
					if (serviceThread != null)
						return;
					prevState = currentServiceState;
					currentServiceState = NSService.ServiceState.RUNNING;
					serviceThread = Thread.currentThread();
					serviceStateMutex.notifyAll();
				}
				trigger(new NSServiceStartedEvent(NSServiceSpi.this, prevState));
				try {
					NSServiceSpi.this.serviceMain();
				}
				finally {
					while(true) {
						try {
							// why is this needed?
							getEventManager().waitForPendingEvents();
							break;
						}
						catch (InterruptedException ex) {
//							getLogger().error(ex, "interrupted exception in server service: " + ex);
						}
					}
					prevState = null;
					synchronized(serviceStateMutex) {
						prevState = currentServiceState;
						currentServiceState = NSService.ServiceState.STOPPED;
						serviceStateMutex.notifyAll();
						serviceThread = null;
						trigger(new NSServiceStoppedEvent(NSServiceSpi.this, prevState));
					}
					try {
						getEventManager().waitForPendingEvents();
					}
					catch (InterruptedException ex) {
						getLogger().error(ex, "interrupted exception in service: " + ex);
					}
				}
			}
		});
//		thread.setDaemon(true);
		thread.start();
		return thread;
	}

	public void waitForServiceState(ServiceState serviceState) throws InterruptedException {
		synchronized(serviceStateMutex) {
			while(!serviceState.equals(currentServiceState)) {
				serviceStateMutex.wait();
			}
		}
	}
	
	
	protected void stopServiceThread(Thread thread) {
		if (thread == null)
			return;
//		getLogger().trace(new Exception(), "stopServiceThread");
//		thread.interrupt();
		wakeupSelector();
	}

	protected Selector getSelector() {
		synchronized(selectorMutex) {
			return selector;
		}
	}

	protected void wakeupSelector() {
		synchronized(selectorMutex) {
			if (selector != null)
				selector.wakeup();
		}
	}


	protected void serviceMain() {
		getLogger().info(new SimpleLoggable("service thread started"));
		try {
			synchronized(selectorMutex) {
				selector = Selector.open();
			}
			while(isRunning() && !NSService.ServiceState.STOPPED.equals(getPendingServiceState())) {
				try {
					registerChannels(selector);
				}
				catch(InterruptedException ex) {
					throw ex;
				}
				catch(Exception ex) {
					getLogger().error(ex, "exception: " + ex);
					continue;
				}
				
				if (selector.keys().size() == 0) {
					getLogger().trace(new SimpleLoggable("no selection keys"));
					continue;
				}
//				getLogger().trace(new SimpleLoggable("calling select with " + selector.keys().size() + " keys"));
				long selectStart = System.currentTimeMillis();
				int updateCount = selector.select();
				long selectEnd = System.currentTimeMillis();
				getLogger().trace(new SimpleLoggable("select returned " + updateCount + " in " + (selectEnd - selectStart) + "ms"));
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				if (selectedKeys.size() < 1)
					continue;
//				getLogger().trace(new SimpleLoggable("select returned " + updateCount));
//				if (updateCount < 1)
//					continue;
				for (SelectionKey key : selectedKeys) {
					processSelectionKey(selector, key);
				}
				selectedKeys.clear();
			}
		}
		catch (IOException ex) {
			getLogger().error(ex, "io exception in server service: " + ex);
		}
		catch (InterruptedException ex) {
			getLogger().error(ex, "server service interrupted: " + ex);
		}
		finally {
			synchronized(selectorMutex) {
				if (selector != null) {
					try {
						unregisterChannels(selector);
					}
					catch(Exception ex) {
						getLogger().error(ex, "exception in unregistering channels: " + ex);
					}
					try {
						selector.close();
					}
					catch (IOException ex) {
						getLogger().error(ex, "io exception in closing selector: " + ex);
					}
				}
				selector = null;
			}
			getLogger().info(new SimpleLoggable("service thread terminating"));
		}
	}


	protected void activateConnection(Selector selector, NSConnectionSpi connection) throws InterruptedException, IOException {
		connection.register(selector);
	}

	protected void inactivateConnection(Selector selector, NSConnectionSpi connection) throws InterruptedException {
		connection.unregister();
	}

	
	protected void processSelectionKey(Selector selector, SelectionKey key, NSConnectionSpi connection) {
		int ops = key.readyOps();
//		getLogger().trace("processing connection ops: " + NSConnectionSpi.describeOps(ops));
		if (!connection.isConnected()) {
			getLogger().trace(new SimpleLoggable("closed connection: " + connection));
			removeConnection(connection);
			connection.unregister();
			trigger(new NSConnectionDisconnectedEvent(this, connection));
			return;
		}
		if ((ops & SelectionKey.OP_READ) != 0) {
			getLogger().trace(new SimpleLoggable("readable connection: " + connection));
			connection.removeInterestOps(SelectionKey.OP_READ);
			trigger(new NSConnectionReadableEvent(this, connection));
		}
		if ((ops & SelectionKey.OP_WRITE) != 0) {
			getLogger().trace(new SimpleLoggable("writeable connection: " + connection));
			connection.removeInterestOps(SelectionKey.OP_WRITE);
			trigger(new NSConnectionWriteableEvent(this, connection));
		}

	}


	protected void removeConnection(NSConnectionSpi connection) {
	}
}
