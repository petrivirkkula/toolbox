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

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EventSourceTest extends AbstractEventSource
{
	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String rcsid = "$Id$";

	/**
	 * Logger
	 */
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(EventSourceTest.class);

	static {
		logger.loaded(rcsid, EventSourceTest.class);
	}
	
	private static final Class<GenericEvent> MY_EVENT_CLASS = GenericEvent.class;

	private static final String MY_EVENT_NAME = "myeventclass";
	
	private final int counter[] = new int[] { 0 };
	
	private EventHandler<GenericEvent> myEventHandler1 = new EventHandler<GenericEvent>() {
		@Override
		public void on(GenericEvent event) {
			logger.debug("myEventHandler1");
			synchronized(counter) {
				counter[0]++;
				counter.notify();
			}
		}
	};
	private EventHandler<GenericEvent> myEventHandler2 = new EventHandler<GenericEvent>() {
		@Override
		public void on(GenericEvent event) {
			logger.debug("myEventHandler2");
			synchronized(counter) {
				counter[0]++;
				counter.notify();
			}
			throw new RuntimeException("exception");
		}
	};
	
	private EventManager eventManager;
	
	public EventSourceTest() {
		SequentialAsyncExecutor e1 = new SequentialAsyncExecutor();
		SequentialAsyncExecutor e2 = new SequentialAsyncExecutor();
		DefaultEventExecutorFactory eFactory = new DefaultEventExecutorFactory(e1, e2);
		this.eventManager = EventManager.getInstance(eFactory);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetEventManager() {
		assertNotNull(getEventManager());
	}

	@Test
	public void testRegisterUnregister() throws InterruptedException, IOException {
		GenericEvent event1 = new GenericEvent(MY_EVENT_CLASS.getName(), this);
		GenericEvent event2 = new GenericEvent(MY_EVENT_NAME, this);

		EventStats eventStats1 = getEventManager().getEventStats();

		int triggerCalls = 0;
		
		int start = counter[0];
		register(MY_EVENT_CLASS, myEventHandler1);
		synchronized(counter) {
			trigger(event1);
			triggerCalls++;
			counter.wait();
		}
		assertEquals(start + 1, counter[0]);

		start = counter[0];
		trigger(event2);
		triggerCalls++;
		Thread.sleep(100);
		getEventManager().waitForPendingEvents();
		assertEquals(start, counter[0]);

		start = counter[0];
		unregister(MY_EVENT_CLASS, myEventHandler1);
		trigger(event1);
		triggerCalls++;
		Thread.sleep(100);
		getEventManager().waitForPendingEvents();
		assertEquals(start, counter[0]);
		trigger(event2);
		triggerCalls++;
		Thread.sleep(100);
		getEventManager().waitForPendingEvents();
		assertEquals(start, counter[0]);

		
		start = counter[0];
		register(MY_EVENT_NAME, myEventHandler1);
		synchronized(counter) {
			trigger(event2);
			triggerCalls++;
			counter.wait();
		}
		assertEquals(start + 1, counter[0]);

		start = counter[0];
		trigger(event1);
		triggerCalls++;
		Thread.sleep(100);
		getEventManager().waitForPendingEvents();
		assertEquals(start, counter[0]);

		start = counter[0];
		unregister(MY_EVENT_NAME, myEventHandler1);
		trigger(event1);
		triggerCalls++;
		Thread.sleep(100);
		getEventManager().waitForPendingEvents();
		assertEquals(start, counter[0]);
		trigger(event2);
		triggerCalls++;
		Thread.sleep(100);
		getEventManager().waitForPendingEvents();
		assertEquals(start, counter[0]);
		
		EventStats eventStats2 = getEventManager().getEventStats();
		
		assertEquals(2, eventStats2.getTotalHandlerCount() - eventStats1.getTotalHandlerCount());
		assertEquals(triggerCalls, eventStats2.getTotalEventCount() - eventStats1.getTotalEventCount());
		assertTrue(eventStats2.getLastEventTime() > eventStats1.getLastEventTime());
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
		eventStats2.printEventStats(writer);
		writer.flush();
	}

	
	@Test
	public void testRegisterUnregister2() throws InterruptedException, IOException {
		GenericEvent event1 = new GenericEvent(MY_EVENT_CLASS.getName(), this);

		EventStats eventStats1 = getEventManager().getEventStats();

		int triggerCalls = 0;
		
		int start = counter[0];
		register(MY_EVENT_CLASS, myEventHandler1);
		register(MY_EVENT_CLASS, myEventHandler2);
		synchronized(counter) {
			trigger(event1);
			triggerCalls++;
			counter.wait();
		}
		Thread.sleep(100);
		getEventManager().waitForPendingEvents();
		assertEquals(start + 2, counter[0]);
		EventStats eventStats2 = getEventManager().getEventStats();
		
		assertEquals(2, eventStats2.getTotalHandlerCount() - eventStats1.getTotalHandlerCount());
		assertEquals(triggerCalls, eventStats2.getTotalEventCount() - eventStats1.getTotalEventCount());
		assertTrue(eventStats2.getLastEventTime() > eventStats1.getLastEventTime());
		unregister(MY_EVENT_CLASS, myEventHandler1);
		unregister(MY_EVENT_CLASS, myEventHandler2);
	}
	

	@Override
	protected EventManager getEventManager() {
		return eventManager;
	}

}
