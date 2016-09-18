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

import java.util.concurrent.Executor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.eventmgr.DefaultEventExecutorFactory;
import com.petrivirkkula.toolbox.eventmgr.DefaultEventManager;
import com.petrivirkkula.toolbox.eventmgr.EventHandler;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.eventmgr.GenericEvent;
import com.petrivirkkula.toolbox.eventmgr.ParallelAsyncExecutor;

/**
 * @author Petri Virkkula
 *
 */
public class HandlerExceptionTest
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
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(HandlerExceptionTest.class);

	static {
		LOGGER.loaded(RCSID, HandlerExceptionTest.class);
	}
	
	
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
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventManager#getInstance()}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testExeption() throws InterruptedException {
		Executor executor = new ParallelAsyncExecutor();
		EventManager eventManager = EventManager.getInstance(new DefaultEventExecutorFactory(executor));
		assertEquals(DefaultEventManager.class, eventManager.getClass());
		final int counter[] = new int[1];
		counter[0] = 0;
		EventHandler<GenericEvent> eventHandler = new EventHandler<GenericEvent>() {
			@Override
			public void on(GenericEvent event) {
				LOGGER.info("on GenericEvent: " + event);
				synchronized(counter) {
					counter[0]++;
					counter.notify();
					throw new RuntimeException("exception");
				}
			}
			
		};
		eventManager.register(this, "abc", eventHandler);
		GenericEvent event = new GenericEvent("abc", this);
		synchronized(counter) {
			eventManager.trigger(event);
			counter.wait(1000);
			assertEquals(1, counter[0]);
		}
		synchronized(counter) {
			eventManager.trigger(event);
			counter.wait(1000);
			assertEquals(2, counter[0]);
		}
	}

}
