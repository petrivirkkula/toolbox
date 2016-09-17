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

package com.petrivirkkula.toolbox.logger.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.petrivirkkula.toolbox.logger.LogLevel;

/**
 *
 * @author		Petri Virkula
 * @version		$Id$
 */
public class TestLogLevel
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id$";

	/**
	 * MyLogLevel class used for testing of LogLevel
	 */
	private static class MyLogLevel extends LogLevel {
		private static final long serialVersionUID = 1L;
		public MyLogLevel(String levelName, int levelValue) {
			super(levelName, levelValue);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
	}


	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() {
	}


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}


	/**
	 * Test method for NAME constants defined in {@link com.petrivirkkula.toolbox.logger.LogLevel}.
	 */
	@Test
	public void testNAMEConstants() {
		assertEquals("TRACE", LogLevel._TRACE_NAME);
		assertEquals("DEBUG", LogLevel._DEBUG_NAME);
		assertEquals("INFO", LogLevel._INFO_NAME);
		assertEquals("NOTICE", LogLevel._NOTICE_NAME);
		assertEquals("AUDIT", LogLevel._AUDIT_NAME);
		assertEquals("WARN", LogLevel._WARN_NAME);
		assertEquals("ERROR", LogLevel._ERROR_NAME);
		assertEquals("FATAL", LogLevel._FATAL_NAME);
	}

	/**
	 * Test method for NAME constants defined in {@link com.petrivirkkula.toolbox.logger.LogLevel}.
	 */
	@Test
	public void testVALUEConstants() {
		assertEquals(4096, LogLevel._TRACE_VALUE);
		assertEquals(1024, LogLevel._DEBUG_VALUE);
		assertEquals(512, LogLevel._INFO_VALUE);
		assertEquals(256, LogLevel._NOTICE_VALUE);
		assertEquals(128, LogLevel._AUDIT_VALUE);
		assertEquals(64, LogLevel._WARN_VALUE);
		assertEquals(32, LogLevel._ERROR_VALUE);
		assertEquals(16, LogLevel._FATAL_VALUE);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.LogLevel#getLevelName()}.
	 */
	@Test
	public void testGetLevelName() {
		assertEquals(LogLevel._TRACE_NAME, LogLevel.TRACE.getLevelName());
		assertEquals(LogLevel._DEBUG_NAME, LogLevel.DEBUG.getLevelName());
		assertEquals(LogLevel._INFO_NAME, LogLevel.INFO.getLevelName());
		assertEquals(LogLevel._NOTICE_NAME, LogLevel.NOTICE.getLevelName());
		assertEquals(LogLevel._AUDIT_NAME, LogLevel.AUDIT.getLevelName());
		assertEquals(LogLevel._WARN_NAME, LogLevel.WARN.getLevelName());
		assertEquals(LogLevel._ERROR_NAME, LogLevel.ERROR.getLevelName());
		assertEquals(LogLevel._FATAL_NAME, LogLevel.FATAL.getLevelName());
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.LogLevel#getLevelValue()}.
	 */
	@Test
	public void testGetLevelValue() {
		assertEquals(LogLevel._TRACE_VALUE, LogLevel.TRACE.getLevelValue());
		assertEquals(LogLevel._DEBUG_VALUE, LogLevel.DEBUG.getLevelValue());
		assertEquals(LogLevel._INFO_VALUE, LogLevel.INFO.getLevelValue());
		assertEquals(LogLevel._NOTICE_VALUE, LogLevel.NOTICE.getLevelValue());
		assertEquals(LogLevel._AUDIT_VALUE, LogLevel.AUDIT.getLevelValue());
		assertEquals(LogLevel._WARN_VALUE, LogLevel.WARN.getLevelValue());
		assertEquals(LogLevel._ERROR_VALUE, LogLevel.ERROR.getLevelValue());
		assertEquals(LogLevel._FATAL_VALUE, LogLevel.FATAL.getLevelValue());
	}


	@Test
	public void testConstructor() {
		MyLogLevel mll1 = new MyLogLevel("ABC", 1000);
		MyLogLevel mll2 = null;
		try {
			mll2 = new MyLogLevel(null, 1000);
			fail("expected exception for null log level name");
		}
		catch(IllegalArgumentException ex) {
			assertTrue(ex instanceof IllegalArgumentException);
		}
		assertEquals(false, mll1.equals(mll2));
	}
	
	@Test
	public void testEquals() {
		MyLogLevel mll1 = new MyLogLevel("ABC", 1000);
		MyLogLevel mll2 = new MyLogLevel("DEF", 1000);
		MyLogLevel mll3 = new MyLogLevel("DEF", 2000);
		assertEquals(false, mll1.equals(null));
		assertEquals(true, mll1.equals(mll1));
		assertEquals(true, mll1.equals(mll2));
		assertEquals(false, mll2.equals(mll3));
		try {
			mll1.equals("str");
			fail("should be getting ClassCastExcption");
		}
		catch(ClassCastException ex) {
			assertTrue(ex instanceof ClassCastException);
		}
	}

	@Test
	public void testCompareTo() {
		MyLogLevel mll1 = new MyLogLevel("ABC", 1000);
		MyLogLevel mll2 = new MyLogLevel("DEF", 1000);
		MyLogLevel mll3 = new MyLogLevel("DEF", 2000);
		try {
			mll1.compareTo(null);
			fail("expected NullPointerException");
		}
		catch(NullPointerException ex) {
			assertTrue(ex instanceof NullPointerException);
		}
		assertEquals(true, mll1.compareTo(mll1) == 0);
		assertEquals(true, mll1.compareTo(mll2) < 0);
		assertEquals(true, mll2.compareTo(mll3) < 0);
		try {
			mll1.equals("str");
			fail("should be getting ClassCastExcption");
		}
		catch(ClassCastException ex) {
			assertTrue(ex instanceof ClassCastException);
		}
	}
	@Test
	public void testhashCode() {
		MyLogLevel mll = new MyLogLevel("ABC", 1000);
		assertEquals(mll.hashCode(), mll.getLevelValue());
	}
}
