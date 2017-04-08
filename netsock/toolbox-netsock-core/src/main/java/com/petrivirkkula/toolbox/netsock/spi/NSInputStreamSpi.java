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


import java.io.EOFException;
import java.io.IOException;

import com.petrivirkkula.toolbox.netsock.NSInputStream;



/**
 * Service SPI class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class NSInputStreamSpi extends NSInputStream
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSInputStreamSpi.class);

	static {
		logger.loaded(RCSID, NSInputStreamSpi.class);
	}

	private final NSConnectionSpi connection;
	
	
	
	/**
	 * @param connection
	 */
	public NSInputStreamSpi(NSConnectionSpi connection) {
		super();
		this.connection = connection;
	}

	

	/* (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		byte[] b = new byte[1];
		int c = readBuffer(b, 0, b.length);
		if (c < 0)
			return -1;
		if (c == 0)
			throw new EOFException("end-of-file");
		return b[0];
	}




	/* (non-Javadoc)
	 * @see java.io.InputStream#read(byte[])
	 */
	@Override
	public int read(byte[] b) throws IOException {
		return readBuffer(b, 0, b.length);
	}




	/* (non-Javadoc)
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return readBuffer(b, off, len);
	}




	/* (non-Javadoc)
	 * @see java.io.InputStream#skip(long)
	 */
	@Override
	public long skip(long n) throws IOException {
		return super.skip(n);
	}




	/* (non-Javadoc)
	 * @see java.io.InputStream#available()
	 */
	@Override
	public int available() throws IOException {
		return connection.bytesReadable();
	}




	/* (non-Javadoc)
	 * @see java.io.InputStream#close()
	 */
	@Override
	public void close() throws IOException {
		super.close();
		connection.close();
	}




	/* (non-Javadoc)
	 * @see java.io.InputStream#mark(int)
	 */
	@Override
	public synchronized void mark(int readlimit) {
		super.mark(readlimit);
	}




	/* (non-Javadoc)
	 * @see java.io.InputStream#reset()
	 */
	@Override
	public synchronized void reset() throws IOException {
		super.reset();
	}




	/* (non-Javadoc)
	 * @see java.io.InputStream#markSupported()
	 */
	@Override
	public boolean markSupported() {
		return super.markSupported();
	}


	protected int readBuffer(byte[] buffer, int offset, int length) throws IOException {
		return connection.readInput(buffer, offset, length);
	}

}
