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


/**
 * Log level class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class LogLevel implements java.io.Serializable, Comparable<LogLevel>
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id$";

	/**
	 * Serialization Version UID
	 */
	private static final long serialVersionUID = 20130126L;


	/**
	 * Name of log category LOADED.
	 */
	public static final String _LOADED_NAME = "LOADED";

	/**
	 * Value of log category LOADED.
	 */
	public static final int _LOADED_VALUE = 16384;

	/**
	 * Log category LOADED.
	 */
	public static final LogLevel LOADED = new LogLevel(_LOADED_NAME, _LOADED_VALUE);


	public static final String _TRACE_NAME = "TRACE";
	public static final int _TRACE_VALUE = 4096;
	public static final LogLevel TRACE = new LogLevel(_TRACE_NAME, _TRACE_VALUE);

	public static final String _DEBUG_NAME = "DEBUG";
	public static final int _DEBUG_VALUE = 1024;
	public static final LogLevel DEBUG = new LogLevel(_DEBUG_NAME, _DEBUG_VALUE);

	public static final String _INFO_NAME = "INFO";
	public static final int _INFO_VALUE = 512;
	public static final LogLevel INFO = new LogLevel(_INFO_NAME, _INFO_VALUE);

	public static final String _NOTICE_NAME = "NOTICE";
	public static final int _NOTICE_VALUE = 256;
	public static final LogLevel NOTICE = new LogLevel(_NOTICE_NAME, _NOTICE_VALUE);

	public static final String _AUDIT_NAME = "AUDIT";
	public static final int _AUDIT_VALUE = 128;
	public static final LogLevel AUDIT = new LogLevel(_AUDIT_NAME, _AUDIT_VALUE);

	public static final String _WARN_NAME = "WARN";
	public static final int _WARN_VALUE = 64;
	public static final LogLevel WARN = new LogLevel(_WARN_NAME, _WARN_VALUE);

	public static final String _ERROR_NAME = "ERROR";
	public static final int _ERROR_VALUE = 32;
	public static final LogLevel ERROR = new LogLevel(_ERROR_NAME, _ERROR_VALUE);

	public static final String _FATAL_NAME = "FATAL";
	public static final int _FATAL_VALUE = 16;
	public static final LogLevel FATAL = new LogLevel(_FATAL_NAME, _FATAL_VALUE);


	private final String levelName;

	private final int levelValue;



	/**
	 * Constructor with log level name and log level value parameters.
	 * 
	 * @param levelName		log level name
	 * @param levelValue	log level integer value
	 */
	protected LogLevel(String levelName, int levelValue) {
		this.levelName = levelName;
		this.levelValue = levelValue;
		if (levelName == null)
			throw new IllegalArgumentException("level name is null");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof LogLevel))
			throw new ClassCastException("obj is not instance of LogLevel");
		LogLevel other = (LogLevel) obj;
		if (getLevelValue() == other.getLevelValue())
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return getLevelValue();
	}

	@Override
	public int compareTo(LogLevel other) {
		int c = getLevelValue() - other.getLevelValue();
		if (c != 0)
			return c;
		return getLevelName().compareTo(other.getLevelName());
	}

	/**
	 * Gets the log level name.
	 * 
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}


	/**
	 * Gets log level numeric value.
	 * 
	 * @return the levelValue
	 */
	public int getLevelValue() {
		return levelValue;
	}


}
