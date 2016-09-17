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

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.logger.ExtendedLoggable;
import com.petrivirkkula.toolbox.logger.SimpleLoggable;

/**
 * @author petri
 *
 */
public class TestSimpleLoggable
{

	private static class TestException extends Exception {
		private static final long serialVersionUID = 1L; 		
	};
	
	private static final String LOGSTRING = "log message";
	private static final Object LOGOBJECT = new Object();
	private static ExtendedLoggable LOGGABLE1;
	private static ExtendedLoggable LOGGABLE2;

	private static final TestException TESTEXCEPTION = new TestException();


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LOGGABLE1 = new SimpleLoggable(LOGSTRING);
		LOGGABLE2 = new SimpleLoggable(TESTEXCEPTION, LOGSTRING);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#SimpleLoggable(java.lang.Object)}.
	 */
	@Test
	public void testSimpleLoggableObject0() {
		SimpleLoggable loggable = new SimpleLoggable(LOGOBJECT);
		StringBuffer sb = new StringBuffer();
		loggable.append(sb);
		assertTrue(LOGOBJECT.toString().equals(sb.toString()));
	}
	
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#SimpleLoggable(java.lang.Object)}.
	 */
	@Test
	public void testSimpleLoggableObject1() {
		SimpleLoggable loggable = new SimpleLoggable(LOGGABLE1);
		StringBuffer sb = new StringBuffer();
		loggable.append(sb);
		assertTrue(LOGGABLE1.toString().equals(sb.toString()));
		assertSame(LOGGABLE1.getException(), loggable.getException());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#SimpleLoggable(java.lang.Object)}.
	 */
	@Test
	public void testSimpleLoggableObject2() {
		SimpleLoggable loggable = new SimpleLoggable(LOGGABLE2);
		StringBuffer sb = new StringBuffer();
		loggable.append(sb);
		assertTrue(LOGGABLE2.toString().equals(sb.toString()));
		assertSame(LOGGABLE2.getException(), loggable.getException());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#append(java.lang.Appendable)}.
	 */
	@Test
	public void testAppend1() {
		SimpleLoggable loggable = new SimpleLoggable(LOGSTRING);
		StringBuffer sb = new StringBuffer();
		loggable.append(sb);
		assertTrue(LOGSTRING.equals(sb.toString()));
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#append(java.lang.Appendable)}.
	 * @throws IOException 
	 */
	@Test
	public void testAppend2() throws IOException {
		PipedWriter writer = null;
		Reader reader = null;
		try {
			SimpleLoggable loggable = new SimpleLoggable(LOGSTRING);
			writer = new PipedWriter();
			reader = new PipedReader(writer);
			loggable.append(writer);
			writer.flush();
			char cbuf1[] = new char[LOGSTRING.length()];
			int count1 = reader.read(cbuf1);
			assertTrue(count1 == LOGSTRING.length());
			assertTrue(LOGSTRING.equals(new String(cbuf1)));
			writer.close();
			loggable.append(writer); // IOException should be caught inside
			char cbuf2[] = new char[LOGSTRING.length()];
			int count2 = reader.read(cbuf2);
			assertTrue(count2 == -1);
		}
		finally {
			if (reader != null) {
				try { reader.close(); } catch(Exception ex) { ex = null; }
			}
			if (writer != null) {
				try { writer.close(); } catch(Exception ex) { ex = null; }
			}
		}
	}

}
