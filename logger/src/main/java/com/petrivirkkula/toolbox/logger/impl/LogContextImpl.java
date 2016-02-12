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

 package com.petrivirkkula.toolbox.logger.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.petrivirkkula.toolbox.logger.LogContext;

/**
 *
 * @author		Petri Virkkula 
 * @version		$Id$
 */
public class LogContextImpl implements LogContext
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
	private static final long serialVersionUID = 20100703L;

	/**
	 * Thread local list of log contexts.
	 */
	private static final InheritableThreadLocal<LogContextImpl> logContext = new InheritableThreadLocal<LogContextImpl>() {
		@Override protected LogContextImpl initialValue() {
			LogContextImpl context = new LogContextImpl();
			return context;
		}
		@Override protected LogContextImpl childValue(LogContextImpl parentContext) {
			LogContextImpl childContext = new LogContextImpl(parentContext);
			return childContext;
		}
	};


	private final Map<String,String> attributes;


	public LogContextImpl() {
		this.attributes = new TreeMap<String,String>();
	}

	public LogContextImpl(LogContextImpl parentContext) {
		this.attributes = new TreeMap<String,String>();
		synchronized(parentContext.attributes) {
			attributes.putAll(parentContext.attributes);
		}
	}





	public void setAttribute(String key, String value) {
		synchronized(attributes) {
			attributes.put(key, value);
		}
	}


	public void setAttributes(List<String> keys, List<String> values) {
		if (keys.size() != values.size())
			throw new IllegalArgumentException("list sizes differ");
		Iterator<String> keyIter = keys.iterator(); 
		Iterator<String> valueIter = values.iterator();
		synchronized(attributes) {
			while(keyIter.hasNext()) {
				String key = keyIter.next();
				String value = valueIter.next();
				attributes.put(key, value);
			}
		}
		return;
	}


	public void setAttributes(Map<String,String> keyvalues) {
		synchronized(attributes) {
			attributes.putAll(keyvalues);
		}
	}


	public String getAttribute(String key) {
		String ret = null;
		synchronized(attributes) {
			ret = attributes.get(key);
		}
		return ret;
	}


	public List<String> getAttributes(List<String> keys) {
		List<String> values = new ArrayList<String>();
		synchronized(attributes) {
			Iterator<String> keyIter = keys.iterator(); 
			while(keyIter.hasNext()) {
				String key = keyIter.next();
				values.add(attributes.get(key));
			}
		}
		return values;
	}


	public Map<String,String> getAllAttributes() {
		Map<String,String> values = new TreeMap<String,String>();
		synchronized(attributes) {
			values.putAll(attributes);
		}
		return values;
	}


	public String removeAttribute(String key) {
		String ret = null;
		synchronized(attributes) {
			ret = attributes.remove(key);
		}
		return ret;
	}


	public void removeAttributes(Collection<String> keys) {
		synchronized(attributes) {
			Iterator<String> keyIter = keys.iterator(); 
			while(keyIter.hasNext()) {
				String key = keyIter.next();
				attributes.remove(key);
			}
		}
	}


	public void removeAllAttributes() {
		synchronized(attributes) {
			attributes.clear();
		}
	}


	public static LogContext getCurrentLogContext() {
		LogContextImpl context = logContext.get();
		return context;
	}


}
