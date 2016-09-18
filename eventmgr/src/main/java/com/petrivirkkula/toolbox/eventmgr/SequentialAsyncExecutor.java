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


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;



/**
 * A Sequential Asynchronous Executor.
 * 
 * Events are processed by this executor parallel in a separate thread one by by
 * in serial fashion.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class SequentialAsyncExecutor implements Executor
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(SequentialAsyncExecutor.class);

	static {
		logger.loaded(RCSID, SequentialAsyncExecutor.class);
	}

	/**
	 * The pending list of tasks.
	 */
	private final List<Runnable> tasks;

	/**
	 * The thread that processes the tasks.
	 */
	private final Thread asyncThread;
	
	
	/**
	 * Constructor.
	 */
	public SequentialAsyncExecutor() {
		this.tasks = new ArrayList<Runnable>();
		this.asyncThread = createAsyncThread();
	}
	
	
	/**
	 * Creates single thread for asynchronous execution.
	 *  
	 * @return	new thread
	 */
	protected Thread createAsyncThread() {
		Runnable runnable = new Runnable() {
			public void run() {
				while(true) {
					Runnable task = null;
					synchronized(tasks) {
						while(tasks.isEmpty()) {
							try {
								tasks.wait();
							}
							catch(InterruptedException ex) {
								logger.info("event processing interrupted: " + ex);
								logger.debug(ex, "event processing interrupted: " + ex);
								return;
							}
						}
						task = tasks.remove(0);
					}
					if (task == null) {
						logger.info("exiting event processing");
						return;
					}
					try {
						task.run();
					}
					catch(Throwable ex) {
						logger.error(ex, "exception in event processing: " + ex);
					}
				}
			}
		};
		Thread thread = new Thread(runnable, "SequentialAsyncExecutor#" + System.currentTimeMillis());
		thread.setDaemon(true);
		thread.start();
		return thread;
	}
	
	
	@Override
	public void execute(Runnable command) {
		synchronized(tasks) {
			tasks.add(command);
			tasks.notify();
		}
	}


	/**
	 * Gets the thread used by this executor.
	 * 
	 * @return the asyncThread
	 */
	protected Thread getAsyncThread() {
		return asyncThread;
	}


	/**
	 * Gets the current task list.
	 * 
	 * @return the tasks
	 */
	protected List<Runnable> getTasks() {
		return tasks;
	}


}
