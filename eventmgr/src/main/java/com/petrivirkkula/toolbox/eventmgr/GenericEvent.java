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


import java.util.Date;


/**
 * Generic Event Implementation class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class GenericEvent implements Event
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
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(GenericEvent.class);

	static {
		LOGGER.loaded(RCSID, GenericEvent.class);
	}

	
	/**
	 * Serialization version UID.
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Name of this event.
	 */
	private String eventName;

	/**
	 * Time of this event.
	 */
	private Date eventTime;

	/**
	 * Source of this event.
	 */
	private Object eventSource;

	/**
	 * Nested event.
	 */
	private Event nestedEvent;
	
	
	/**
	 * Constructor without parameters.
	 */
	public GenericEvent() {
		this.eventName = getClass().getName();
		this.eventTime = new Date();
		this.eventSource = null;
		this.nestedEvent = null;
	}
	
	
	/**
	 * Constructor with event source parameter.
	 * 
	 * @param eventSource	source of this event
	 */
	public GenericEvent(Object eventSource) {
		this.eventName = getClass().getName();
		this.eventTime = new Date();
		this.eventSource = eventSource;
		this.nestedEvent = null;
	}

	/**
	 * Constructor with event source and nested event parameters.
	 * 
	 * @param eventSource	source of this event
	 * @param nestedEvent	nested event
	 */
	public GenericEvent(Object eventSource, Event nestedEvent) {
		this.eventName = getClass().getName();
		this.eventTime = new Date();
		this.eventSource = eventSource;
		this.nestedEvent = nestedEvent;
	}

	
	/**
	 * Constructor with event name parameter.
	 * 
	 * @param eventName		name of this event
	 */
	public GenericEvent(String eventName) {
		this.eventName = eventName;
		this.eventTime = new Date();
		this.eventSource = null;
		this.nestedEvent = null;
	}

	/**
	 * Constructor with event name and nested event parameters.
	 * 
	 * @param eventName		name of this event
	 * @param nestedEvent	nested event
	 */
	public GenericEvent(String eventName, Event nestedEvent) {
		this.eventName = eventName;
		this.eventTime = new Date();
		this.eventSource = null;
		this.nestedEvent = nestedEvent;
	}
	
	/**
	 * Constructor with event name and source parameters.
	 * 
	 * @param eventName		name of this event
	 * @param eventSource	source of this event
	 */
	public GenericEvent(String eventName, Object eventSource) {
		this.eventName = eventName;
		this.eventTime = new Date();
		this.eventSource = eventSource;
		this.nestedEvent = null;
	}

	
	/**
	 * Constructor with event name, source and nested event parameters.
	 * 
	 * @param eventName		name of this event
	 * @param eventSource	source of this event
	 * @param nestedEvent	nested event
	 */
	public GenericEvent(String eventName, Object eventSource, Event nestedEvent) {
		this.eventName = eventName;
		this.eventTime = new Date();
		this.eventSource = eventSource;
		this.nestedEvent = nestedEvent;
	}

	
	/**
	 * Constructor with event name, time and source parameters.
	 * 
	 * @param eventName		name of this event
	 * @param eventTime		time of this event
	 * @param eventSource	source of this event
	 */
	public GenericEvent(String eventName, Date eventTime, Object eventSource) {
		this.eventName = eventName;
		this.eventTime = eventTime;
		this.eventSource = eventSource;
		this.nestedEvent = null;
	}

	
	/**
	 * Constructor with event name, time, source and nested event parameters.
	 * 
	 * @param eventName		name of this event
	 * @param eventTime		time of this event
	 * @param eventSource	source of this event
	 * @param nestedEvent	nested event
	 */
	public GenericEvent(String eventName, Date eventTime, Object eventSource, Event nestedEvent) {
		this.eventName = eventName;
		this.eventTime = eventTime;
		this.eventSource = eventSource;
		this.nestedEvent = nestedEvent;
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		appendField(sb, "eventName", getEventName()).append(",");
		appendField(sb, "eventTime", getEventTime()).append(",");
		appendField(sb, "eventSource", getEventSource()).append(",");
		appendField(sb, "nestedEvent", getNestedEvent());
		sb.append("}");
		return sb.toString();
	}

	
	private StringBuilder appendField(StringBuilder sb, String name, Object value) {
		sb.append("\"").append(name).append("\":");
		if (value == null) {
			sb.append("null");
		} else {
			sb.append("\"").append(value.toString()).append("\"");
		}
		return sb;
	}


	/**
	 * Gets name of this event.
	 * 
	 * @return the event name
	 */
	public String getEventName() {
		return eventName;
	}

	
	/**
	 * Sets the name of this event.
	 * 
	 * @param eventName the event name to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	
	/**
	 * Gets the time of this event.
	 * 
	 * @return the eventTime
	 */
	public Date getEventTime() {
		return eventTime;
	}

	
	/**
	 * Sets the time of this event.
	 * 
	 * @param eventTime the eventTime to set
	 */
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	
	/**
	 * Gets source of this event.
	 * 
	 * @return the eventSource
	 */
	public Object getEventSource() {
		return eventSource;
	}

	
	/**
	 * Sets the source of this event/
	 * 
	 * @param eventSource the eventSource to set
	 */
	public void setEventSource(Object eventSource) {
		this.eventSource = eventSource;
	}


	/**
	 * Gets the nested event.
	 * 
	 * @return the nested event
	 */
	public Event getNestedEvent() {
		return nestedEvent;
	}


	/**
	 * Sets the nested event.
	 * 
	 * @param nestedEvent the nestedEvent to set
	 */
	public void setNestedEvent(Event nestedEvent) {
		this.nestedEvent = nestedEvent;
	}
	
	
}
