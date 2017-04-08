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

package com.petrivirkkula.toolbox.netsock;

/**
 * Runtime exception base class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class NSRuntimeException extends RuntimeException
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";

	
	/**
	 * Serialization version UID
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor without parameter.
	 */
	public NSRuntimeException() {
	}

	
	/**
	 * Constructor with message parameter.
	 * 
	 * @param message	exception message
	 */
	public NSRuntimeException(String message) {
		super(message);
	}

	
	/**
	 * Constructor with cause parameter
	 * 
	 * @param cause		nested exception
	 */
	public NSRuntimeException(Throwable cause) {
		super(cause);
	}

	
	/**
	 * Constructor with message and cause parameters.
	 * 
	 * @param message	exception message
	 * @param cause		nested exception
	 */
	public NSRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
