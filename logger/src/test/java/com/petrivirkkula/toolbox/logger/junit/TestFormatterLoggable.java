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
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.util.Locale;

import static org.junit.Assert.*;

import org.junit.Test;

import com.petrivirkkula.toolbox.logger.FormatterLoggable;

/**
 * @author Petri Virkkula
 *
 */
public class TestFormatterLoggable
{
	private static class TestException extends Exception {
		private static final long serialVersionUID = 1L; 		
	};
	
	private static final String LOGSTRING = "log message";
	private static final String LOGFORMAT = "{0} {1}";
	private static final String LOGARG1 = "log";
	private static final String LOGARG2 = "message";
	private static final String LOGARGS[] = { LOGARG1, LOGARG2 };

	private static final TestException TESTEXCEPTION = new TestException();

	private static final Locale TESTLOCALE = Locale.ENGLISH;
	
	
	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#FormatterLoggable(java.lang.Throwable, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testFormatterLoggableThrowableStringObjectArray() {
		FormatterLoggable loggable = new FormatterLoggable(TESTEXCEPTION, LOGFORMAT, LOGARGS);
		assertSame(null, loggable.getLocale());
		assertTrue(LOGSTRING.equals(loggable.toString()));
		assertSame(TESTEXCEPTION, loggable.getException());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#FormatterLoggable(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testFormatterLoggableStringObjectArray() {
		FormatterLoggable loggable = new FormatterLoggable(LOGFORMAT, LOGARGS);
		assertSame(null,loggable.getLocale());
		assertTrue(LOGSTRING.equals(loggable.toString()));
		assertSame(null, loggable.getException());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#FormatterLoggable(java.lang.Throwable, java.util.Locale, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testFormatterLoggableThrowableLocaleStringObjectArray() {
		FormatterLoggable loggable = new FormatterLoggable(TESTEXCEPTION, TESTLOCALE, LOGFORMAT, LOGARGS);
		assertSame(TESTLOCALE, loggable.getLocale());
		assertTrue(LOGSTRING.equals(loggable.toString()));
		assertSame(TESTEXCEPTION, loggable.getException());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#FormatterLoggable(java.util.Locale, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testFormatterLoggableLocaleStringObjectArray() {
		FormatterLoggable loggable = new FormatterLoggable(TESTLOCALE, LOGFORMAT, LOGARGS);
		assertSame(TESTLOCALE, loggable.getLocale());
		assertTrue(LOGSTRING.equals(loggable.toString()));
		assertSame(null, loggable.getException());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#append(java.lang.Appendable)}.
	 */
	@Test
	public void testAppend1() {
		FormatterLoggable loggable = new FormatterLoggable(LOGFORMAT, LOGARGS);
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
			FormatterLoggable loggable = new FormatterLoggable(LOGFORMAT, LOGARGS);
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

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#toLogMessage()}.
	 */
	@Test
	public void testToLogMessage() {
		FormatterLoggable loggable = new FormatterLoggable(LOGFORMAT, LOGARGS);
		assertSame(null,loggable.getLocale());
		assertTrue(LOGSTRING.equals(loggable.toLogMessage()));
		assertSame(null, loggable.getException());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#setLocale(java.util.Locale)}
	 * and {@link com.petrivirkkula.toolbox.logger.FormatterLoggable#getLocale()}.
	 */
	@Test
	public void testGetSetLocale() {
		FormatterLoggable loggable = new FormatterLoggable(TESTLOCALE, LOGFORMAT, LOGARGS);
		assertSame(TESTLOCALE, loggable.getLocale());
		loggable.setLocale(null);
		assertSame(null, loggable.getLocale());
		loggable.setLocale(TESTLOCALE);
		assertSame(TESTLOCALE, loggable.getLocale());
	}

}
