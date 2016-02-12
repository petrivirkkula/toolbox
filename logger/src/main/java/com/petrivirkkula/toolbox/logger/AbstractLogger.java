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
 * Abstract Logging class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public abstract class AbstractLogger implements ILogger
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";




	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public abstract boolean equals(Object obj);


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public abstract int hashCode();



	// ============================================================
	// Logging Methods
	// ============================================================


	/**
	 * Log LOADED level RCS Id of class.
	 * 
	 * @param	rcsid		the RCS Id
	 * @param	clazz		the class 
	 */
	@Override
	public void loaded(String rcsid, Class<?> clazz) {
		log(LogLevel.LOADED, "loaded with RCS Id " + rcsid);
	}


	/**
	 * Log TRACE level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void trace(Object message) {
		log(LogLevel.TRACE, createLoggable(message));
	}

	/**
	 * Log TRACE level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void trace(String message) {
		Loggable loggable = createLoggable(message);
		log(LogLevel.TRACE, loggable);
	}


	/**
	 * Log TRACE level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void trace(Loggable message) {
		log(LogLevel.TRACE, message);
	}


	/**
	 * Log TRACE level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void trace(Object message, Throwable detail) {
		log(LogLevel.TRACE, createLoggable(detail, message));
	}


	/**
	 * Log TRACE level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void trace(String message, Throwable detail) {
		log(LogLevel.TRACE, message, detail);
	}


	/**
	 * Log TRACE level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void trace(String format, Object... args) {
		log(LogLevel.TRACE, createLoggable(format, args));
	}


	/**
	 * Log TRACE level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void trace(Throwable detail, String format, Object... args) {
		log(LogLevel.TRACE, createLoggable(detail, format, args));
	}


	/**
	 * Log DEBUG level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void debug(Object message) {
		log(LogLevel.DEBUG, createLoggable(message));
	}

	/**
	 * Log DEBUG level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void debug(String message) {
		log(LogLevel.DEBUG, createLoggable(message));
	}

	/**
	 * Log DEBUG level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void debug(Loggable message) {
		log(LogLevel.DEBUG, message);
	}


	/**
	 * Log DEBUG level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void debug(Object message, Throwable detail) {
		log(LogLevel.DEBUG, createLoggable(message), detail);
	}


	/**
	 * Log DEBUG level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void debug(String message, Throwable detail) {
		log(LogLevel.DEBUG, message, detail);
	}


	/**
	 * Log DEBUG level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void debug(String format, Object... args) {
		log(LogLevel.DEBUG, createLoggable(format, args));
	}


	/**
	 * Log DEBUG level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void debug(Throwable detail, String format, Object... args) {
		log(LogLevel.DEBUG, createLoggable(detail, format, args));
	}


	/**
	 * Log INFO level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void info(Object message) {
		log(LogLevel.INFO, createLoggable(message));
	}


	/**
	 * Log INFO level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void info(String message) {
		log(LogLevel.INFO, createLoggable(message));
	}


	/**
	 * Log INFO level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void info(Loggable message) {
		log(LogLevel.INFO, message);
	}


	/**
	 * Log INFO level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void info(Object message, Throwable detail) {
		log(LogLevel.INFO, createLoggable(detail, message));
	}


	/**
	 * Log INFO level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void info(String message, Throwable detail) {
		log(LogLevel.INFO, message, detail);
	}


	/**
	 * Log INFO level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void info(String format, Object... args) {
		log(LogLevel.INFO, createLoggable(format, args));
	}


	/**
	 * Log INFO level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void info(Throwable detail, String format, Object... args) {
		log(LogLevel.INFO, createLoggable(detail, format, args));
	}


	/**
	 * Log WARN level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void warn(Object message) {
		log(LogLevel.WARN, createLoggable(message));
	}

	
	/**
	 * Log WARN level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void warn(String message) {
		log(LogLevel.WARN, createLoggable(message));
	}


	/**
	 * Log WARN level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void warn(Loggable message) {
		log(LogLevel.WARN, message);
	}


	/**
	 * Log WARN level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void warn(Object message, Throwable detail) {
		log(LogLevel.WARN, createLoggable(detail, message));
	}


	/**
	 * Log WARN level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void warn(String message, Throwable detail) {
		log(LogLevel.WARN, message, detail);
	}


	/**
	 * Log WARN level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void warn(String format, Object... args) {
		log(LogLevel.WARN, createLoggable(format, args));
	}


	/**
	 * Log WARN level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void warn(Throwable detail, String format, Object... args) {
		log(LogLevel.WARN, createLoggable(detail, format, args));
	}


	/**
	 * Log ERROR level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void error(Object message) {
		log(LogLevel.ERROR, createLoggable(message));
	}


	/**
	 * Log ERROR level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void error(String message) {
		log(LogLevel.ERROR, createLoggable(message));
	}


	/**
	 * Log ERROR level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void error(Loggable message) {
		log(LogLevel.ERROR, message);
	}


	/**
	 * Log ERROR level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void error(Object message, Throwable detail) {
		log(LogLevel.ERROR, createLoggable(detail, message));
	}


	/**
	 * Log ERROR level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void error(String message, Throwable detail) {
		log(LogLevel.ERROR, message, detail);
	}


	/**
	 * Log ERROR level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void error(String format, Object... args) {
		log(LogLevel.ERROR, createLoggable(format, args));
	}


	/**
	 * Log ERROR level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void error(Throwable detail, String format, Object... args) {
		log(LogLevel.ERROR, createLoggable(detail, format, args));
	}


	/**
	 * Log FATAL level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void fatal(Object message) {
		log(LogLevel.FATAL, createLoggable(message));
	}


	/**
	 * Log FATAL level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void fatal(String message) {
		log(LogLevel.FATAL, createLoggable(message));
	}


	/**
	 * Log FATAL level message.
	 * 
	 * @param	message		the message
	 */
	@Override
	public void fatal(Loggable message) {
		log(LogLevel.FATAL, message);
	}


	/**
	 * Log FATAL level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void fatal(Object message, Throwable detail) {
		log(LogLevel.FATAL, createLoggable(detail, message));
	}


	/**
	 * Log FATAL level message with exception detail.
	 * 
	 * @param	message		the message
	 * @param	detail		the exception detail
	 */
	@Override
	public void fatal(String message, Throwable detail) {
		log(LogLevel.FATAL, createLoggable(detail, message));
	}


//	/**
//	 * Log FATAL level message with exception detail.
//	 * 
//	 * @param	message		the message
//	 * @param	detail		the exception detail
//	 */
//	@Override
//	public void fatal(Loggable message, Throwable detail) {
//		log(LogLevel.FATAL, message, detail);
//	}


	/**
	 * Log FATAL level formatted message.
	 * 
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void fatal(String format, Object... args) {
		log(LogLevel.FATAL, createLoggable(format, args));
	}


	/**
	 * Log FATAL level formatted message with exception detail.
	 * 
	 * @param	detail		the exception detail
	 * @param	format		the format string
	 * @param	args		the format arguments
	 */
	@Override
	public void fatal(Throwable detail, String format, Object... args) {
		log(LogLevel.FATAL, createLoggable(detail, format, args));
	}


	@Override
	public void log(LogLevel logLevel, Object message) {
		logInternal(logLevel, createLoggable(message));
	}


	@Override
	public void log(LogLevel logLevel, String message) {
		logInternal(logLevel, createLoggable(message));
	}

	@Override
	public void log(LogLevel logLevel, Loggable message) {
		logInternal(logLevel, message);
	}


	@Override
	public void log(LogLevel logLevel, Object message, Throwable detail) {
		logInternal(logLevel, createLoggable(detail, message));
	}

	@Override
	public void log(LogLevel logLevel, String message, Throwable detail) {
		logInternal(logLevel, createLoggable(detail, message));
	}

	@Override
	public void log(LogLevel logLevel, String format, Object... args) {
		log(logLevel, createLoggable(format, args));
	}


	@Override
	public void log(LogLevel logLevel, Throwable detail, String format, Object... args) {
		log(logLevel, createLoggable(detail, format, args));
	}




	public abstract void logInternal(LogLevel logLevel, Loggable message);
	

	protected Loggable createLoggable(Throwable detail, Object obj) {
		return new SimpleLoggable(detail, obj);
	}

	protected Loggable createLoggable(Object obj) {
		if (obj instanceof Loggable)
			return (Loggable) obj;
		return new SimpleLoggable(obj);
	}


	protected Loggable createLoggable(Throwable detail, String format, Object[] args) {
		return new FormatterLoggable(detail, format, args);
	}

	protected Loggable createLoggable(String format, Object[] args) {
		return new FormatterLoggable(format, args);
	}


}
