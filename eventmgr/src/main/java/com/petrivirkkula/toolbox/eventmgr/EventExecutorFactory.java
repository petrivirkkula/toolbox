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

package com.petrivirkkula.toolbox.eventmgr;

import java.util.concurrent.Executor;


/**
 * Event Executor Factory.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public interface EventExecutorFactory
{
	/**
	 * File RCS Id.
	 * 
	 * $Id$
	 */
	public static final String RCSID = "$Id$";
	
	
	/**
	 * Gets an executor that will be used for looking up event handlers.
	 * 
	 * @param event	event that is being processed
	 * @return	an executor that will be used in event handler lookup only
	 */
	public Executor getLookupExecutor(Event event);

	
	/**
	 * Gets an executor that will execute event handlers.
	 * 
	 * @param event	event that is being processed
	 * @return	an executor that will execute event handlers
	 */
	public Executor getProcessExecutor(Event event);
}
