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

package com.petrivirkkula.toolbox.eventmgr.spi;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.petrivirkkula.toolbox.eventmgr.EventStats;


/**
 * An Event Manager Provider Interface.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class EventStatsSpi implements EventStats
{

	/**
	 * File RCS Id.
	 *
	 * $Id$
	 */
	public static final String RCSID = "$Id$";

	/**
	 * Serialization version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger
	 */
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(EventStatsSpi.class);

	static {
		logger.loaded(RCSID, EventStatsSpi.class);
	}

	
	private final SortedMap<String,Integer> eventStats = new TreeMap<String,Integer>();

	private final SortedMap<String,Integer> handlerStats = new TreeMap<String,Integer>();
	
	private int totalEventCount = 0;

	private int totalHandlerCount = 0;

	private long lastEventTime = 0;

	private long startTime = System.currentTimeMillis();


	EventStatsSpi createSnapshot() {
		EventStatsSpi result = new EventStatsSpi();
		synchronized(eventStats) {
			result.eventStats.putAll(eventStats);
			result.handlerStats.putAll(handlerStats);
			result.setTotalEventCount(totalEventCount);
			result.setTotalHandlerCount(totalHandlerCount);
			result.setLastEventTime(lastEventTime);
			result.setStartTime(startTime);
		}
		return result;
	}

	void recordStat(final String eventName, final int handlerCount) {
//		logger.trace(new FormatterLoggable("event \"{0}\" called {1} handlers", new Object[] { eventName, handlerCount }));
		synchronized(eventStats) {
			Integer old = eventStats.get(eventName);
			if (old == null)
				eventStats.put(eventName, new Integer(1));
			else
				eventStats.put(eventName, new Integer(old.intValue() + 1));
			if (handlerCount > 0) {
				old = handlerStats.get(eventName);
				if (old == null)
					handlerStats.put(eventName, new Integer(handlerCount));
				else
					handlerStats.put(eventName, new Integer(old.intValue() + handlerCount));
				
			}
			totalEventCount++;
			totalHandlerCount += handlerCount;
			lastEventTime = System.currentTimeMillis();
		}
	}



	/**
	 * @return the totalEventCount
	 */
	@Override
	public int getTotalEventCount() {
		return totalEventCount;
	}


	/**
	 * @param totalEventCount the totalEventCount to set
	 */
	public void setTotalEventCount(int totalEventCount) {
		this.totalEventCount = totalEventCount;
	}


	/**
	 * @return the totalHandlerCount
	 */
	@Override
	public int getTotalHandlerCount() {
		return totalHandlerCount;
	}



	/**
	 * @param totalHandlerCount the totalHandlerCount to set
	 */
	public void setTotalHandlerCount(int totalHandlerCount) {
		this.totalHandlerCount = totalHandlerCount;
	}



	/**
	 * @return the lastEventTime
	 */
	@Override
	public long getLastEventTime() {
		return lastEventTime;
	}



	/**
	 * @param lastEventTime the lastEventTime to set
	 */
	public void setLastEventTime(long lastEventTime) {
		this.lastEventTime = lastEventTime;
	}


	/**
	 * @return the startTime
	 */
	@Override
	public long getStartTime() {
		return startTime;
	}


	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	/**
	 * @return the eventStats
	 */
	@Override
	public SortedMap<String, Integer> getEventStats() {
		return eventStats;
	}



	/**
	 * @return the handlerStats
	 */
	@Override
	public SortedMap<String,Integer> getHandlerStats() {
		return handlerStats;
	}

	@Override
	public void printEventStats(Writer out) throws IOException {
		out.write("EventStats:\n");
		printField(out, "startTime", new Date(startTime));
		printField(out, "lastEventTime", new Date(lastEventTime));
		printField(out, "totalEventCount", totalEventCount);
		printField(out, "totalHandlerCount", totalHandlerCount);
		printMap(out, "handlerStats", handlerStats);
		printMap(out, "eventStats", eventStats);
	}

	private void printField(Writer out, String name, Date value) throws IOException {
		printField(out, name, "" + value); 
	}

	private void printField(Writer out, String name, int value) throws IOException {
		printField(out, name, "" + value); 
	}

	private void printField(Writer out, String name, String value) throws IOException {
		out.write("\t" + name + ": " + value + "\n");
	}

	private void printMap(Writer out, String name, SortedMap<String,Integer> map) throws IOException {
		out.write("\t" + name + ":\n");
		for (String key : map.keySet()) {
			Integer value = map.get(key);
			out.write("\t\t" + key + ": " + value + "\n");
		}
	}

}
