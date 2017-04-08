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
 * Client service.
 * 
 * The client service manages multiple client destination addresses. These addresses are tried
 * one by one with round-robin algorithm. 
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public interface NSClientService extends NSService
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";
	
	
	/**
	 * Adds a destination with host and port parameters.
	 * 
	 * @param	host	destination host
	 * @param	port	destination port
	 * @return	socket address of the destination
	 * @throws	NSRuntimeException	an error occurred
	 */
	public NSClientDestination addDestination(String host, int port) throws NSRuntimeException;


	/**
	 * Adds a destination with inet address and port parameters.
	 * 
	 * @param	inetAddr			inet address of destination
	 * @param	port				destination port
	 * @return	socket address of the destination
	 * @throws	NSRuntimeException	an error occurred
	 */
	public NSClientDestination addDestination(InetAddress inetAddr, int port) throws NSRuntimeException;

	
	/**
	 * Adds a destination address.
	 * 
	 * @param	destination			destination as inet socket address
	 * @return	client destination
	 * @throws	NSRuntimeException	if there was an error
	 */
	public NSClientDestination addDestination(InetSocketAddress destination) throws NSRuntimeException;

	
	/**
	 * Adds a destination address with bound local address.
	 * 
	 * @param	destination			destination address
	 * @param	source				local address to be used
	 * @return	client destination
	 * @throws	NSRuntimeException	if there is an error
	 */
	public NSClientDestination addDestination(InetSocketAddress destination, InetSocketAddress source) throws NSRuntimeException;

	
	/**
	 * Adds a destination.
	 * 
	 * @param	destination			destination address
	 * @return	the client destination argument
	 * @throws	NSRuntimeException	if there is an error
	 */
	public NSClientDestination addDestination(NSClientDestination destination) throws NSRuntimeException;

	
	/**
	 * Removes a destination address with bound local address.
	 * 
	 * @param	destination			destination address
	 * @param	source				local address to be used
	 * @return	true if the destination was removed
	 * @throws	NSRuntimeException	if there is an error
	 */
	public boolean removeDestination(InetSocketAddress destination, InetSocketAddress source) throws NSRuntimeException;
	 
	
	/**
	 * Removes a destination address.
	 * 
	 * @param	destination			destination address
	 * @return	true if the destination was removed
	 * @throws	NSRuntimeException	if there is an error
	 */
	public boolean removeDestination(NSClientDestination destination) throws NSRuntimeException;

}
