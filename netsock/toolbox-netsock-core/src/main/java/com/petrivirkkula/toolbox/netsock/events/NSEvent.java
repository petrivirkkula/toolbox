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
package com.petrivirkkula.toolbox.netsock.events;

import java.util.Date;

import com.petrivirkkula.toolbox.eventmgr.Event;
import com.petrivirkkula.toolbox.netsock.NSService;
import com.petrivirkkula.toolbox.netsock.spi.NSEventSpi;
import com.petrivirkkula.toolbox.netsock.spi.NSServiceSpi;

public abstract class NSEvent<SVCIF extends NSService,SVCIMPL extends NSServiceSpi> extends NSEventSpi<SVCIF,SVCIMPL>
{

	/**
	 * Serialization version UID
	 */
	private static final long serialVersionUID = 1L;

	public NSEvent() {
	}

	public NSEvent(SVCIMPL service) {
		super(service);
	}

	public NSEvent(SVCIMPL service, Object eventSource) {
		super(service, eventSource);
	}

	public NSEvent(SVCIMPL service, Object eventSource, Event nestedEvent) {
		super(service, eventSource, nestedEvent);
	}

	public NSEvent(SVCIMPL service, String eventName) {
		super(service, eventName);
	}

	public NSEvent(SVCIMPL service, String eventName, Event nestedEvent) {
		super(service, eventName, nestedEvent);
	}

	public NSEvent(SVCIMPL service, String eventName, Object eventSource) {
		super(service, eventName, eventSource);
	}

	public NSEvent(SVCIMPL service, String eventName, Object eventSource, Event nestedEvent) {
		super(service, eventName, eventSource, nestedEvent);
	}

	public NSEvent(SVCIMPL service, String eventName, Date eventTime, Object eventSource) {
		super(service, eventName, eventTime, eventSource);
	}

	public NSEvent(SVCIMPL service, String eventName, Date eventTime, Object eventSource,
			Event nestedEvent) {
		super(service, eventName, eventTime, eventSource, nestedEvent);
	}

}
