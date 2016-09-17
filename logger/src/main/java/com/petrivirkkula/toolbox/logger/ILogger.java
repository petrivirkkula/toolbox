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
 * Logging interface.
 * 
 * @author		Petri Virkkula 
 * @version		$Id$
 */
public interface ILogger
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";




	// ============================================================
	// Logging Methods
	// ============================================================


	/**
	 * Log LOADED level RCS Id of class.
	 * 
	 * @param	rcsid		the RCS Id
	 * @param	clazz		the class 
	 */
	public void loaded(String rcsid, Class<?> clazz);


	/**
	 * Log TRACE level message.
	 * 
	 * @param	message		the message
	 */
	public void trace(Object message);


	/**
	 * Log TRACE level message.
	 * 
	 * @param	message		the message
	 */
	public void trace(String message);

	
	/**
	 * Log TRACE level message.
	 * 
	 * @param	message		the message
	 */
	public void trace(Loggable message);

	/**
	 * Log TRACE level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void trace(Object message, Throwable detail);


	/**
	 * Log TRACE level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void trace(String message, Throwable detail);

	/**
	 * Log TRACE level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void trace(String format, Object... args);

	/**
	 * Log TRACE level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void trace(Throwable detail, String format, Object... args);

	/**
	 * Log DEBUG level message.
	 * 
	 * @param	message		the message
	 */
	public void debug(Object message);

	/**
	 * Log DEBUG level message.
	 * 
	 * @param	message		the message
	 */
	public void debug(String message);

	/**
	 * Log DEBUG level message.
	 * 
	 * @param	message		the message
	 */
	public void debug(Loggable message);

	/**
	 * Log DEBUG level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void debug(Object message, Throwable detail);


	/**
	 * Log DEBUG level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void debug(String message, Throwable detail);


	/**
	 * Log DEBUG level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void debug(String format, Object... args);


	/**
	 * Log DEBUG level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void debug(Throwable detail, String format, Object... args);


	/**
	 * Log INFO level message.
	 * 
	 * @param	message		the message
	 */
	public void info(Object message);


	/**
	 * Log INFO level message.
	 * 
	 * @param	message		the message
	 */
	public void info(String message);


	/**
	 * Log INFO level message.
	 * 
	 * @param	message		the message
	 */
	public void info(Loggable message);


	/**
	 * Log INFO level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void info(Object message, Throwable detail);


	/**
	 * Log INFO level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void info(String message, Throwable detail);


	/**
	 * Log INFO level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void info(String format, Object... args);


	/**
	 * Log INFO level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void info(Throwable detail, String format, Object... args);


	/**
	 * Log WARN level message.
	 * 
	 * @param	message		the message
	 */
	public void warn(Object message);


	/**
	 * Log WARN level message.
	 * 
	 * @param	message		the message
	 */
	public void warn(String message);


	/**
	 * Log WARN level message.
	 * 
	 * @param	message		the message
	 */
	public void warn(Loggable message);


	/**
	 * Log WARN level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void warn(Object message, Throwable detail);


	/**
	 * Log WARN level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void warn(String message, Throwable detail);


	/**
	 * Log WARN level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void warn(String format, Object... args);


	/**
	 * Log WARN level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void warn(Throwable detail, String format, Object... args);


	/**
	 * Log ERROR level message.
	 * 
	 * @param	message		the message
	 */
	public void error(Object message);

	/**
	 * Log ERROR level message.
	 * 
	 * @param	message		the message
	 */
	public void error(String message);


	/**
	 * Log ERROR level message.
	 * 
	 * @param	message		the message
	 */
	public void error(Loggable message);


	/**
	 * Log ERROR level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void error(Object message, Throwable detail);


	/**
	 * Log ERROR level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void error(String message, Throwable detail);


	/**
	 * Log ERROR level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void error(String format, Object... args);


	/**
	 * Log ERROR level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void error(Throwable detail, String format, Object... args);


	/**
	 * Log FATAL level message.
	 * 
	 * @param	message		the message
	 */
	public void fatal(Object message);


	/**
	 * Log FATAL level message.
	 * 
	 * @param	message		the message
	 */
	public void fatal(String message);


	/**
	 * Log FATAL level message.
	 * 
	 * @param	message		the message
	 */
	public void fatal(Loggable message);


	/**
	 * Log FATAL level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void fatal(Object message, Throwable detail);


	/**
	 * Log FATAL level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void fatal(String message, Throwable detail);


	/**
	 * Log FATAL level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void fatal(Loggable message, Throwable detail);


	/**
	 * Log FATAL level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void fatal(String format, Object... args);


	/**
	 * Log FATAL level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void fatal(Throwable detail, String format, Object... args);


	/**
	 * Log a message at given log level.
	 * 
	 * @param	logLevel	log level
	 * @param	message		the message
	 */
	public void log(LogLevel logLevel, String message);


	/**
	 * Log a message at given log level.
	 * 
	 * @param	logLevel	log level
	 * @param	message		the message
	 */
	public void log(LogLevel logLevel, Object message);


	/**
	 * Log a message and exception detail at given log level.
	 * 
	 * @param	logLevel	log level
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void log(LogLevel logLevel, Object message, Throwable detail);

	
	/**
	 * Log a message and exception detail at given log level.
	 * 
	 * @param	logLevel	log level
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	public void log(LogLevel logLevel, String message, Throwable detail);


	/**
	 * Log a formatted message at given log level.
	 * 
	 * @param	logLevel	log level
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void log(LogLevel logLevel, String format, Object... args);


	/**
	 * Log a formatted message and exception detail at given log level.
	 * 
	 * @param	logLevel	log level
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	public void log(LogLevel logLevel, Throwable detail, String format, Object... args);


	/**
	 * Log a message at given log level.
	 * 
	 * @param	logLevel	log level
	 * @param	message		the loggable object
	 */
	public void log(LogLevel logLevel, Loggable message);


	/**
	 * Is FATAL log level enabled?.
	 * 
	 * @return	true if enabled, false otherwise
	 */
	public boolean isFatalEnabled();
	
	/**
	 * Is ERROR log level enabled?.
	 * 
	 * @return	true if enabled, false otherwise
	 */
	public boolean isErrorEnabled();

	/**
	 * Is WARN log level enabled?.
	 * 
	 * @return	true if enabled, false otherwise
	 */
	public boolean isWarnEnabled();

	/**
	 * Is INFO log level enabled?.
	 * 
	 * @return	true if enabled, false otherwise
	 */
	public boolean isInfoEnabled();
	
	/**
	 * Is DEBUG log level enabled?.
	 * 
	 * @return	true if enabled, false otherwise
	 */
	public boolean isDebugEnabled();
	
	/**
	 * Is TRACE log level enabled?.
	 * 
	 * @return	true if enabled, false otherwise
	 */
	public boolean isTraceEnabled();

	/**
	 * Is LOADED log level enabled?.
	 * 
	 * @return	true if enabled, false otherwise
	 */
	public boolean isLoadedEnabled();
	
	/**
	 * Is given log level enabled?.
	 * 
	 * @param	logLevel	log level
	 * @return	true if enabled, false otherwise
	 */
	public boolean isLogLevelEnabled(LogLevel logLevel);
	
}
