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

package com.petrivirkkula.toolbox.eventmgr;


import com.petrivirkkula.toolbox.eventmgr.spi.EventManagerSpi;


/**
 * An abstract Event Manager class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public abstract class EventManager
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(EventManager.class);

	static {
		logger.loaded(RCSID, EventManager.class);
	}

	/**
	 * 
	 * @author Petri Virkkula
	 * @see http://en.wikipedia.org/wiki/Singleton_pattern#The_solution_of_Bill_Pugh
	 */
	private static class EventManagerClassHolder {
		static Class<? extends EventManagerSpi> DEFAULT_EVENT_MANAGER_CLASS = DefaultEventManager.class;
	}
	
	static {
		new EventManagerClassHolder();
	}
	
	public static EventManager getInstance() {
		EventExecutorFactory executorFactory = DefaultEventExecutorFactory.getInstance();
		Class<? extends EventManagerSpi> eventManagerClass = EventManagerClassHolder.DEFAULT_EVENT_MANAGER_CLASS;
		return getInstance(executorFactory, eventManagerClass);
	}

	public static EventManager getInstance(EventExecutorFactory executorFactory) {
		Class<? extends EventManagerSpi> eventManagerClass = EventManagerClassHolder.DEFAULT_EVENT_MANAGER_CLASS;
		return getInstance(executorFactory, eventManagerClass);
	}
	
	
	/**
	 * Gets event manager instance.
	 * 
	 * @param	executorFactory		executor factory
	 * @param	eventManagerClass	event manager class
	 * @return	event manager instance
	 */
	public static EventManager getInstance(EventExecutorFactory executorFactory, Class<? extends EventManagerSpi> eventManagerClass) {
		EventManagerSpi eventManager = null;
		try {
			eventManager = eventManagerClass.newInstance();
			eventManager.initialize(executorFactory);
		} 
		catch (InstantiationException ex) {
			logger.error("exception in event manager creation: " + ex);
			throw new EventRuntimeException(ex);
		}
		catch (IllegalAccessException ex) {
			logger.error("exception in event manager creation: " + ex);
			throw new EventRuntimeException(ex);
		}
		return eventManager;
	}
	
	
	/**
	 * Triggers an event.
	 *
	 * @param <E>			class of the event
	 * @param event			event
	 */
	public abstract <E extends Event> void trigger(E event);
	
	
	/**
	 * Registers an event handler for given even originating from given event source.
	 *
	 * @param <E>			class of the event
	 * @param eventSource	source of event
	 * @param eventClass	event class
	 * @param eventHandler	event handler
	 * @return	this EventManager for call chaining
	 */
	public <E extends Event> EventManager register(Object eventSource, Class<E> eventClass, EventHandler<E> eventHandler) {
		return register(eventSource, eventClass.getName(), eventHandler);
	}

	
	/**
	 * Registers an event handler for an event in source.
	 * 
	 * @param <E>			class of the event
	 * @param eventSource	source of event
	 * @param eventName		name of event
	 * @param eventHandler	event handler
	 * @return	this EventManager for call chaining
	 */
	public abstract <E extends Event> EventManager register(Object eventSource, String eventName, EventHandler<E> eventHandler);

	
	public <E extends Event> EventManager unregister(Object eventSource, Class<E> eventClass, EventHandler<E> eventHandler) {
		return unregister(eventSource, eventClass.getName(), eventHandler);
	}

	
	public abstract <E extends Event> EventManager unregister(Object eventSource, String eventName, EventHandler<E> eventHandler);

	public abstract void waitForPendingEvents() throws InterruptedException;
	
	public abstract EventStats getEventStats();

}
