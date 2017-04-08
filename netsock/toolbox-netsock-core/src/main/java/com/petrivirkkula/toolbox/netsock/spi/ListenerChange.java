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



/**
 * Listener change type enum.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
enum ListenerChangeType {
	ADD, REMOVE;
}

/**
 * Listener change class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
class ListenerChange implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * File RCS Id.
	 *
	 * $IdId$
	 */
	public static final String RCSID = "$Id$";

	/**
	 * Logger
	 */
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSServerServiceSpi.class);

	static {
		LOGGER.loaded(RCSID, NSServerServiceSpi.class);
	}

	/**
	 * Type of this change (ADD or REMOVE).
	 */
	private final ListenerChangeType changeType;
	
	/**
	 * Socket address to be ADDed or REMOVEd.
	 */
	private final NSServerListenerSpi listener;
	
	
	/**
	 * Constructor with change type and socket address parameters.
	 * 
	 * @param changeType	type of change (ADD or REMOVE)
	 * @param listener	socket address to be ADDed or REMOVEd.
	 */
	ListenerChange(final ListenerChangeType changeType, final NSServerListenerSpi listener) {
		this.changeType = changeType;
		this.listener = listener;
	}

	/**
	 * Gets the change type.
	 * 
	 * @return the change type
	 */
	public ListenerChangeType getChangeType() {
		return changeType;
	}

	/**
	 * Gets socket address of this change.
	 * 
	 * @return the socket address
	 */
	public NSServerListenerSpi getListener() {
		return listener;
	}
}
