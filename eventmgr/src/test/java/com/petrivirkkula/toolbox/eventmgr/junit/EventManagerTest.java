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
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.logger.Logger;
import com.petrivirkkula.toolbox.eventmgr.DefaultEventExecutorFactory;
import com.petrivirkkula.toolbox.eventmgr.DefaultEventManager;
import com.petrivirkkula.toolbox.eventmgr.Event;
import com.petrivirkkula.toolbox.eventmgr.EventHandler;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.eventmgr.EventRuntimeException;
import com.petrivirkkula.toolbox.eventmgr.EventStats;
import com.petrivirkkula.toolbox.eventmgr.GenericEvent;
import com.petrivirkkula.toolbox.eventmgr.spi.EventManagerSpi;

/**
 * @author Petri Virkkula
 *
 */
public class EventManagerTest
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(EventManagerTest.class);

	static {
		logger.loaded(RCSID, EventManagerTest.class);
	}

	public static abstract class MyAbstractEventManager extends EventManagerSpi {
	}

	private static class MyPrivateEventManager extends EventManagerSpi {
		@Override
		public Map<Object, Map<String, Integer>> getEventHandlerCounts() {
			return null;
		}
		@Override
		public <E extends Event> void trigger(E event) {
		}
		@Override
		public <E extends Event> EventManager register(Object eventSource, String eventName, EventHandler<E> eventHandler) {
			return null;
		}
		@Override
		public <E extends Event> EventManager unregister(Object eventSource, String eventName, EventHandler<E> eventHandler) {
			return null;
		}
		@Override
		protected Logger getLogger() {
			return null;
		}
		@Override
		protected <E extends Event> List<EventHandler<? extends Event>> lookupHandlers(E event) {
			return null;
		}
	}

	
	public static class MyPublicEventManager extends MyPrivateEventManager {
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
	 */
	@Test
	public void testGetInstance() {
		EventManager eventManager = EventManager.getInstance();
		assertEquals(DefaultEventManager.class, eventManager.getClass());
	}


	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventManager#getInstance(com.petrivirkkula.toolbox.eventmgr.EventExecutorFactory)}.
	 */
	@Test
	public void testGetInstanceEventExecutorFactory() {
		EventManager eventManager = EventManager.getInstance(new DefaultEventExecutorFactory());
		assertEquals(DefaultEventManager.class, eventManager.getClass());
		EventHandler<GenericEvent> eventHandler = new EventHandler<GenericEvent>(){
			@Override
			public void on(GenericEvent event) {
			}
		};
		eventManager.register(this, GenericEvent.class, eventHandler);
		eventManager.unregister(this, GenericEvent.class, eventHandler);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventManager#getInstance(com.petrivirkkula.toolbox.eventmgr.EventExecutorFactory, java.lang.Class)}.
	 */
	@Test
	public void testGetInstanceEventExecutorFactoryClassOfQextendsEventManagerSpi() {
		EventManager eventManager = EventManager.getInstance(new DefaultEventExecutorFactory(), DefaultEventManager.class);
		assertEquals(DefaultEventManager.class, eventManager.getClass());
		eventManager = EventManager.getInstance(new DefaultEventExecutorFactory(), DefaultEventManager.class);
		assertEquals(DefaultEventManager.class, eventManager.getClass());
		eventManager = EventManager.getInstance(new DefaultEventExecutorFactory(), MyPublicEventManager.class);
		assertEquals(MyPublicEventManager.class, eventManager.getClass());
		try {
			eventManager = EventManager.getInstance(new DefaultEventExecutorFactory(), MyPrivateEventManager.class);
			fail("expected exception");
		}
		catch(EventRuntimeException ex) {
			assertEquals(IllegalAccessException.class, ex.getCause().getClass());
		}

	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventManager#getInstance(com.petrivirkkula.toolbox.eventmgr.EventExecutorFactory, java.lang.Class)}.
	 */
	@Test
	public void testGetInstanceEventExecutorFactoryClassOfQextendsEventManagerSpi2() {
		try {
			EventManager.getInstance(new DefaultEventExecutorFactory(), MyAbstractEventManager.class);
			fail("expected exception");
		}
		catch(EventRuntimeException ex) {
			assertEquals(InstantiationException.class, ex.getCause().getClass());
		}

	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.eventmgr.EventManager#getEventStats()}.
	 */
	@Test
	public void testGetEventStats() {
		EventManager eventManager = EventManager.getInstance();
		assertNotNull(eventManager);
		EventStats stats = eventManager.getEventStats();
		assertNotNull(stats);
		stats.getEventStats();
		stats.getHandlerStats();
		stats.getStartTime();
		stats.getTotalEventCount();
		stats.getTotalHandlerCount();
	}

	
}
