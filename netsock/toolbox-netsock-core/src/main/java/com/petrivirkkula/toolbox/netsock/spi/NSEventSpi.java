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

 package com.petrivirkkula.toolbox.netsock.spi;

import java.util.Date;

import com.petrivirkkula.toolbox.eventmgr.Event;
import com.petrivirkkula.toolbox.eventmgr.GenericEvent;
import com.petrivirkkula.toolbox.netsock.NSService;

public abstract class NSEventSpi<SVCIF extends NSService,SVCIMPL extends NSServiceSpi> extends GenericEvent
{
	/**
	 * Serialization version UID
	 */
	private static final long serialVersionUID = 1L;
	
	
	private final SVCIMPL service;

	@SuppressWarnings("unchecked")
	public SVCIF getService() {
		return (SVCIF)service;
	}
	
	protected NSEventSpi() {
		this.service = null;
	}

	protected NSEventSpi(SVCIMPL service) {
		super();
		this.service = service;
	}

	protected NSEventSpi(SVCIMPL service, Object eventSource) {
		super(eventSource);
		this.service = service;
	}

	protected NSEventSpi(SVCIMPL service, Object eventSource, Event nestedEvent) {
		super(eventSource, nestedEvent);
		this.service = service;
	}

	protected NSEventSpi(SVCIMPL service, String eventName) {
		super(eventName);
		this.service = service;
	}

	protected NSEventSpi(SVCIMPL service, String eventName, Event nestedEvent) {
		super(eventName, nestedEvent);
		this.service = service;
	}

	protected NSEventSpi(SVCIMPL service, String eventName, Object eventSource) {
		super(eventName, eventSource);
		this.service = service;
	}

	protected NSEventSpi(SVCIMPL service, String eventName, Object eventSource, Event nestedEvent) {
		super(eventName, eventSource, nestedEvent);
		this.service = service;
	}

	protected NSEventSpi(SVCIMPL service, String eventName, Date eventTime, Object eventSource) {
		super(eventName, eventTime, eventSource);
		this.service = service;
	}

	protected NSEventSpi(SVCIMPL service, String eventName, Date eventTime, Object eventSource,
			Event nestedEvent) {
		super(eventName, eventTime, eventSource, nestedEvent);
		this.service = service;
	}

}
