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

package com.petrivirkkula.toolbox.logger.impl;


import com.petrivirkkula.toolbox.logger.ExtendedLoggable;
import com.petrivirkkula.toolbox.logger.ILogger;
import com.petrivirkkula.toolbox.logger.LogLevel;
import com.petrivirkkula.toolbox.logger.Loggable;
import com.petrivirkkula.toolbox.logger.Logger;


/**
 * Logging implementation class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class LoggerImpl extends Logger implements ILogger
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";


	/**
	 * Log category of this logger. 
	 */
	private final String logCategory;

	private final org.slf4j.Logger slf4jLogger;

	/**
	 * Constructor with log category parameter.
	 * 
	 * @param	logCategory	log category for this logger
	 */
	public LoggerImpl(String logCategory) {
		this.logCategory = logCategory;
		this.slf4jLogger = org.slf4j.LoggerFactory.getLogger(logCategory);
	}

	/**
	 * Constructor with log category and slf4j logger parameters.
	 * 
	 * @param	logCategory	log category for this logger
	 * @param	slf4jLogger	SLF4J logger
	 */
	public LoggerImpl(String logCategory, org.slf4j.Logger slf4jLogger) {
		this.logCategory = logCategory;
		this.slf4jLogger = slf4jLogger;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof LoggerImpl))
			throw new ClassCastException("obj is not instance of LoggerImpl");
		LoggerImpl other = (LoggerImpl) obj;
		return getLogCategory().equals(other.getLogCategory());
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getLogCategory().hashCode();
	}


	/**
	 * @return the logCategory
	 */
	public String getLogCategory() {
		return logCategory;
	}

	@Override
	public boolean isFatalEnabled() {
		return slf4jLogger.isErrorEnabled();
	}
	
	public boolean isErrorEnabled() {
		return slf4jLogger.isErrorEnabled();
	}
	
	public boolean isWarnEnabled() {
		return slf4jLogger.isWarnEnabled();
	}
	
	public boolean isInfoEnabled() {
		return slf4jLogger.isInfoEnabled();
	}
	
	public boolean isDebugEnabled() {
		return slf4jLogger.isDebugEnabled();
	}
	
	public boolean isTraceEnabled() {
		return slf4jLogger.isTraceEnabled();
	}

	public boolean isLoadedEnabled() {
		return slf4jLogger.isTraceEnabled();
	}

	public boolean isLogLevelEnabled(LogLevel logLevel) {
		if (logLevel.getLevelValue() <= LogLevel._FATAL_VALUE)
			return isFatalEnabled();
		else if (logLevel.getLevelValue() <= LogLevel._ERROR_VALUE)
			return isErrorEnabled();
		else if (logLevel.getLevelValue() <= LogLevel._WARN_VALUE)
			return isWarnEnabled();
		else if (logLevel.getLevelValue() <= LogLevel._INFO_VALUE)
			return isInfoEnabled();
		else if (logLevel.getLevelValue() <= LogLevel._DEBUG_VALUE)
			return isDebugEnabled();
		else if (logLevel.getLevelValue() <= LogLevel._TRACE_VALUE)
			return isTraceEnabled();
		else
			return isLoadedEnabled();
	}


	public void logInternal(LogLevel logLevel, Loggable loggable) {
		if (!isLogLevelEnabled(logLevel))
			return;
		
		String text = loggable.toLogMessage();
		Throwable detail = null;
		if (loggable instanceof ExtendedLoggable)
			detail = ((ExtendedLoggable)loggable).getException();
		if (detail == null) {
			if (logLevel.getLevelValue() <= LogLevel._FATAL_VALUE)
				slf4jLogger.error(text, new Exception());
			else if (logLevel.getLevelValue() <= LogLevel._ERROR_VALUE)
				slf4jLogger.error(text);
			else if (logLevel.getLevelValue() <= LogLevel._WARN_VALUE)
				slf4jLogger.warn(text);
			else if (logLevel.getLevelValue() <= LogLevel._INFO_VALUE)
				slf4jLogger.info(text);
			else if (logLevel.getLevelValue() <= LogLevel._DEBUG_VALUE)
				slf4jLogger.debug(text);
			else
				slf4jLogger.trace(text);
		} else {
			if (logLevel.getLevelValue() <= LogLevel._FATAL_VALUE)
				slf4jLogger.error(text, detail);
			else if (logLevel.getLevelValue() <= LogLevel._ERROR_VALUE)
				slf4jLogger.error(text, detail);
			else if (logLevel.getLevelValue() <= LogLevel._WARN_VALUE)
				slf4jLogger.warn(text, detail);
			else if (logLevel.getLevelValue() <= LogLevel._INFO_VALUE)
				slf4jLogger.info(text, detail);
			else if (logLevel.getLevelValue() <= LogLevel._DEBUG_VALUE)
				slf4jLogger.debug(text, detail);
			else
				slf4jLogger.trace(text, detail);
		}
	}



}
