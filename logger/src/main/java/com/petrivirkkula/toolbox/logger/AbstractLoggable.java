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
 * Abstract base class for loggables.
 *
 * @author		Petri Virkkula
 * @version		$Id$
 */
public abstract class AbstractLoggable implements Loggable
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
	 * The exception associated with this loggable.
	 */
	private final Throwable exception;


	/**
	 * Construction with exception parameter.
	 * 
	 * @param	exception	the exception (or null if none) associated with this loggable
	 */
	protected AbstractLoggable(Throwable exception) {
		this.exception = exception;
	}

	/**
	 * Convert this object to string.
	 * 
	 * @return		string representation of this object
	 */
	@Override
	public String toString() {
		return toLogMessage();
	}

	/**
	 * Gets the exception associated with this loggable.
	 * 
	 * @return		the exception or <code>null</code> 
	 */
	public Throwable getException() {
		return exception;
	}

}
