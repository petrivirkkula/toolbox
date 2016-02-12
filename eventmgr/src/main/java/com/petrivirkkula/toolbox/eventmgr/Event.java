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
 * An Event.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public interface Event extends java.io.Serializable
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";
	
	
	/**
	 * Gets the name of this event.
	 * 
	 * @return	name of this event
	 */
	public String getEventName();
	
	
	/**
	 * Gets time of this event.
	 *  
	 * @return	event time
	 */
	public Date getEventTime();
	
	
	/**
	 * Gets the event source
	 * 
	 * @return	the object that generated the event
	 */
	public Object getEventSource();
	
	
	/**
	 * Gets the nested event.
	 * 
	 * @return	the event that triggered this event	
	 */
	public Event getNestedEvent();
	
}
