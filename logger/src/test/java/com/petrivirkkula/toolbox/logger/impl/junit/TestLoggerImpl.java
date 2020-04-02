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

 package com.petrivirkkula.toolbox.logger.impl.junit;

import mockit.Expectations;
import mockit.Mocked;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.logger.impl.LoggerImpl;
import com.petrivirkkula.toolbox.logger.junit.TestLogger;



public class TestLoggerImpl
{
	

	private LoggerImpl tested;

	private static class TestException extends Exception {
		private static final long serialVersionUID = 1L; 		
	}

	public static final String LOGCATEGORY = "TestLoggerImpl";

	private static final TestException TESTEXCEPTION = new TestException();
	
	private static final String LOGSTRING = "log message";
	
	@Mocked org.slf4j.Logger slf4jLogger;

	@Before
	public void setUp() {
		tested = new LoggerImpl(LOGCATEGORY, slf4jLogger);
	}

	
	@Test
	public void testHashCode() {
		assertEquals(tested.hashCode(), LOGCATEGORY.hashCode());
	}

	@Test
	public void testEqualsObject() {
		LoggerImpl logger1 = new LoggerImpl(LOGCATEGORY);
		StringBuilder sb = new StringBuilder());
		sb.append("Test");
		sb.append("LoggerImpl");
		LoggerImpl logger2 = new LoggerImpl(sb.toString());
		sb.append("3");
		LoggerImpl logger3 = new LoggerImpl(sb.toString());
		assertEquals(logger1, logger2);
		assertFalse(logger1.equals(logger3));
		assertFalse(logger1.equals(null));
		try {
			logger1.equals("abc");
			fail("expected ClassCastException");
		}
		catch(ClassCastException ex) {
			assertTrue(ex instanceof ClassCastException);
		}
	}


//	private <T> void createLoggingExpectations(final LogLevel expectedLogLevel, final T expectedLogMessage, final Throwable expectedException) {
//		new NonStrictExpectations() {
//			{
//				slf4jLlogger.logInternal(withEqual(expectedLogLevel), (Loggable)any); times = 1;
//			}
//		};
//	}
//	private <T> void createLoggingVerifications(final LogLevel expectedLogLevel, final T expectedLogMessage, final Throwable expectedException) {
//		new Verifications() {
//			{
//				slf4jLlogger.logInternal(withEqual(expectedLogLevel), (Loggable)any); times = 1;
//				forEachInvocation = new Object() {
//					@SuppressWarnings("unused")
//					public void validate(LogLevel logLevel, Loggable loggable) {
//						StringBuffer sb = new StringBuffer();
//						loggable.append(sb);
//						assertTrue(expectedLogLevel.equals(logLevel));
//						assertTrue(expectedLogMessage.toString().equals(sb.toString()));
//						if (expectedException == null)
//							assertNull(loggable.getException());
//						else
//							assertEquals(expectedException, loggable.getException());
//					}
//				};
//			}
//		};
//	}
//
	
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#loaded()}.
	 */
	@Test
	public void testLoaded1(){
		new Expectations() {
			{
				slf4jLogger.isTraceEnabled(); result = true; times = 1;
				slf4jLogger.trace("loaded with RCS Id RCSID"); times = 1;
			}
		};
		tested.loaded("RCSID", TestLogger.class);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#loaded()}.
	 */
	@Test
	public void testLoaded2(){
		new Expectations() {
			{
				slf4jLogger.isTraceEnabled(); result = false; times = 1;
				slf4jLogger.trace("loaded with RCS Id RCSID"); times = 0;
			}
		};
		tested.loaded("RCSID", TestLogger.class);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#trace()}.
	 */
	@Test
	public void testTrace1() {
		new Expectations() {
			{
				slf4jLogger.isTraceEnabled(); result = true; times = 1;
				slf4jLogger.trace(LOGSTRING, TESTEXCEPTION); times = 1;
			}
		};
		tested.trace(LOGSTRING, TESTEXCEPTION);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#trace()}.
	 */
	@Test
	public void testTrace2() {
		new Expectations() {
			{
				slf4jLogger.isTraceEnabled(); result = false; times = 1;
				slf4jLogger.trace(LOGSTRING); times = 0;
			}
		};
		tested.trace(LOGSTRING);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#debug()}.
	 */
	@Test
	public void testDebug1() {
		new Expectations() {
			{
				slf4jLogger.isDebugEnabled(); result = true; times = 1;
				slf4jLogger.debug(LOGSTRING, TESTEXCEPTION); times = 1;
			}
		};
		tested.debug(LOGSTRING, TESTEXCEPTION);
	}
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#debug()}.
	 */
	@Test
	public void testDebug2() {
		new Expectations() {
			{
				slf4jLogger.isDebugEnabled(); result = false; times = 1;
				slf4jLogger.debug(LOGSTRING); times = 0;
			}
		};
		tested.debug(LOGSTRING);
	}
	

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#info()}.
	 */
	@Test
	public void testInfo1() {
		new Expectations() {
			{
				slf4jLogger.isInfoEnabled(); result = true; times = 1;
				slf4jLogger.info(LOGSTRING, TESTEXCEPTION); times = 1;
			}
		};
		tested.info(LOGSTRING, TESTEXCEPTION);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#info()}.
	 */
	@Test
	public void testInfo2() {
		new Expectations() {
			{
				slf4jLogger.isInfoEnabled(); result = false; times = 1;
				slf4jLogger.info(LOGSTRING); times = 0;
			}
		};
		tested.info(LOGSTRING);
	}
	
	
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#warn()}.
	 */
	@Test
	public void testWarn1() {
		new Expectations() {
			{
				slf4jLogger.isWarnEnabled(); result = true; times = 1;
				slf4jLogger.warn(LOGSTRING, TESTEXCEPTION); times = 1;
			}
		};
		tested.warn(LOGSTRING, TESTEXCEPTION);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#warn()}.
	 */
	@Test
	public void testWarn2() {
		new Expectations() {
			{
				slf4jLogger.isWarnEnabled(); result = false; times = 1;
				slf4jLogger.warn(LOGSTRING); times = 0;
			}
		};
		tested.warn(LOGSTRING);
	}
	

	
	
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#error()}.
	 */
	@Test
	public void testError1() {
		new Expectations() {
			{
				slf4jLogger.isErrorEnabled(); result = true; times = 1;
				slf4jLogger.error(LOGSTRING, TESTEXCEPTION); times = 1;
			}
		};
		tested.error(LOGSTRING, TESTEXCEPTION);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#error()}.
	 */
	@Test
	public void testError2() {
		new Expectations() {
			{
				slf4jLogger.isErrorEnabled(); result = false; times = 1;
				slf4jLogger.error(LOGSTRING); times = 0;
			}
		};
		tested.error(LOGSTRING);
	}
	

	
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#fatal()}.
	 */
	@Test
	public void testFatal1() {
		new Expectations() {
			{
				slf4jLogger.isErrorEnabled(); result = true; times = 1;
				slf4jLogger.error(LOGSTRING, TESTEXCEPTION); times = 1;
			}
		};
		tested.fatal(LOGSTRING, TESTEXCEPTION);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.ILogger#fatal()}.
	 */
	@Test
	public void testFatal2() {
		new Expectations() {
			{
				slf4jLogger.isErrorEnabled(); result = false; times = 1;
				slf4jLogger.error(LOGSTRING, (Exception)withNotNull()); times = 0;
			}
		};
		tested.fatal(LOGSTRING);
	}

}
