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

import com.petrivirkkula.toolbox.logger.SimpleLoggable;




/**
 * Event Manager Implementation class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class DefaultEventExecutorFactory implements EventExecutorFactory
{
	/**
	 * File RCS Id.
	 *
	 * $IdId$
	 */
	public static final String RCSID = "$Id$";

	/**
	 * Logger
	 */
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(DefaultEventExecutorFactory.class);

	static {
		logger.loaded(RCSID, DefaultEventExecutorFactory.class);
	}

	private final Executor lookupExecutor;
	private final Executor processExecutor;

	/**
	 * Constructor with no parameters.
	 *
	 * This is same as <code>new DefaultEventExecutorFactory(new ParallelAsyncExecutor())</code>.
	 */
	public DefaultEventExecutorFactory() {
		this(new ParallelAsyncExecutor());
		getLogger().trace(new SimpleLoggable("created executor factory")); 
	}

	/**
	 * Constructor with executor parameter.
	 *
	 * The passed executor will be used for both lookups and processing tasks.
	 *
	 * @param executor		executor
	 */
	public DefaultEventExecutorFactory(Executor executor) {
		this(executor, executor);
	}

	/**
	 * Constructor with lookup and process executor parameters.
	 * @param lookupExecutor	lookup executor
	 * @param processExecutor	processing executor
	 */
	public DefaultEventExecutorFactory(Executor lookupExecutor, Executor processExecutor) {
		this.lookupExecutor = lookupExecutor;
		this.processExecutor = processExecutor;
		getLogger().trace(new SimpleLoggable("created executor factory")); 
	}
	
	/**
	 * Gets a new instance factory executor.
	 * 
	 * @return	new factory instance
	 */
	public static DefaultEventExecutorFactory getInstance() {
		return new DefaultEventExecutorFactory();
	}

	
	/**
	 * Gets logger.
	 * 
	 * @return	logger
	 */
	protected com.petrivirkkula.toolbox.logger.Logger getLogger() {
		return logger;
	}


	@Override
	public Executor getLookupExecutor(Event event) {
		return lookupExecutor;
	}


	@Override
	public Executor getProcessExecutor(Event event) {
		return processExecutor;
	}





}
