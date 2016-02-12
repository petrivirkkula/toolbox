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
 * Interface representing a loggable message.
 *
 * @author		Petri Virkkula
 * @version		$Id$
 */
public interface ExtendedLoggable extends Loggable
{
    /**
     * File RCS Id.
     *
     * $Id$
     */
    public static final String RCSID = "$Id$";

    public String toLogMessage();
    
	/**
	 * Gets the exception associated with this loggable.
	 * 
	 * @return		the exception or <code>null</code> 
	 */
	public Throwable getException();
	
	/**
	 * Appends this loggable to a string buffer.
	 * 
	 * @param	buffer		buffer (e.g. string buffer)
	 * @return	the argument string buffer
	 */
	public Appendable append(Appendable buffer);
}
