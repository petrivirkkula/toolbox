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

package com.petrivirkkula.toolbox.logger.impl.junit;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petrivirkkula.toolbox.logger.impl.LogContextImpl;

/**
 * @author petri
 *
 */
public class TestLogContextImpl
{

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
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#LogContextImpl()}.
	 */
	@Test
	public void testLogContextImpl() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		ctx.setAttribute("key1", "value1");
		assertTrue(ctx.getAllAttributes().size() == 1);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#LogContextImpl(com.petrivirkkula.toolbox.logger.impl.LogContextImpl)}.
	 */
	@Test
	public void testLogContextImplLogContextImpl() {
		LogContextImpl ctx1 = new LogContextImpl();
		ctx1.setAttribute("key1", "value1");
		LogContextImpl ctx2 = new LogContextImpl(ctx1);
		assertTrue(ctx2.getAllAttributes().size() == 1);
		assertEquals("value1", ctx2.getAttribute("key1"));
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#setAttribute(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetAttribute() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		ctx.setAttribute("key1", "value1");
		assertTrue(ctx.getAllAttributes().size() == 1);
		assertEquals("value1", ctx.getAttribute("key1"));
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#setAttributes(java.util.List, java.util.List)}.
	 */
	@Test
	public void testSetAttributesListOfStringListOfString() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		assertTrue(ctx.getAllAttributes().size() == 0);
		List<String> keys = new ArrayList<String>();
		keys.add("key1");
		keys.add("key2");
		keys.add("key3");
		keys.add("key4");
		keys.add("key5");
		keys.add("key6");
		List<String> values = new ArrayList<String>();
		values.add("value1");
		values.add("value2");
		values.add("value3");
		values.add("value4");
		values.add("value5");
		values.add("value6");
		ctx.setAttributes(keys, values);
		assertTrue(ctx.getAllAttributes().size() == 6);
		ctx.removeAllAttributes();
		keys.add("key7");
		try {
			ctx.setAttributes(keys, values);
			fail("expected IllegalArgumentException");
		}
		catch(IllegalArgumentException ex) {
			assertTrue(ex instanceof IllegalArgumentException);
		}
		assertTrue(ctx.getAllAttributes().size() == 0);
		ctx.removeAllAttributes();
		values.add("value7");
		values.add("value8");
		try {
			ctx.setAttributes(keys, values);
			fail("expected IllegalArgumentException");
		}
		catch(IllegalArgumentException ex) {
			assertTrue(ex instanceof IllegalArgumentException);
		}
		assertTrue(ctx.getAllAttributes().size() == 0);
		ctx.removeAllAttributes();
		keys.clear();
		values.clear();
		ctx.setAttributes(keys, values);
		assertTrue(ctx.getAllAttributes().size() == 0);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#setAttributes(java.util.Map)}.
	 */
	@Test
	public void testSetAttributesMapOfStringString() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		assertTrue(ctx.getAllAttributes().size() == 0);
		Map<String,String> map = new HashMap<String,String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		map.put("key4", "value4");
		map.put("key5", "value5");
		map.put("key6", "value6");
		ctx.setAttributes(map);
		assertTrue(ctx.getAllAttributes().size() == 6);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#getAttribute(java.lang.String)}.
	 */
	@Test
	public void testGetAttribute() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		ctx.setAttribute("key1", "value1");
		assertTrue(ctx.getAllAttributes().size() == 1);
		assertEquals("value1", ctx.getAttribute("key1"));
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#getAttributes(java.util.List)}.
	 */
	@Test
	public void testGetAttributes() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		ctx.setAttribute("key1", "value1");
		ctx.setAttribute("key2", "value2");
		ctx.setAttribute("key3", "value3");
		ctx.setAttribute("key4", "value4");
		ctx.setAttribute("key5", "value5");
		ctx.setAttribute("key6", "value6");
		assertTrue(ctx.getAllAttributes().size() == 6);
		List<String> list = new ArrayList<String>();
		List<String> values = ctx.getAttributes(list);
		assertTrue(values.size() == 0);
		list.add("key2");
		list.add("key4");
		list.add("key6");
		values = ctx.getAttributes(list);
		assertTrue(values.size() == 3);
		assertEquals("value2", values.get(0));
		assertEquals("value4", values.get(1));
		assertEquals("value6", values.get(2));
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#getAllAttributes()}.
	 */
	@Test
	public void testGetAllAttributes() {
		LogContextImpl ctx = new LogContextImpl();
		Map<String,String> all1 = ctx.getAllAttributes(); 
		assertNotNull(all1);
		assertTrue(all1.size() == 0);
		ctx.setAttribute("key1", "value1");
		Map<String,String> all2 = ctx.getAllAttributes(); 
		assertNotNull(all2);
		assertNotSame(all1, all2);
		assertTrue(all2.size() == 1);
		Map<String,String> all3 = ctx.getAllAttributes(); 
		assertNotNull(all3);
		assertNotSame(all3, all2);
		assertTrue(all3.size() == 1);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#removeAttribute(java.lang.String)}.
	 */
	@Test
	public void testRemoveAttribute() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		ctx.setAttribute("key1", "value1");
		assertTrue(ctx.getAllAttributes().size() == 1);
		assertEquals("value1", ctx.getAttribute("key1"));
		ctx.removeAttribute("key");
		assertTrue(ctx.getAllAttributes().size() == 1);
		assertEquals("value1", ctx.getAttribute("key1"));
		ctx.removeAttribute("key1");
		assertTrue(ctx.getAllAttributes().size() == 0);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#removeAttributes(java.util.Collection)}.
	 */
	@Test
	public void testRemoveAttributes() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		ctx.setAttribute("key1", "value1");
		ctx.setAttribute("key2", "value2");
		ctx.setAttribute("key3", "value3");
		ctx.setAttribute("key4", "value4");
		ctx.setAttribute("key5", "value5");
		ctx.setAttribute("key6", "value6");
		assertTrue(ctx.getAllAttributes().size() == 6);
		List<String> list = new ArrayList<String>();
		ctx.removeAttributes(list);
		assertTrue(ctx.getAllAttributes().size() == 6);
		list.add("key2");
		list.add("key4");
		list.add("key6");
		ctx.removeAttributes(list);
		assertTrue(ctx.getAllAttributes().size() == 3);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#removeAllAttributes()}.
	 */
	@Test
	public void testRemoveAllAttributes() {
		LogContextImpl ctx = new LogContextImpl();
		assertNotNull(ctx.getAllAttributes());
		ctx.setAttribute("key1", "value1");
		assertTrue(ctx.getAllAttributes().size() == 1);
		ctx.removeAllAttributes();
		assertTrue(ctx.getAllAttributes().size() == 0);
	}

	/**
	 * Test method for {@link com.petrivirkkula.toolbox.logger.impl.LogContextImpl#getCurrentLogContext()}.
	 */
	@Test
	public void testGetCurrentLogContext() {
		assertNotNull(LogContextImpl.getCurrentLogContext());
	}

}
