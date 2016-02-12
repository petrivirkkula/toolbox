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

package com.petrivirkkula.toolbox.eventmgr.junit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.eventmgr.GenericEvent;

/**
 * @author Petri Virkkula
 *
 */
public class GenericEventTest
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(GenericEventTest.class);


	static {
		logger.loaded(RCSID, GenericEventTest.class);
	}

	
	private static final String TEST_EVENT_NAME = "test.event";

	private static final Date TEST_EVENT_TIME = new Date();
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent()}.
	 */
	@Test
	public void testGenericEvent() {
		GenericEvent event = new GenericEvent();
		assertEquals(GenericEvent.class.getName(), event.getEventName());
		assertNotNull(event.getEventTime());
		assertTrue((System.currentTimeMillis() - event.getEventTime().getTime()) < 1000);
		assertNull(event.getEventSource());
		assertNull(event.getNestedEvent());
		event.setEventName(TEST_EVENT_NAME);
		event.setEventSource(this);
		GenericEvent nested = new GenericEvent();
		event.setNestedEvent(nested);
		Date time = new Date();
		event.setEventTime(time);
		assertEquals(TEST_EVENT_NAME, event.getEventName());
		assertEquals(this, event.getEventSource());
		assertEquals(nested, event.getNestedEvent());
		assertEquals(time, event.getEventTime());
		
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent(java.lang.Object)}.
	 */
	@Test
	public void testGenericEventObject() {
		GenericEvent event = new GenericEvent(this);
		assertEquals(GenericEvent.class.getName(), event.getEventName());
		assertNotNull(event.getEventTime());
		assertTrue((System.currentTimeMillis() - event.getEventTime().getTime()) < 1000);
		assertEquals(this, event.getEventSource());
		assertNull(event.getNestedEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent(java.lang.Object, com.petrivirkkula.toolbox.eventmgr.Event)}.
	 */
	@Test
	public void testGenericEventObjectEvent() {
		GenericEvent nested = new GenericEvent();
		GenericEvent event = new GenericEvent(this, nested);
		assertEquals(GenericEvent.class.getName(), event.getEventName());
		assertNotNull(event.getEventTime());
		assertTrue((System.currentTimeMillis() - event.getEventTime().getTime()) < 1000);
		assertEquals(this, event.getEventSource());
		assertEquals(nested, event.getNestedEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent(java.lang.String)}.
	 */
	@Test
	public void testGenericEventString() {
		GenericEvent event = new GenericEvent(TEST_EVENT_NAME);
		assertEquals(TEST_EVENT_NAME, event.getEventName());
		assertNotNull(event.getEventTime());
		assertTrue((System.currentTimeMillis() - event.getEventTime().getTime()) < 1000);
		assertNull(event.getEventSource());
		assertNull(event.getNestedEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent(java.lang.String, com.petrivirkkula.toolbox.eventmgr.Event)}.
	 */
	@Test
	public void testGenericEventStringEvent() {
		GenericEvent nested = new GenericEvent();
		GenericEvent event = new GenericEvent(TEST_EVENT_NAME, nested);
		assertEquals(TEST_EVENT_NAME, event.getEventName());
		assertNotNull(event.getEventTime());
		assertTrue((System.currentTimeMillis() - event.getEventTime().getTime()) < 1000);
		assertNull(event.getEventSource());
		assertEquals(nested, event.getNestedEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent(java.lang.String, java.lang.Object)}.
	 */
	@Test
	public void testGenericEventStringObject() {
		GenericEvent event = new GenericEvent(TEST_EVENT_NAME, this);
		assertEquals(TEST_EVENT_NAME, event.getEventName());
		assertNotNull(event.getEventTime());
		assertTrue((System.currentTimeMillis() - event.getEventTime().getTime()) < 1000);
		assertEquals(this, event.getEventSource());
		assertNull(event.getNestedEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent(java.lang.String, java.lang.Object, com.petrivirkkula.toolbox.eventmgr.Event)}.
	 */
	@Test
	public void testGenericEventStringObjectEvent() {
		GenericEvent nested = new GenericEvent();
		GenericEvent event = new GenericEvent(TEST_EVENT_NAME, this, nested);
		assertEquals(TEST_EVENT_NAME, event.getEventName());
		assertNotNull(event.getEventTime());
		assertTrue((System.currentTimeMillis() - event.getEventTime().getTime()) < 1000);
		assertEquals(this, event.getEventSource());
		assertEquals(nested, event.getNestedEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent(java.lang.String, java.util.Date, java.lang.Object)}.
	 */
	@Test
	public void testGenericEventStringDateObject() {
		GenericEvent event = new GenericEvent(TEST_EVENT_NAME, TEST_EVENT_TIME, this);
		assertEquals(TEST_EVENT_NAME, event.getEventName());
		assertEquals(TEST_EVENT_TIME, event.getEventTime());
		assertTrue(TEST_EVENT_TIME.getTime() == event.getEventTime().getTime());
		assertEquals(this, event.getEventSource());
		assertNull(event.getNestedEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.GenericEvent#GenericEvent(java.lang.String, java.util.Date, java.lang.Object, com.petrivirkkula.toolbox.eventmgr.Event)}.
	 */
	@Test
	public void testGenericEventStringDateObjectEvent() {
		GenericEvent nested = new GenericEvent();
		GenericEvent event = new GenericEvent(TEST_EVENT_NAME, TEST_EVENT_TIME, this, nested);
		assertEquals(TEST_EVENT_NAME, event.getEventName());
		assertEquals(TEST_EVENT_TIME, event.getEventTime());
		assertTrue(TEST_EVENT_TIME.getTime() == event.getEventTime().getTime());
		assertEquals(this, event.getEventSource());
		assertEquals(nested, event.getNestedEvent());
	}

}
