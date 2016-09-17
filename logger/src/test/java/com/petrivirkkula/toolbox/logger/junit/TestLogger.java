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

import java.io.IOException;

import mockit.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.logger.LogContext;
import com.petrivirkkula.toolbox.logger.LogLevel;
import com.petrivirkkula.toolbox.logger.Loggable;
import com.petrivirkkula.toolbox.logger.ExtendedLoggable;
import com.petrivirkkula.toolbox.logger.Logger;
import com.petrivirkkula.toolbox.logger.SimpleLoggable;
import com.petrivirkkula.toolbox.logger.impl.LoggerImpl;

/**
 * @author petri
 *
 */
public class TestLogger
{

	private Logger tested;

	private static class TestException extends Exception {
		private static final long serialVersionUID = 1L; 		
	};
	
	private static final TestException testException = new TestException();
	
	private static final String LOGSTRING = "log message";
	private static final Object LOGOBJECT = new Object();
	private static final String LOGFORMAT = "{0} {1}";
	private static final String LOGARG1 = "log";
	private static final String LOGARG2 = "message";
	private static Loggable LOGGABLE1;
	private static Loggable LOGGABLE2;
	
	@Mocked(methods="logInternal",inverse=false) LoggerImpl loggerImpl;

	@Before
	public void setUp() {
		tested = Logger.getLogger("TestLogger");
		LOGGABLE1 = new SimpleLoggable(LOGSTRING);
		LOGGABLE2 = new SimpleLoggable(testException, LOGSTRING);
	}
	

	private <T> void createLoggingExpectations(final LogLevel expectedLogLevel, final T expectedLogMessage, final Throwable expectedException) {
		new NonStrictExpectations() {
			{
				loggerImpl.logInternal(withEqual(expectedLogLevel), (Loggable)any); times = 1;
			}
		};
	}
	private <T> void createLoggingVerifications(final LogLevel expectedLogLevel, final T expectedLogMessage, final Throwable expectedException) {
		new Verifications() {
			{
				loggerImpl.logInternal(withEqual(expectedLogLevel), (Loggable)any); times = 1;
				forEachInvocation = new Object() {
					@SuppressWarnings("unused")
					public void validate(LogLevel logLevel, ExtendedLoggable loggable) {
						StringBuilder sb = new StringBuilder();
						loggable.append(sb);
						assertTrue(expectedLogLevel.equals(logLevel));
						assertTrue(expectedLogMessage.toString().equals(sb.toString()));
						if (expectedException == null)
							assertNull(loggable.getException());
						else
							assertEquals(expectedException, loggable.getException());
					}
				};
			}
		};
	}

	
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#loaded(java.lang.String, java.lang.Class)}.
	 * @throws IOException 
	 */
	@Test
	public void testLoaded(){
		tested.loaded("RCSID", TestLogger.class);
		createLoggingVerifications(LogLevel.LOADED, "loaded with RCS Id RCSID", null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#trace(java.lang.String)}.
	 */
	@Test
	public void testTraceObject() {
		createLoggingExpectations(LogLevel.TRACE, LOGOBJECT, null);
		tested.trace(LOGOBJECT);
		createLoggingVerifications(LogLevel.TRACE, LOGOBJECT, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#trace(java.lang.String)}.
	 */
	@Test
	public void testTraceString() {
		createLoggingExpectations(LogLevel.TRACE, LOGSTRING, null);
		tested.trace(LOGSTRING);
		createLoggingVerifications(LogLevel.TRACE, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#trace(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testTraceLoggable1() {
		createLoggingExpectations(LogLevel.TRACE, LOGGABLE1, null);
		tested.trace(LOGGABLE1);
		createLoggingVerifications(LogLevel.TRACE, LOGGABLE1, null);
	}

	@Test
	public void testTraceLoggable2() {
		createLoggingExpectations(LogLevel.TRACE, LOGGABLE2, testException);
		tested.trace(LOGGABLE2);
		createLoggingVerifications(LogLevel.TRACE, LOGGABLE2, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#trace(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testTraceObjectThrowable() {
		createLoggingExpectations(LogLevel.TRACE, LOGOBJECT, testException);
		tested.trace(LOGOBJECT, testException);
		createLoggingVerifications(LogLevel.TRACE, LOGOBJECT, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#trace(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testTraceStringThrowable() {
		createLoggingExpectations(LogLevel.TRACE, LOGSTRING, testException);
		tested.trace(LOGSTRING, testException);
		createLoggingVerifications(LogLevel.TRACE, LOGSTRING, testException);
	}

//	/**
//	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#trace(com.petrivirkkula.toolbox.logger.Loggable, java.lang.Throwable)}.
//	 */
//	@Test
//	public void testTraceLoggableThrowable() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#trace(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testTraceStringObjectArray() {
		createLoggingExpectations(LogLevel.TRACE, LOGSTRING, null);
		tested.trace(LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.TRACE, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#trace(java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testTraceThrowableStringObjectArray() {
		createLoggingExpectations(LogLevel.TRACE, LOGSTRING, testException);
		tested.trace(testException, LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.TRACE, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#debug(java.lang.String)}.
	 */
	@Test
	public void testDebugObject() {
		createLoggingExpectations(LogLevel.DEBUG, LOGOBJECT, null);
		tested.debug(LOGOBJECT);
		createLoggingVerifications(LogLevel.DEBUG, LOGOBJECT, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#debug(java.lang.String)}.
	 */
	@Test
	public void testDebugString() {
		createLoggingExpectations(LogLevel.DEBUG, LOGSTRING, null);
		tested.debug(LOGSTRING);
		createLoggingVerifications(LogLevel.DEBUG, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#debug(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testDebugLoggable1() {
		createLoggingExpectations(LogLevel.DEBUG, LOGGABLE1, null);
		tested.debug(LOGGABLE1);
		createLoggingVerifications(LogLevel.DEBUG, LOGGABLE1, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#debug(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testDebugLoggable2() {
		createLoggingExpectations(LogLevel.DEBUG, LOGGABLE2, testException);
		tested.debug(LOGGABLE2);
		createLoggingVerifications(LogLevel.DEBUG, LOGGABLE2, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#debug(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testDebugObjectThrowable() {
		createLoggingExpectations(LogLevel.DEBUG, LOGOBJECT, testException);
		tested.debug(LOGOBJECT, testException);
		createLoggingVerifications(LogLevel.DEBUG, LOGOBJECT, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#debug(com.petrivirkkula.toolbox.logger.Loggable, java.lang.Throwable)}.
	 */
	@Test
	public void testDebugStringThrowable() {
		createLoggingExpectations(LogLevel.DEBUG, LOGSTRING, testException);
		tested.debug(LOGSTRING, testException);
		createLoggingVerifications(LogLevel.DEBUG, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#debug(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testDebugStringObjectArray() {
		createLoggingExpectations(LogLevel.DEBUG, LOGSTRING, null);
		tested.debug(LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.DEBUG, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#debug(java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testDebugThrowableStringObjectArray() {
		createLoggingExpectations(LogLevel.DEBUG, LOGSTRING, testException);
		tested.debug(testException, LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.DEBUG, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#info(java.lang.String)}.
	 */
	@Test
	public void testInfoObject() {
		createLoggingExpectations(LogLevel.INFO, LOGOBJECT, null);
		tested.info(LOGOBJECT);
		createLoggingVerifications(LogLevel.INFO, LOGOBJECT, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#info(java.lang.String)}.
	 */
	@Test
	public void testInfoString() {
		createLoggingExpectations(LogLevel.INFO, LOGSTRING, null);
		tested.info(LOGSTRING);
		createLoggingVerifications(LogLevel.INFO, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#info(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testInfoLoggable1() {
		createLoggingExpectations(LogLevel.DEBUG, LOGGABLE1, null);
		tested.debug(LOGGABLE1);
		createLoggingVerifications(LogLevel.DEBUG, LOGGABLE1, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#info(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testInfoLoggable2() {
		createLoggingExpectations(LogLevel.INFO, LOGGABLE2, testException);
		tested.info(LOGGABLE2);
		createLoggingVerifications(LogLevel.INFO, LOGGABLE2, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#info(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testInfoObjectThrowable() {
		createLoggingExpectations(LogLevel.INFO, LOGOBJECT, testException);
		tested.info(LOGOBJECT, testException);
		createLoggingVerifications(LogLevel.INFO, LOGOBJECT, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#info(com.petrivirkkula.toolbox.logger.Loggable, java.lang.Throwable)}.
	 */
	@Test
	public void testInfoStringThrowable() {
		createLoggingExpectations(LogLevel.INFO, LOGSTRING, testException);
		tested.info(LOGSTRING, testException);
		createLoggingVerifications(LogLevel.INFO, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#info(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testInfoStringObjectArray() {
		createLoggingExpectations(LogLevel.INFO, LOGSTRING, null);
		tested.info(LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.INFO, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#info(java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testInfoThrowableStringObjectArray() {
		createLoggingExpectations(LogLevel.INFO, LOGSTRING, testException);
		tested.info(testException, LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.INFO, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#warn(java.lang.String)}.
	 */
	@Test
	public void testWarnObject() {
		createLoggingExpectations(LogLevel.WARN, LOGOBJECT, null);
		tested.warn(LOGOBJECT);
		createLoggingVerifications(LogLevel.WARN, LOGOBJECT, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#warn(java.lang.String)}.
	 */
	@Test
	public void testWarnString() {
		createLoggingExpectations(LogLevel.WARN, LOGSTRING, null);
		tested.warn(LOGSTRING);
		createLoggingVerifications(LogLevel.WARN, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#warn(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testWarnLoggable1() {
		createLoggingExpectations(LogLevel.WARN, LOGGABLE1, null);
		tested.warn(LOGGABLE1);
		createLoggingVerifications(LogLevel.WARN, LOGGABLE1, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#warn(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testWarnLoggable2() {
		createLoggingExpectations(LogLevel.WARN, LOGGABLE2, testException);
		tested.warn(LOGGABLE2);
		createLoggingVerifications(LogLevel.WARN, LOGGABLE2, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#warn(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testWarnObjectThrowable() {
		createLoggingExpectations(LogLevel.WARN, LOGOBJECT, testException);
		tested.warn(LOGOBJECT, testException);
		createLoggingVerifications(LogLevel.WARN, LOGOBJECT, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#warn(com.petrivirkkula.toolbox.logger.Loggable, java.lang.Throwable)}.
	 */
	@Test
	public void testWarnStringThrowable() {
		createLoggingExpectations(LogLevel.WARN, LOGSTRING, testException);
		tested.warn(LOGSTRING, testException);
		createLoggingVerifications(LogLevel.WARN, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#warn(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testWarnStringObjectArray() {
		createLoggingExpectations(LogLevel.WARN, LOGSTRING, null);
		tested.warn(LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.WARN, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#warn(java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testWarnThrowableStringObjectArray() {
		createLoggingExpectations(LogLevel.WARN, LOGSTRING, testException);
		tested.warn(testException, LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.WARN, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#error(java.lang.String)}.
	 */
	@Test
	public void testErrorObject() {
		createLoggingExpectations(LogLevel.ERROR, LOGOBJECT, null);
		tested.error(LOGOBJECT);
		createLoggingVerifications(LogLevel.ERROR, LOGOBJECT, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#error(java.lang.String)}.
	 */
	@Test
	public void testErrorString() {
		createLoggingExpectations(LogLevel.ERROR, LOGSTRING, null);
		tested.error(LOGSTRING);
		createLoggingVerifications(LogLevel.ERROR, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#error(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testErrorLoggable1() {
		createLoggingExpectations(LogLevel.ERROR, LOGGABLE1, null);
		tested.error(LOGGABLE1);
		createLoggingVerifications(LogLevel.ERROR, LOGGABLE1, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#error(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testErrorLoggable2() {
		createLoggingExpectations(LogLevel.ERROR, LOGGABLE2, testException);
		tested.error(LOGGABLE2);
		createLoggingVerifications(LogLevel.ERROR, LOGGABLE2, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#error(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testErrorObjectThrowable() {
		createLoggingExpectations(LogLevel.ERROR, LOGOBJECT, testException);
		tested.error(LOGOBJECT, testException);
		createLoggingVerifications(LogLevel.ERROR, LOGOBJECT, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#error(com.petrivirkkula.toolbox.logger.Loggable, java.lang.Throwable)}.
	 */
	@Test
	public void testErrorStringThrowable() {
		createLoggingExpectations(LogLevel.ERROR, LOGSTRING, testException);
		tested.error(LOGSTRING, testException);
		createLoggingVerifications(LogLevel.ERROR, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#error(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testErrorStringObjectArray() {
		createLoggingExpectations(LogLevel.ERROR, LOGSTRING, null);
		tested.error(LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.ERROR, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#error(java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testErrorThrowableStringObjectArray() {
		createLoggingExpectations(LogLevel.ERROR, LOGSTRING, testException);
		tested.error(testException, LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.ERROR, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(java.lang.Object)}.
	 */
	@Test
	public void testFatalObject() {
		createLoggingExpectations(LogLevel.FATAL, LOGOBJECT, null);
		tested.fatal(LOGOBJECT);
		createLoggingVerifications(LogLevel.FATAL, LOGOBJECT, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(java.lang.String)}.
	 */
	@Test
	public void testFatalString() {
		createLoggingExpectations(LogLevel.FATAL, LOGSTRING, null);
		tested.fatal(LOGSTRING);
		createLoggingVerifications(LogLevel.FATAL, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testFatalLoggable1_0() {
		createLoggingExpectations(LogLevel.FATAL, LOGGABLE1, null);
		tested.fatal((Object)LOGGABLE1);
		createLoggingVerifications(LogLevel.FATAL, LOGGABLE1, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testFatalLoggable1() {
		createLoggingExpectations(LogLevel.FATAL, LOGGABLE1, null);
		tested.fatal(LOGGABLE1);
		createLoggingVerifications(LogLevel.FATAL, LOGGABLE1, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testFatalLoggable2_0() {
		createLoggingExpectations(LogLevel.FATAL, LOGGABLE2, testException);
		tested.fatal((Object)LOGGABLE2);
		createLoggingVerifications(LogLevel.FATAL, LOGGABLE2, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testFatalLoggable2() {
		createLoggingExpectations(LogLevel.FATAL, LOGGABLE2, testException);
		tested.fatal(LOGGABLE2);
		createLoggingVerifications(LogLevel.FATAL, LOGGABLE2, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(java.lang.Object, java.lang.Throwable)}.
	 */
	@Test
	public void testFatalObjectThrowable() {
		createLoggingExpectations(LogLevel.FATAL, LOGOBJECT, testException);
		tested.fatal(LOGOBJECT, testException);
		createLoggingVerifications(LogLevel.FATAL, LOGOBJECT, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testFatalStringThrowable() {
		createLoggingExpectations(LogLevel.FATAL, LOGSTRING, testException);
		tested.fatal(LOGSTRING, testException);
		createLoggingVerifications(LogLevel.FATAL, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(com.petrivirkkula.toolbox.logger.Loggable, java.lang.Throwable)}.
	 */
	@Test
	public void testFatalLoggableThrowable() {
		createLoggingExpectations(LogLevel.FATAL, LOGGABLE2, testException);
		tested.fatal(LOGGABLE2, testException);
		createLoggingVerifications(LogLevel.FATAL, LOGGABLE2, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testFatalStringObjectArray() {
		createLoggingExpectations(LogLevel.FATAL, LOGSTRING, null);
		tested.fatal(LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.FATAL, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#fatal(java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testFatalThrowableStringObjectArray() {
		createLoggingExpectations(LogLevel.FATAL, LOGSTRING, testException);
		tested.fatal(testException, LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.FATAL, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.Object)}.
	 */
	@Test
	public void testLogLogLevelObject() {
		createLoggingExpectations(LogLevel.FATAL, LOGOBJECT, null);
		tested.log(LogLevel.FATAL, LOGOBJECT);
		createLoggingVerifications(LogLevel.FATAL, LOGOBJECT, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.String)}.
	 */
	@Test
	public void testLogLogLevelString() {
		createLoggingExpectations(LogLevel.FATAL, LOGSTRING, null);
		tested.log(LogLevel.FATAL, LOGSTRING);
		createLoggingVerifications(LogLevel.FATAL, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.Object, java.lang.Throwable)}.
	 */
	@Test
	public void testLogLogLevelObjectThrowable() {
		createLoggingExpectations(LogLevel.FATAL, LOGOBJECT, testException);
		tested.log(LogLevel.FATAL,LOGOBJECT, testException);
		createLoggingVerifications(LogLevel.FATAL, LOGOBJECT, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testLogLogLevelStringThrowable() {
		createLoggingExpectations(LogLevel.FATAL, LOGSTRING, testException);
		tested.log(LogLevel.FATAL, LOGSTRING, testException);
		createLoggingVerifications(LogLevel.FATAL, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testLogLogLevelStringObjectArray() {
		createLoggingExpectations(LogLevel.FATAL, LOGSTRING, null);
		tested.log(LogLevel.FATAL, LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.FATAL, LOGSTRING, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testLogLogLevelThrowableStringObjectArray() {
		createLoggingExpectations(LogLevel.FATAL, LOGSTRING, testException);
		tested.log(LogLevel.FATAL, testException, LOGFORMAT, LOGARG1, LOGARG2);
		createLoggingVerifications(LogLevel.FATAL, LOGSTRING, testException);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#log(com.petrivirkkula.toolbox.logger.LogLevel, com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testLogLogLevelLoggable1() {
		createLoggingExpectations(LogLevel.FATAL, LOGGABLE1, null);
		tested.log(LogLevel.FATAL, LOGGABLE1);
		createLoggingVerifications(LogLevel.FATAL, LOGGABLE1, null);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.AbstractLogger#log(com.petrivirkkula.toolbox.logger.LogLevel, com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testLogLogLevelLoggable2() {
		createLoggingExpectations(LogLevel.FATAL, LOGGABLE2, testException);
		tested.log(LogLevel.FATAL, LOGGABLE2);
		createLoggingVerifications(LogLevel.FATAL, LOGGABLE2, testException);
	}

	
	private LogContext context2 = null;
	
	@Test
	public void testGetCurrentLogContext() throws InterruptedException {
		LogContext context1 = Logger.getCurrentLogContext();
		context1.setAttribute("key1", "value1");
		assertNotNull(context1.getAttribute("key1"));
		Thread thread = new Thread(new Runnable() { public void run() { context2 = Logger.getCurrentLogContext(); }});
		thread.start();
		thread.join();
		assertNotNull(context1);
		assertNotNull(context2);
		assertNotSame(context1, context2);
		assertNotNull(context1.getAttribute("key1"));
		assertNotNull(context2.getAttribute("key1"));
		assertEquals(context1.getAttribute("key1"), context2.getAttribute("key1"));
		context1.setAttribute("key2", "value2");
		assertNotNull(context1.getAttribute("key2"));
		assertNull(context2.getAttribute("key2"));
		context2.setAttribute("key3", "value3");
		assertNull(context1.getAttribute("key3"));
		assertNotNull(context2.getAttribute("key3"));
	}
}
