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

import java.util.Map;
import java.util.concurrent.Executor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.eventmgr.DefaultEventExecutorFactory;
import com.petrivirkkula.toolbox.eventmgr.DefaultEventManager;
import com.petrivirkkula.toolbox.eventmgr.Event;
import com.petrivirkkula.toolbox.eventmgr.EventExecutorFactory;
import com.petrivirkkula.toolbox.eventmgr.EventHandler;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.eventmgr.GenericEvent;
import com.petrivirkkula.toolbox.eventmgr.spi.EventManagerSpi;

/**
 * @author Petri Virkkula
 *
 */
public class DefaultEventManagerTest
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(DefaultEventManagerTest.class);


	static {
		logger.loaded(RCSID, DefaultEventManagerTest.class);
	}

	
	private static final String TEST_EVENT_NAME1 = "test.event1";

	private static final String TEST_EVENT_NAME2 = "test.event2";

//	private static final Date TEST_EVENT_TIME = new Date();

	private static final Object mutex = new Object();
	
	private static int counter = 0;
	
	private static final EventHandler<GenericEvent> TEST_EVENT_HANDLER1 = new EventHandler<GenericEvent>() {
		@Override
		public void on(GenericEvent event) {
			synchronized(mutex) {
				counter++;
				mutex.notify();
			}
		}
		
	};

	private static final EventHandler<GenericEvent> TEST_EVENT_HANDLER2 = new EventHandler<GenericEvent>() {
		@Override
		public void on(GenericEvent event) {
			synchronized(mutex) {
				counter++;
				mutex.notify();
			}
		}
		
	};

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
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.DefaultEventManager#trigger(com.petrivirkkula.toolbox.eventmgr.Event)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testTrigger() throws InterruptedException {
		Class<? extends EventManagerSpi> clazz = DefaultEventManager.class;
		EventManager eventManager = EventManager.getInstance(new DefaultEventExecutorFactory(), clazz);
		GenericEvent event = new GenericEvent(TEST_EVENT_NAME1, this);
		synchronized(mutex) {
			counter = 0;
			eventManager.trigger(event);
			mutex.wait(1000L);
			assertEquals(0, counter);
		}
		eventManager.register(this, TEST_EVENT_NAME1, TEST_EVENT_HANDLER1);
		synchronized(mutex) {
			counter = 0;
			eventManager.trigger(event);
			mutex.wait();
			assertEquals(1, counter);
		}
	}

	
	@Test
	public void testTrigger2() throws InterruptedException {
		EventManager eventManager = EventManager.getInstance(new EventExecutorFactory() {
			@Override
			public Executor getLookupExecutor(Event event) {
				return null;
			}
			@Override
			public Executor getProcessExecutor(Event event) {
				return null;
			}
		}, DefaultEventManager.class);
		GenericEvent event = new GenericEvent(TEST_EVENT_NAME1, this);
		counter = 0;
		eventManager.trigger(event);
		assertEquals(0, counter);
		eventManager.register(this, TEST_EVENT_NAME1, TEST_EVENT_HANDLER1);
		counter = 0;
		eventManager.trigger(event);
		assertEquals(1, counter);
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.DefaultEventManager#getEventHandlerCounts()}.
	 */
	@Test
	public void testGetEventHandlerCounts() {
		DefaultEventManager eventManager = (DefaultEventManager) EventManager.getInstance(new DefaultEventExecutorFactory(), DefaultEventManager.class);
		Map<Object,Map<String,Integer>> handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(0, handlerCounts.size());
		eventManager.register(this, TEST_EVENT_NAME1, TEST_EVENT_HANDLER1);
		assertEquals(0, handlerCounts.size());
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(1, handlerCounts.size());
		assertNotNull(handlerCounts.get(this));
		assertEquals(1, handlerCounts.get(this).size());
		eventManager.register(this, TEST_EVENT_NAME1, TEST_EVENT_HANDLER1);
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(1, handlerCounts.size());
		assertNotNull(handlerCounts.get(this));
		assertEquals(1, handlerCounts.get(this).size());
		eventManager.register(eventManager, TEST_EVENT_NAME1, TEST_EVENT_HANDLER1);
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(2, handlerCounts.size());
		assertNotNull(handlerCounts.get(this));
		assertEquals(1, handlerCounts.get(this).size());
		assertNotNull(handlerCounts.get(eventManager));
		assertEquals(1, handlerCounts.get(eventManager).size());
		eventManager.register(eventManager, TEST_EVENT_NAME1, TEST_EVENT_HANDLER2);
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(2, handlerCounts.size());
		assertNotNull(handlerCounts.get(this));
		assertEquals(1, handlerCounts.get(this).size());
		assertNotNull(handlerCounts.get(eventManager));
		assertEquals(1, handlerCounts.get(eventManager).size());
		eventManager.unregister(eventManager, TEST_EVENT_NAME1, TEST_EVENT_HANDLER1);
		eventManager.unregister(eventManager, TEST_EVENT_NAME1, TEST_EVENT_HANDLER1);
		assertEquals(2, handlerCounts.size());
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(2, handlerCounts.size());
		assertNotNull(handlerCounts.get(this));
		assertEquals(1, handlerCounts.get(this).size());
		eventManager.unregister(this, TEST_EVENT_NAME1, TEST_EVENT_HANDLER1);
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(1, handlerCounts.size());
		eventManager.unregister(eventManager, TEST_EVENT_NAME2, TEST_EVENT_HANDLER2);
		assertEquals(1, handlerCounts.size());
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(1, handlerCounts.size());
		eventManager.unregister(eventManager, TEST_EVENT_NAME1, TEST_EVENT_HANDLER2);
		assertEquals(1, handlerCounts.size());
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(0, handlerCounts.size());
		eventManager.unregister(eventManager, TEST_EVENT_NAME1, TEST_EVENT_HANDLER2);
		assertEquals(0, handlerCounts.size());
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(0, handlerCounts.size());
		eventManager.unregister(eventManager, "not.there", TEST_EVENT_HANDLER2);
		assertEquals(0, handlerCounts.size());
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(0, handlerCounts.size());
		eventManager.unregister(new Object(), "not.there", TEST_EVENT_HANDLER2);
		assertEquals(0, handlerCounts.size());
		handlerCounts = eventManager.getEventHandlerCounts();
		assertEquals(0, handlerCounts.size());
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.DefaultEventManager#initialize(com.petrivirkkula.toolbox.eventmgr.EventExecutorFactory)}.
	 */
	@Test
	public void testInitialize() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.DefaultEventManager#DefaultEventManager()}.
	 */
	@Test
	public void testDefaultEventManager() {
//		fail("Not yet implemented");
	}

}
