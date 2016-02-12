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

package com.petrivirkkula.toolbox.logger;


import java.util.Locale;
import java.io.IOException;
import java.text.MessageFormat;


/**
 * Loggable that formats message based on format string and message arguments.
 *
 * @see			java.text.MessageFormat
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class FormatterLoggable extends AbstractLoggable implements ExtendedLoggable
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id$";


	/**
	 * Serialization version uid.
	 */
	public static final long serialVersionUID = 20101126L;


	/**
	 * Locate to use.
	 */
	private Locale locale;


	/**
	 * Message format.
	 */
	private final String format;


	/**
	 * Message args.
	 */
	private final Object[] args;


	/**
	 * Constructor with exception, format and args parameters.
	 *
	 * @param exception	exception associted with this log message
	 * @param format	format of this log message
	 * @param args	message args
	 */
	public FormatterLoggable(Throwable exception, String format, Object[] args) {
		super(exception);
		this.format = format;
		this.args = args;
	}


	/**
	 * Constructor with format and args parameters.
	 *
	 * @param format	format of this log message
	 * @param args	message args
	 */
	public FormatterLoggable(String format, Object[] args) {
		super(null);
		this.format = format;
		this.args = args;
	}


	/**
	 * Constructor with exception, locale, format and args parameters.
	 *
	 * @param exception	exception associted with this log message
	 * @param locale	locale to use
	 * @param format	format of this log message
	 * @param args	message args
	 */
	public FormatterLoggable(Throwable exception, Locale locale, String format, Object[] args) {
		super(exception);
		this.locale = locale;
		this.format = format;
		this.args = args;
	}

	/**
	 * Constructor with locale, format and args parameters.
	 *
	 * @param locale	locale to use
	 * @param format	format of this log message
	 * @param args	message args
	 */
	public FormatterLoggable(Locale locale, String format, Object[] args) {
		super(null);
		this.locale = locale;
		this.format = format;
		this.args = args;
	}


	/**
	 * Appends this loggable to a string buffer.
	 * 
	 * @param	buffer		buffer (e.g. string buffer)
	 * @return	the argument string buffer
	 */
	public Appendable append(Appendable buffer) {
		try {
			buffer.append(toLogMessage());
		}
		catch(IOException ex) {
			ex = null;
		}
		return buffer;
	}


	/**
	 * Convert this loggable to log message.
	 * 
	 * @return	log message string
	 */
	public String toLogMessage() {
		MessageFormat formatter = null;
		if (locale == null)
			formatter = new MessageFormat(format);
		else
			formatter = new MessageFormat(format, locale);
		return formatter.format(args);
	}


	/**
	 * Gets message locale
	 * 
	 * @return	message locale or null if not set
	 */
	public Locale getLocale() {
		return locale;
	}


	/**
	 * Sets message locale.
	 * 
	 * @param locale	new locale to use
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
