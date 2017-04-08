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

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Server service.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public interface NSServerService extends NSService
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";
	
	/**
	 * Adds a listener with port parameter.
	 * 
	 * Same as calling the {@link NSServerService#addListener(String, int)} and passing {@code "0.0.0.0"} as {@code host} parameter.
	 * 
	 * @param port	port number for the listener
	 * @return	socket address for the listener
	 * @see NSServerService#addListener(String, int)
	 */
	public NSServerListener addListener(int port);

	public NSServerListener addListener(String host, int port);

	public NSServerListener addListener(InetAddress inetAddr, int port) throws NSRuntimeException;

	public NSServerListener addListener(InetSocketAddress socketAddr) throws NSRuntimeException;

	public NSServerListener removeListener(int port) throws NSRuntimeException;

	public NSServerListener removeListener(String host, int port) throws NSRuntimeException;

	public NSServerListener removeListener(InetAddress inetAddr, int port) throws NSRuntimeException;

	public NSServerListener removeListener(InetSocketAddress socketAddr) throws NSRuntimeException;
	
	public NSServerListener removeListener(NSServerListener listener) throws NSRuntimeException;

}
