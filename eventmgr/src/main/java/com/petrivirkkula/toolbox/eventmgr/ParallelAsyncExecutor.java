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


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Sequential Asynchronous Executor.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class ParallelAsyncExecutor implements Executor
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
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(ParallelAsyncExecutor.class);

	static {
		LOGGER.loaded(RCSID, ParallelAsyncExecutor.class);
	}


	/**
	 * Thread pool executor.
	 */
	private final Executor executor;
	
	
	/**
	 * Work queue
	 */
	private final BlockingQueue<Runnable> workQueue;
	
	
	/**
	 * Constructor with min and max thread parameters.
	 */
	public ParallelAsyncExecutor(int minThreads, int maxThreads) {
		this.workQueue = new LinkedBlockingQueue<Runnable>();
		this.executor = createExecutor(minThreads, maxThreads);
	}

	/**
	 * Constructor.
	 */
	public ParallelAsyncExecutor() {
		this(0, 100);
	}

	
	/**
	 * Creates a new thread pool executor.
	 * 
	 * @return	executor
	 */
	protected Executor createExecutor(int minThreads, int maxThreads) {
		return new ThreadPoolExecutor(minThreads, maxThreads, 15, TimeUnit.SECONDS, workQueue, new ThreadFactory() {
			   public Thread newThread(Runnable r) {
			     return new Thread(r) {
			    	 @Override
			    	 public void run() {
			    		 try {
			    			 LOGGER.trace("started ParallelAsyncExecutor thread: " + this);
			    			 super.run();
			    		 }
			    		 finally {
			    			 LOGGER.trace("completed ParallelAsyncExecutor thread: " + this);
			    		 }
		    		 }

			     };
			   }
			 });

	}

		
	@Override
	public void execute(Runnable command) {
		executor.execute(command);
	}



}
