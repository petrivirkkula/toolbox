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


/**
 * A Runtime Event Exception.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class EventRuntimeException extends RuntimeException
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(EventRuntimeException.class);

	static {
		logger.loaded(RCSID, EventRuntimeException.class);
	}

	/**
	 * Causing event
	 */
	private Event causingEvent;
	
	
	/**
	 * Serialization version UID.
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor without parameters.
	 */
	public EventRuntimeException() {
		this.causingEvent = null;
	}

	
	/**
	 * Constructor with message parameter.
	 * 
	 * @param message	exception messages
	 */
	public EventRuntimeException(String message) {
		super(message);
		this.causingEvent = null;
	}

	
	/**
	 * Constructor with event and message parameters.
	 * 
	 * @param causingEvent	event causing this exception 
	 * @param message	exception messages
	 */
	public EventRuntimeException(Event causingEvent, String message) {
		super(message);
		this.causingEvent = causingEvent;
	}

	
	/**
	 * Constructor with cause parameter.
	 * 
	 * @param cause		nested exception
	 */
	public EventRuntimeException(Throwable cause) {
		super(cause);
		this.causingEvent = null;
	}

	
	/**
	 * Constructor with event and cause parameters.
	 * 
	 * @param causingEvent		the event that caused this exception
	 * @param cause		nested exception
	 */
	public EventRuntimeException(Event causingEvent, Throwable cause) {
		super(cause);
		this.causingEvent = causingEvent;
	}

	
	/**
	 * Constructor with message and cause parameters.
	 * 
	 * @param message	exception messages
	 * @param cause		nested exception
	 */
	public EventRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.causingEvent = null;
	}

	
	/**
	 * Constructor with event, message and cause parameters.
	 * 
	 * @param causingEvent		event that caused this exception
	 * @param message	exception messages
	 * @param cause		nested exception
	 */
	public EventRuntimeException(Event causingEvent, String message, Throwable cause) {
		super(message, cause);
		this.causingEvent = causingEvent;
	}

	
	/**
	 * Gets the event that caused this exception.
	 * 
	 * @return the event
	 */
	public Event getCausingEvent() {
		return causingEvent;
	}

	
	/**
	 * Sets the event that caused this exception.
	 * 
	 * @param causingEvent the event to set
	 */
	public void setCausingEvent(Event causingEvent) {
		this.causingEvent = causingEvent;
	}

}
