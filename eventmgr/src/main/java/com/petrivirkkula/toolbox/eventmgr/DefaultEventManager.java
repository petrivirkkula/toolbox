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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.petrivirkkula.toolbox.logger.SimpleLoggable;
import com.petrivirkkula.toolbox.eventmgr.spi.EventManagerSpi;



/**
 * The Default Event Manager Implementation class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class DefaultEventManager extends EventManagerSpi
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
	private static final com.petrivirkkula.toolbox.logger.Logger LOGGER = com.petrivirkkula.toolbox.logger.Logger.getLogger(DefaultEventManager.class);

	static {
		LOGGER.loaded(RCSID, DefaultEventManager.class);
	}

	
	/**
	 * Map of handlers.
	 */
	private final Map<Object,SortedMap<String,List<EventHandler<? extends Event>>>> eventHandlers;
		
	
	/**
	 * Constructor without parameters.
	 */
	public DefaultEventManager() {
		super();
		this.eventHandlers = new HashMap<Object, SortedMap<String, List<EventHandler<? extends Event>>>>();
		getLogger().debug(new SimpleLoggable("created default event manager"));
	}


	/**
	 * Gets LOGGER.
	 * 
	 * @return	LOGGER
	 */
	protected com.petrivirkkula.toolbox.logger.Logger getLogger() {
		return LOGGER;
	}

	


	@Override
	public <E extends Event> EventManager register(Object eventSource, String eventName, EventHandler<E> eventHandler) {
		Map<String,List<EventHandler<? extends Event>>> handlerMap = lookupHandlerMapFor(eventSource, true);
		List<EventHandler<? extends Event>> handlers = lookupHandlersFor(handlerMap, eventName, true);
		boolean ok = false;
		synchronized(handlers) {
			if (!handlers.contains(eventHandler)) {
				handlers.add(eventHandler);
				ok = true;
			}
		}
		if (ok) {
//			getLogger().debug(new SimpleLoggable("added handler on " + eventSource + " for " + eventName));
		}
		return this;
	}


	@Override
	public <E extends Event> EventManager unregister(Object eventSource, String eventName, EventHandler<E> eventHandler) {
		Map<String,List<EventHandler<? extends Event>>> handlerMap = lookupHandlerMapFor(eventSource, false);
		if (handlerMap == null)
			return this;
		List<EventHandler<? extends Event>> handlers = lookupHandlersFor(handlerMap, eventName, false);
		if (handlers == null)
			return this;
		synchronized(handlers) {
			if (handlers.contains(eventHandler))
				handlers.remove(eventHandler);
//			if (handlers.size() == 0) {
//				lookupHandlersFor(handlerMap, eventName, false);
//			}
		}
//		handlerMap = lookupHandlerMapFor(eventSource, false);
		return this;
	}


	@Override
	public void initialize(EventExecutorFactory executorFactory) {
		super.initialize(executorFactory);
	}


	@Override
	protected <E extends Event> List<EventHandler<? extends Event>> lookupHandlers(final E event) {
		Object eventSource = event.getEventSource();
		Map<String,List<EventHandler<? extends Event>>> handlerMap = lookupHandlerMapFor(eventSource, false);
		if (handlerMap == null) {
//			getLogger().trace(new SimpleLoggable("no handlers defined for object " + eventSource));
			return null;
		}
		List<EventHandler<? extends Event>> handlers = lookupHandlersFor(handlerMap, event.getEventName(), false);
		return handlers;
	}


	/**
	 * Looks up for handler map for a given event source.
	 * 
	 * @param eventSource	event source
	 * @param create		in case no handler map exists: if true create a new handler map if none exists for the event source; otherwise return null 
	 * @return				handler map, or null if create is false and no handler map exists for the event source
	 */
	protected Map<String,List<EventHandler<? extends Event>>> lookupHandlerMapFor(Object eventSource, boolean create) {
		SortedMap<String,List<EventHandler<? extends Event>>> handlerMap = null;
		synchronized(eventHandlers) {
			handlerMap = eventHandlers.get(eventSource);
			if (handlerMap == null && create) {
				handlerMap = new TreeMap<String,List<EventHandler<? extends Event>>>();
				eventHandlers.put(eventSource, handlerMap);
//			} else if (handlerMap != null && !create && handlerMap.size() == 0) {
//				eventHandlers.remove(eventSource);
			}
		}
		return handlerMap;
	}


	/**
	 * Looks up for event handler list for the given event name.
	 * 
	 * @param	handlerMap	handler map
	 * @param	eventName	event name
	 * @param	create		in case no handlers exists: if true create a new handler list if none exists for the event name; otherwise return null 
	 * @return				handler list, or null if create is false and no handler list exists for the event name
	 */
protected List<EventHandler<? extends Event>> lookupHandlersFor(Map<String,List<EventHandler<? extends Event>>> handlerMap, String eventName, boolean create) {
		List<EventHandler<? extends Event>> handlers = null;
		synchronized(handlerMap) {
			handlers = handlerMap.get(eventName);
			if (handlers == null && create) {
				handlers = new ArrayList<EventHandler<? extends Event>>();
				handlerMap.put(eventName, handlers);
//			} else if (handlers != null && !create && handlers.size() == 0) {
//				handlerMap.remove(eventName);
			}
		}
		return handlers;
	}

	
	@Override
	public Map<Object,Map<String,Integer>> getEventHandlerCounts() {
		Map<Object,Map<String,Integer>> result = new HashMap<Object,Map<String,Integer>>();
		synchronized(eventHandlers) {
			Iterator<Object> sourceIter = eventHandlers.keySet().iterator();
			while (sourceIter.hasNext()) {
				Object source = sourceIter.next();
				Map<String,List<EventHandler<? extends Event>>> handlerList = eventHandlers.get(source);
//				if (handlerList.size() == 0) {
//					eventHandlers.remove(source);
//					continue;
//				}
				Map<String,Integer> counters = new TreeMap<String,Integer>();
				Iterator<String> namesIter = handlerList.keySet().iterator();
				while (namesIter.hasNext()) {
					String name = namesIter.next();
					List<EventHandler<? extends Event>> handlers = handlerList.get(name);
//					if (handlers.size() == 0) {
//						handlerList.remove(name);
//						continue;
//					}
					if (handlers.size() > 0)
						counters.put(name,  new Integer(handlers.size()));
				}
				if (counters.size() > 0)
					result.put(source, counters);
			}
		}
		return result;
		
	}



}
