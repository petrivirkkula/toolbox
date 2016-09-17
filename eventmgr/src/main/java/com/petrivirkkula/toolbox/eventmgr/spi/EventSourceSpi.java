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

import com.petrivirkkula.toolbox.eventmgr.Event;
import com.petrivirkkula.toolbox.eventmgr.EventHandler;
import com.petrivirkkula.toolbox.eventmgr.EventManager;


/**
 * An abstract Event Manager class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public abstract class EventSourceSpi
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(EventSourceSpi.class);

	static {
		logger.loaded(RCSID, EventSourceSpi.class);
	}

	protected abstract EventManager getEventManager();	
	
	
	/**
	 * Triggers an event.
	 *
	 * @param <E>			class of the event
	 * @param event			event
	 */
	public <E extends Event> void trigger(E event) {
		getEventManager().trigger(event);
	}
		
	/**
	 * Registers event handler for event class.
	 *
	 * @param <E>			class of the event
	 * @param eventClass	class of event
	 * @param eventHandler	event handler
	 */
	protected <E extends Event> void register(Class<E> eventClass, EventHandler<E> eventHandler) {
		register(eventClass.getName(), eventHandler);
	}

	
	/**
	 * Registers an event handler for an event.
	 * 
	 * @param <E>			class of the event
	 * @param eventName		name of event
	 * @param eventHandler	event handler
	 */
	protected <E extends Event> void register(String eventName, EventHandler<E> eventHandler) {
		getEventManager().register(this, eventName, eventHandler);
	}

	
	/**
	 * Unregisters event handler for event class.
	 *
	 * @param <E>			class of the event
	 * @param eventClass	class of event
	 * @param eventHandler	event handler
	 */
	protected <E extends Event> void unregister(Class<E> eventClass, EventHandler<E> eventHandler) {
		unregister(eventClass.getName(), eventHandler);
	}

	
	/**
	 * Unregisters an event handler for an event.
	 * 
	 * @param <E>			class of the event
	 * @param eventName		name of event
	 * @param eventHandler	event handler
	 */
	protected <E extends Event> void unregister(String eventName, EventHandler<E> eventHandler) {
		getEventManager().unregister(this, eventName, eventHandler);
	}

}
