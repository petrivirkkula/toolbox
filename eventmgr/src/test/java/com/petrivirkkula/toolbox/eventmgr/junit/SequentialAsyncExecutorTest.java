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

package com.petrivirkkula.toolbox.eventmgr.junit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.eventmgr.SequentialAsyncExecutor;

/**
 * @author Petri Virkkula
 *
 */
public class SequentialAsyncExecutorTest
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(SequentialAsyncExecutorTest.class);

	static {
		logger.loaded(RCSID, SequentialAsyncExecutorTest.class);
	}

	public static class MySequentialAsyncExecutor extends SequentialAsyncExecutor {
		@Override
		public Thread getAsyncThread() {
			return super.getAsyncThread();
		}
		@Override
		public List<Runnable> getTasks() {
			return super.getTasks();
		}

	};

	
	private int counter = 0;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.SequentialAsyncExecutor#SequentialAsyncExecutor()}.
	 */
	@Test
	public void testSequentialAsyncExecutor() {
		SequentialAsyncExecutor executor = new SequentialAsyncExecutor();
		executor.execute(new Runnable() {
			public void run() {
				logger.info("execute");
			}
		});
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.SequentialAsyncExecutor#createAsyncThread()}.
	 */
	@Test
	public void testCreateAsyncThread() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.SequentialAsyncExecutor#execute(java.lang.Runnable)}.
	 */
	@Test
	public void testExecute() {
		SequentialAsyncExecutor executor = new SequentialAsyncExecutor();
		final Object mutex = new Object();
		counter = 0;
		Thread bg = new Thread(new Runnable() {
			public void run() {
				synchronized(mutex) {
					counter++;
					mutex.notify();
				}
				synchronized(mutex) {
					while(counter < 2) {
						try {
							mutex.wait();
						} catch (InterruptedException ex) {
							logger.error(ex, "exception: " + ex);
							fail("interrupted");
						}
					}
				}
				logger.info("done");
			}
		});
		bg.setDaemon(true);
		bg.start();
		synchronized(mutex) {
			while(counter < 1) {
				try {
					mutex.wait();
				} catch (InterruptedException ex) {
					logger.error(ex, "exception: " + ex);
					fail("interrupted");
				}
			}
			counter++;
			mutex.notify();
		}
		executor.execute(new Runnable() {
			public void run() {
				synchronized(mutex) {
					counter++;
					mutex.notify();
				}
			}
		});
		synchronized(mutex) {
			while(counter < 2) {
				try {
					mutex.wait();
				} catch (InterruptedException ex) {
					logger.error(ex, "exception: " + ex);
					fail("interrupted");
				}
			}
		}
		executor.execute(new Runnable() {
			public void run() {
				synchronized(mutex) {
					counter++;
					mutex.notify();
				}
			}
		});
		synchronized(mutex) {
			while(counter < 3) {
				try {
					mutex.wait();
				} catch (InterruptedException ex) {
					logger.error(ex, "exception: " + ex);
					fail("interrupted");
				}
			}
		}
		logger.info("completed");
	}

	
	@Test
	public void testMySequentialAsyncExecutor() throws InterruptedException {
		MySequentialAsyncExecutor executor = new MySequentialAsyncExecutor();
		final Object mutex = new Object();
		counter = 0;
		executor.execute(new Runnable() {
			public void run() {
				throw new RuntimeException();
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				logger.info("execute");
				synchronized(mutex) {
					counter++;
					mutex.notify();
				}
			}
		});
		synchronized(mutex) {
			while(counter < 1) {
				try {
					mutex.wait();
				} catch (InterruptedException ex) {
					logger.error(ex, "exception: " + ex);
					fail("interrupted");
				}
			}
		}
		assertEquals(0, executor.getTasks().size());
		executor.getAsyncThread().interrupt();
		executor.execute(new Runnable() {
			public void run() {
				try {
					logger.info("execute");
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					logger.error(ex, "exception: " + ex);
					fail("interrupted");
				}
			}
		});
		assertEquals(1, executor.getTasks().size());
		Thread.sleep(1000);
	}

	
	@Test
	public void testMySequentialAsyncExecutor2() throws InterruptedException {
		MySequentialAsyncExecutor executor = new MySequentialAsyncExecutor();
		executor.execute(new Runnable() {
			public void run() {
				throw new RuntimeException();
			}
		});
		executor.execute(null);

		executor.execute(new Runnable() {
			public void run() {
				logger.info("execute");
			}
		});
		Thread.sleep(1000);
		assertEquals(1, executor.getTasks().size());
	}
}
