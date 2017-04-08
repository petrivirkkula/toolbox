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

import java.io.Closeable;

import com.petrivirkkula.toolbox.eventmgr.EventSource;

/**
 * Service.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public interface NSService extends EventSource, Closeable
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";
	

	/**
	 * Possible service states.
	 * 
	 * @author	Petri Virkkula
	 */
	public static enum ServiceState {
		/**
		 * Service is stopped.
		 */
		STOPPED,
		
		/**
		 * Service is running.
		 */
		RUNNING;
	};
	
	/**
	 * Starts this manager object.
	 * 
	 * Call this method is NO-OP if the manager is already running.
	 */
	public boolean startService() throws InterruptedException;
	
	
	/**
	 * Stops the service.
	 * 
	 * @return	true if state was changed, false if the service was already stopped or was already stopping
	 * @throws	InterruptedException	if the calling thread was interrupted before service was stopped
	 */
	public boolean stopService() throws InterruptedException;
	
	
	/**
	 * Gets current service state.
	 * 
	 * @return	current service state, STOPPED or RUNNING.
	 */
	public ServiceState getCurrentServiceState();

	
	/**
	 * Gets pending service state.
	 * 
	 * @return	pending service state, STOPPED or RUNNING
	 */
	public ServiceState getPendingServiceState();

	
	/**
	 * Wait the service to reach given state.
	 * 
	 * @param	serviceState			wait the service to be in given state
	 * @throws	InterruptedException	if waiting thread was interrupted before state change happened
	 */
	public void waitForServiceState(ServiceState serviceState) throws InterruptedException;
	
	
	/**
	 * Is running (i.e. service state is RUNNING)?
	 * 
	 * @return	true if service is running, otherwise false
	 */
	public boolean isRunning();
	
	
	/**
	 * Is stopped (i.e. service state is stopped)?
	 * 
	 * @return	true if service is stopped, otherwise false
	 */
	public boolean isStopped();
}
