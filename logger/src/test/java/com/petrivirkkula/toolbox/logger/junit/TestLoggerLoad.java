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

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class TestLoggerLoad
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id$";


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}


	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#getLogger}.
	 */
	@Test
	public void testGetLogger() {
		Logger log1 = Logger.getLogger(getClass().getName());
		Logger log2 = Logger.getLogger(getClass().getName());
		assertEquals(log1, log2);
		Logger log3 = Logger.getLogger(getClass());
		Logger log4 = Logger.getLogger(getClass());
		assertEquals(log3, log4);
		assertEquals(log1,log3);
	}



	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#loaded(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testStaticLoaded() throws Exception {
		Class.forName("com.petrivirkkula.toolbox.logger.junit.StaticLogLoaded");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#loaded(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testLoaded() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#trace}.
	 */
	@Test
	public void testTrace() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#debug}.
	 */
	@Test
	public void testDebug() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#info}.
	 */
	@Test
	public void testInfo() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#warn}.
	 */
	@Test
	public void testWarn() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#error}.
	 */
	@Test
	public void testError() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#fatal}.
	 */
	@Test
	public void testFatal() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.String)}.
	 */
	@Test
	public void testLogLogLevelString() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.Object)}.
	 */
	@Test
	public void testLogLogLevelObject() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.Object, java.lang.Throwable)}.
	 */
	@Test
	public void testLogLogLevelObjectThrowable() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testLogLogLevelStringThrowable() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testLogLogLevelStringObjectArray() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#log(com.petrivirkkula.toolbox.logger.LogLevel, java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testLogLogLevelThrowableStringObjectArray() {
		//fail("Not yet implemented");
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.Logger#log(com.petrivirkkula.toolbox.logger.LogLevel, com.petrivirkkula.toolbox.logger.Loggable)}.
	 */
	@Test
	public void testLogLogLevelLoggable() {
		//fail("Not yet implemented");
	}

}
