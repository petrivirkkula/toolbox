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

package com.petrivirkkula.toolbox.netsock.spi;


import java.io.Serializable;
import java.net.InetSocketAddress;

import com.petrivirkkula.toolbox.eventmgr.AbstractEventSource;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.netsock.NSClientDestination;


/**
 * Service SPI class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class NSClientDestinationSpi extends AbstractEventSource implements NSClientDestination, Serializable, Comparable<NSClientDestinationSpi>
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id$";

	/**
	 * Logger
	 */
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSClientDestinationSpi.class);

	static {
		LOGGER.loaded(RCSID, NSClientDestinationSpi.class);
	}

	private static final long serialVersionUID = 1L;

	
	private final NSClientServiceSpi clientService;
	
	private final InetSocketAddress sourceAddress;

	private final InetSocketAddress destinationAddress;

	
	public NSClientDestinationSpi(NSClientServiceSpi clientService, InetSocketAddress destinationAddress) {
		this(clientService, destinationAddress, null);
	}
	
	public NSClientDestinationSpi(NSClientServiceSpi clientService, InetSocketAddress destinationAddress, InetSocketAddress sourceAddress) {
		this.clientService = clientService;
		this.destinationAddress = destinationAddress;
		this.sourceAddress = sourceAddress;
	}

	
	/**
	 * @return the serverService
	 */
	protected NSClientServiceSpi getClientService() {
		return clientService;
	}


	
	public InetSocketAddress getDestinationAddress() {
		return destinationAddress;
	}
	
	@Override
	public InetSocketAddress getSourceAddress() {
		return sourceAddress;
	
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
			if (!(obj instanceof NSClientDestinationSpi))
				throw new ClassCastException("obj is not instance of Client Destination");
			NSClientDestinationSpi other = (NSClientDestinationSpi) obj;
			if (this.clientService != other.clientService)
				return false;
			boolean c = this.destinationAddress.equals(other.destinationAddress);
			if (!c)
				return c;
			
			if (this.sourceAddress != null) {
				if (other.sourceAddress == null) {
					c = false;
				} else {
					c = this.sourceAddress.equals(other.sourceAddress);
				}
			} else if (other.sourceAddress != null) {
				c = false;
			}
			return c;
		}
	
	
	@Override
	public int compareTo(NSClientDestinationSpi obj) {
		if (obj == null)
			return 1;
		if (!(obj instanceof NSClientDestinationSpi))
			throw new ClassCastException("obj is not instance of Client Destination");
		NSClientDestinationSpi other = (NSClientDestinationSpi) obj;
		if (this.clientService != other.clientService)
			return 1;
		int c = this.destinationAddress.toString().compareTo(other.destinationAddress.toString());
		if (c != 0)
			return c;
		
		if (this.sourceAddress != null) {
			if (other.sourceAddress == null) {
				c = 1;
			} else {
				c = this.sourceAddress.toString().compareTo(other.sourceAddress.toString());
			}
		} else if (other.sourceAddress != null) {
			c = -1;
		}
		return c;
	}
	
	
	@Override
	public int hashCode() {
		int h = this.destinationAddress.hashCode();
		if (this.sourceAddress != null)
			h ^= this.sourceAddress.hashCode();
		return h;
	}




	@Override
	protected EventManager getEventManager() {
		return clientService.getEventManager();
	}


}