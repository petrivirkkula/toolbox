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

package com.petrivirkkula.toolbox.eventmgr.spi;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.petrivirkkula.toolbox.logger.SimpleLoggable;
import com.petrivirkkula.toolbox.eventmgr.DirectExecutor;
import com.petrivirkkula.toolbox.eventmgr.Event;
import com.petrivirkkula.toolbox.eventmgr.EventExecutorFactory;
import com.petrivirkkula.toolbox.eventmgr.EventHandler;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.eventmgr.EventStats;


/**
 * An Event Manager Provider Interface.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public abstract class EventManagerSpi extends EventManager
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
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(EventManagerSpi.class);

	static {
		LOGGER.loaded(RCSID, EventManagerSpi.class);
	}

	
	/**
	 * Event Executor factory.
	 */
	private EventExecutorFactory executorFactory;
	
	/**
	 * Event stats.
	 */
	private final EventStatsSpi eventStats = new EventStatsSpi();

	
	/**
	 * Mutex protecting <code>lookupCounter</code>.
	 */
	private final Object lookupCounterMutex = new Object();
	
	/**
	 *  Lookup active thread count.
	 */
	private int lookupCounter = 0;
	
	
	/**
	 * Mutex guarding the <code>processingCounter</code>
	 */
	private final Object processingCounterMutex = new Object();
	
	
	/**
	 * Active processing thread count.
	 */
	private int processingCounter = 0;
	
	
	/**
	 * Gets LOGGER.
	 * 
	 * @return	LOGGER
	 */
	protected abstract com.petrivirkkula.toolbox.logger.Logger getLogger();
	

	/**
	 * Gets event handler usage counts.
	 * 
	 * @return	map of event handler usage counts
	 */
	public abstract Map<Object,Map<String,Integer>> getEventHandlerCounts();
	
	
	/**
	 * Lookups for event handler list for given event.
	 * 
	 * @param	event	event
	 * @return	event handler list
	 */
	protected abstract <E extends Event> List<EventHandler<? extends Event>> lookupHandlers(final E event);


	
	/**
	 * Initialize this Event Manager.
	 * 
	 * @param executorFactory	executor factory to be used
	 */
	public void initialize(EventExecutorFactory executorFactory) {
		this.executorFactory = executorFactory;
	}


	/**
	 * Gets the executor factory for this event manager.
	 * 
	 * @return the executorFactory
	 */
	protected EventExecutorFactory getExecutorFactory() {
		return executorFactory;
	}

	
	/**
	 * Records event stats
	 * 
	 * @param eventName		name of event
	 * @param handlerCount	added calls to the event name
	 */
	protected void recordStat(String eventName, int handlerCount) {
		eventStats.recordStat(eventName, handlerCount);
	}

	
	@Override
	public EventStats getEventStats() {
		return eventStats.createSnapshot();
	}
	
	
	/**
	 * Trigger the event passed as argument.
	 * 
	 * @param event		event to be triggered
	 */
	@Override
	public <E extends Event> void trigger(final E event) {
//		getLogger().trace(new FormatterLoggable("trigger {0}", new Object[] { event }));
		final EventExecutorFactory factory = getExecutorFactory();
		Executor executor = factory.getLookupExecutor(event);
		if (executor == null)
			executor = new DirectExecutor();
		final Executor lookupExecutor  = executor;
		lookupExecutor.execute(new Runnable() {
			public void run() {
				int handlerCount = 0;
				try {
					eventLookupStarted();
					List<EventHandler<? extends Event>> handlers = lookupHandlers(event);
					if (handlers == null) {
	//					getLogger().trace(new SimpleLoggable("no handlers"));
//						recordStat(event.getEventName(), 0);
						return;
					}
					List<EventHandler<? extends Event>> handlersCopy = null;
					synchronized(handlers) {
						handlersCopy = new ArrayList<EventHandler<? extends Event>>(handlers);
					}
					Executor processExecutor = factory.getProcessExecutor(event);
					if (processExecutor == null)
						processExecutor = lookupExecutor;
					if (processExecutor == lookupExecutor && handlersCopy.size() == 1) {
						handlerCount++;
						@SuppressWarnings("unchecked")
						final EventHandler<Event> handler = (EventHandler<Event>) handlersCopy.get(0);
						getLogger().trace(new SimpleLoggable("calling event " + event + " handler " + handler));
						try {
							eventProcessingStarted();
							handler.on(event);
						}
						catch(Throwable ex) {
							getLogger().warn(new SimpleLoggable(ex, "exception in handler: " + ex));
						}
						finally {
							eventProcessingCompleted();
						}
					} else {
						@SuppressWarnings("rawtypes")
						Iterator iterator = handlersCopy.listIterator();
						while (iterator.hasNext()) {
							handlerCount++;
							@SuppressWarnings("unchecked")
							final EventHandler<Event> handler = (EventHandler<Event>) iterator.next();
							getLogger().trace(new SimpleLoggable("calling event " + event + " handler " + handler));
							processExecutor.execute(new Runnable() {
								public void run() {
									try {
										eventProcessingStarted();
										handler.on(event);
									}
									catch(Throwable ex) {
										getLogger().warn(new SimpleLoggable(ex, "exception in handler: " + ex));
									}
									finally {
										eventProcessingCompleted();
									}
								}

							});
						}
					}
				}
				finally {
					recordStat(event.getEventName(), handlerCount);
					eventLookupCompleted();
				}
			}

		});
	}

	/**
	 * Records completion of event lookup.
	 */
	private void eventLookupCompleted() {
		synchronized(lookupCounterMutex) {
			lookupCounter--;
			if (lookupCounter == 0)
				lookupCounterMutex.notifyAll();
		}
	}

	
	/**
	 * Records start of event lookup.
	 */
	private void eventLookupStarted() {
		synchronized(lookupCounterMutex) {
			lookupCounter++;
		}
	}

	
	/**
	 * Records completion of event processing.
	 */
	private void eventProcessingCompleted() {
		synchronized(processingCounterMutex) {
			processingCounter--;
			if (processingCounter == 0)
				processingCounterMutex.notifyAll();
		}
	}

	
	/**
	 * Records start of event processing.
	 */
	private void eventProcessingStarted() {
		synchronized(processingCounterMutex) {
			processingCounter++;
		}
	}
	
	
	@Override
	public void waitForPendingEvents() throws InterruptedException {
		synchronized(lookupCounterMutex) {
			while (lookupCounter > 0) {
				lookupCounterMutex.wait();
			}
		}
		synchronized(processingCounterMutex) {
			while (processingCounter > 0) {
				processingCounterMutex.wait();
			}
		}
	}

}
