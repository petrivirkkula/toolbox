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


import java.io.IOException;

import com.petrivirkkula.toolbox.netsock.NSOutputStream;



/**
 * Service SPI class.
 * 
 * @author		Petri Virkkula
 * @version		$I$
 */
public class NSOutputStreamSpi extends NSOutputStream
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSOutputStreamSpi.class);

	static {
		logger.loaded(RCSID, NSOutputStreamSpi.class);
	}

	private final NSConnectionSpi connection;
	
	
	
	/**
	 * @param connection
	 */
	public NSOutputStreamSpi(NSConnectionSpi connection) {
		super();
		this.connection = connection;
	}

	
	@Override
	public void write(int b) throws IOException {
		byte buffer[] = new byte[1];
		buffer[0] = (byte)(b & 0xff);
		writeBuffer(buffer, 0, 1);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException {
		if (b == null)
			throw new IllegalArgumentException("null argument");
		if (b.length > 0)
			writeBuffer(b, 0, b.length);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int offset, int len) throws IOException {
		if (b == null)
			throw new IllegalArgumentException("null argument");
		if (offset >= b.length)
			throw new IllegalArgumentException("offset too big");
		if ((offset + len) >= b.length)
			throw new IllegalArgumentException("length too big");
		writeBuffer(b, offset, len);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		super.flush();
		connection.flushOutput();
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		super.close();
		connection.close();
	}

	
	protected void writeBuffer(byte[] buffer, int offset, int length) throws IOException {
		connection.writeOutput(buffer, offset, length);
	}


}
