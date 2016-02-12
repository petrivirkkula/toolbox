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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.eventmgr.EventRuntimeException;
import com.petrivirkkula.toolbox.eventmgr.GenericEvent;

/**
 * @author Petri Virkkula
 *
 */
public class EventRuntimeExceptionTest
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(EventRuntimeExceptionTest.class);

	static {
		logger.loaded(RCSID, EventRuntimeExceptionTest.class);
	}

	
	private static final String TEST_MESSAGE = "Test Message";
	static {
		logger.debug("TEST_MESSAGE=" + TEST_MESSAGE);
	}
	
	
	private static GenericEvent TEST_EVENT = null;
	static {
		try {
			TEST_EVENT = new GenericEvent("test.event");
		}
		catch(Throwable th) {
			logger.error(th, "exception: " + th);
		}
	}
	static {
		logger.debug("TEST_EVENT=" + TEST_EVENT);
	}

	private static final Exception TEST_CAUSE = new Exception("test exception");
	static {
		logger.debug("TEST_CAUSE=" + TEST_CAUSE);
	}

	
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
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#EventRuntimeException()}.
	 */
	@Test
	public void testEventRuntimeException() {
		EventRuntimeException ex = new EventRuntimeException();
		assertEquals(null, ex.getMessage());
		assertEquals(null, ex.getCause());
		assertEquals(null, ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#EventRuntimeException(java.lang.String)}.
	 */
	@Test
	public void testEventRuntimeExceptionString() {
		EventRuntimeException ex = new EventRuntimeException(TEST_MESSAGE);
		assertEquals(TEST_MESSAGE, ex.getMessage());
		assertEquals(null, ex.getCause());
		assertEquals(null, ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#EventRuntimeException(com.petrivirkkula.toolbox.eventmgr.Event, java.lang.String)}.
	 */
	@Test
	public void testEventRuntimeExceptionEventString() {
		EventRuntimeException ex = new EventRuntimeException(TEST_EVENT, TEST_MESSAGE);
		assertEquals(TEST_MESSAGE, ex.getMessage());
		assertEquals(null, ex.getCause());
		assertEquals(TEST_EVENT, ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#EventRuntimeException(java.lang.Throwable)}.
	 */
	@Test
	public void testEventRuntimeExceptionThrowable() {
		EventRuntimeException ex = new EventRuntimeException(TEST_CAUSE);
		assertEquals(TEST_CAUSE.getClass().getName() + ": " + TEST_CAUSE.getMessage(), ex.getMessage());
		assertEquals(TEST_CAUSE, ex.getCause());
		assertEquals(null, ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#EventRuntimeException(com.petrivirkkula.toolbox.eventmgr.Event, java.lang.Throwable)}.
	 */
	@Test
	public void testEventRuntimeExceptionEventThrowable() {
		EventRuntimeException ex = new EventRuntimeException(TEST_EVENT, TEST_CAUSE);
		assertEquals(TEST_CAUSE.getClass().getName() + ": " + TEST_CAUSE.getMessage(), ex.getMessage());
		assertEquals(TEST_CAUSE, ex.getCause());
		assertEquals(TEST_EVENT, ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#EventRuntimeException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testEventRuntimeExceptionStringThrowable() {
		EventRuntimeException ex = new EventRuntimeException(TEST_MESSAGE, TEST_CAUSE);
		assertEquals(TEST_MESSAGE, ex.getMessage());
		assertEquals(TEST_CAUSE, ex.getCause());
		assertEquals(null, ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#EventRuntimeException(com.petrivirkkula.toolbox.eventmgr.Event, java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testEventRuntimeExceptionEventStringThrowable() {
		EventRuntimeException ex = new EventRuntimeException(TEST_EVENT, TEST_MESSAGE, TEST_CAUSE);
		assertEquals(TEST_MESSAGE, ex.getMessage());
		assertEquals(TEST_CAUSE, ex.getCause());
		assertEquals(TEST_EVENT, ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#getCausingEvent()}.
	 */
	@Test
	public void testGetCausingEvent() {
		EventRuntimeException ex = new EventRuntimeException(TEST_EVENT, TEST_MESSAGE, TEST_CAUSE);
		assertEquals(TEST_MESSAGE, ex.getMessage());
		assertEquals(TEST_CAUSE, ex.getCause());
		assertEquals(TEST_EVENT, ex.getCausingEvent());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventRuntimeException#setCausingEvent(com.petrivirkkula.toolbox.eventmgr.Event)}.
	 */
	@Test
	public void testSetCausingEvent() {
		EventRuntimeException ex = new EventRuntimeException(TEST_EVENT, TEST_MESSAGE, TEST_CAUSE);
		assertEquals(TEST_MESSAGE, ex.getMessage());
		assertEquals(TEST_CAUSE, ex.getCause());
		assertEquals(TEST_EVENT, ex.getCausingEvent());
		ex.setCausingEvent(null);
		assertEquals(null, ex.getCausingEvent());
	}

}
