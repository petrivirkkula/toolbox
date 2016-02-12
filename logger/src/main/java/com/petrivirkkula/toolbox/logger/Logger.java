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



import com.petrivirkkula.toolbox.logger.impl.LogContextImpl;
import com.petrivirkkula.toolbox.logger.impl.LoggerImpl;


/**
 * Logging class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public abstract class Logger extends AbstractLogger implements ILogger
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";




	/**
	 * Constructor without parameters.
	 */
	protected Logger() {
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public abstract boolean equals(Object obj);


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public abstract int hashCode();

	/**
	 * Gets new logger.
	 * 
	 * @param	logCategory	log category of new logger
	 * @return	new logger
	 */
	public static Logger getLogger(String logCategory) {
		return new LoggerImpl(logCategory);
	}


	/**
	 * Gets new logger.
	 * 
	 * @param	logCategory	log category of new logger
	 * @return	new logger
	 */
	public static Logger getLogger(Class<?> logCategory) {
		return new LoggerImpl(logCategory.getName());
	}


	// ============================================================
	// Log Context methods
	// ============================================================



	/**
	 * Gets current log context.
	 * 
	 * @return	current log context
	 */
	public static LogContext getCurrentLogContext() {
		return LogContextImpl.getCurrentLogContext();
	}


}
