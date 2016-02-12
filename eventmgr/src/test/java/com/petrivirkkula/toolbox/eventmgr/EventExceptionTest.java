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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author petri
 *
 */
public class EventExceptionTest {

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
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventException#EventException()}.
	 */
	@Test
	public void testEventException() {
		new EventException();
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventException#EventException(java.lang.String)}.
	 */
	@Test
	public void testEventExceptionString() {
		EventException ex = new EventException("exception");
		assertEquals("exception", ex.getMessage());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventException#EventException(com.petrivirkkula.toolbox.eventmgr.Event, java.lang.String)}.
	 */
	@Test
	public void testEventExceptionEventString() {
		GenericEvent event = new GenericEvent("myevent");
		EventException ex = new EventException(event, "exception");
		assertEquals("exception", ex.getMessage());
		assertTrue(event == ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventException#EventException(java.lang.Throwable)}.
	 */
	@Test
	public void testEventExceptionThrowable() {
		Exception nested = new Exception();
		EventException ex = new EventException(nested);
		assertTrue(nested == ex.getCause());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventException#EventException(com.petrivirkkula.toolbox.eventmgr.Event, java.lang.Throwable)}.
	 */
	@Test
	public void testEventExceptionEventThrowable() {
		GenericEvent event = new GenericEvent("myevent");
		Exception nested = new Exception();
		EventException ex = new EventException(event, nested);
		assertTrue(nested == ex.getCause());
		assertTrue(event == ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventException#EventException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testEventExceptionStringThrowable() {
		Exception nested = new Exception();
		EventException ex = new EventException("exception", nested);
		assertTrue(nested == ex.getCause());
		assertEquals("exception", ex.getMessage());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventException#EventException(com.petrivirkkula.toolbox.eventmgr.Event, java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testEventExceptionEventStringThrowable() {
		GenericEvent event = new GenericEvent("myevent");
		Exception nested = new Exception();
		EventException ex = new EventException(event, "exception", nested);
		assertTrue(nested == ex.getCause());
		assertEquals("exception", ex.getMessage());
		assertTrue(event == ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventException#getCausingEvent()}.
	 */
	@Test
	public void testCausingEvent() {
		EventException ex = new EventException("exception");
		assertEquals("exception", ex.getMessage());
		assertTrue(null == ex.getCausingEvent());
		GenericEvent event = new GenericEvent("myevent");
		ex.setCausingEvent(event);
		assertTrue(event == ex.getCausingEvent());
	}


}
