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


import com.petrivirkkula.toolbox.eventmgr.Event;
import com.petrivirkkula.toolbox.eventmgr.EventHandler;


/**
 * Event Source interface.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public interface EventSource
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id$";

		
	/**
	 * Regusters an event handler.
	 *
	 * @param <E>			class of the event
	 * @param eventClass	event to be registered
	 * @param eventHandler	event handler
	 */
	public <E extends Event> void register(Class<E> eventClass, EventHandler<E> eventHandler);
	
	
	/**
	 * Registers an event handler for an event.
	 * 
	 * @param <E>			class of the event
	 * @param eventName		name of the event
	 * @param eventHandler	event handler
	 */
	public <E extends Event> void register(String eventName, EventHandler<E> eventHandler);
	
	
	/**
	 * Unregisters an event handler for an event.
	 * 
	 * @param <E>			class of the event
	 * @param eventClass	class of the event
	 * @param eventHandler	event handler
	 */
	public <E extends Event> void unregister(Class<E> eventClass, EventHandler<E> eventHandler);

	
	/**
	 * Unregisters an event handler for an event.
	 * 
	 * @param <E>			class of the event
	 * @param eventName		name of the event
	 * @param eventHandler	event handler
	 */
	public <E extends Event> void unregister(String eventName, EventHandler<E> eventHandler);

		
}
