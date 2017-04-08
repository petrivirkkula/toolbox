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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.petrivirkkula.toolbox.logger.SimpleLoggable;
import com.petrivirkkula.toolbox.eventmgr.AbstractEventSource;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.netsock.NSConnection;
import com.petrivirkkula.toolbox.netsock.NSInputStream;
import com.petrivirkkula.toolbox.netsock.NSOutputStream;


/**
 * Service SPI class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class NSConnectionSpi extends AbstractEventSource implements NSConnection, Serializable, Comparable<NSConnectionSpi>
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSConnectionSpi.class);

	static {
		logger.loaded(RCSID, NSConnectionSpi.class);
	}

	private static final long serialVersionUID = 1L;

	

	private final NSServiceSpi service;
	
	private InetSocketAddress localAddress;
	
	private InetSocketAddress remoteAddress;
	
	private SocketChannel channel;
	
	private SelectionKey selectionKey;
	
	private ByteBuffer outputBuffer;
	
	private ByteBuffer inputBuffer;

	private NSInputStreamSpi inputStream;

	private NSOutputStreamSpi outputStream;

	private final Object interestOpsMutex = new Object();
	
	private int interestOps;
	
	private boolean connectedFlag = false;
	
	public NSConnectionSpi(NSServiceSpi service, SocketChannel channel) throws IOException {
		this.service = service;
		this.channel = channel;
		this.outputBuffer = ByteBuffer.allocate(8192);
		this.inputBuffer = ByteBuffer.allocate(8192);
		this.inputStream = new NSInputStreamSpi(this);
		this.outputStream = new NSOutputStreamSpi(this);
		this.setInterestOps(SelectionKey.OP_READ);
		channel.configureBlocking(false);
		if (channel != null) {
			connectedFlag = true;
			localAddress = (InetSocketAddress) channel.getLocalAddress();
			remoteAddress = (InetSocketAddress) channel.getRemoteAddress();
		}
	}

	public boolean isConnected() {
		if (!connectedFlag)
			return connectedFlag;
		if (channel == null || !channel.isConnected() || !channel.isOpen())
			return false;
		ByteBuffer buffer = ByteBuffer.allocate(0);
		try {
			int c = channel.read(buffer);
			if (c == -1)
				return false;
		}	
		catch(ClosedByInterruptException ex) {
//			logger.warn(ex, "exception: " + ex);
			return false;
		}	
		catch(Exception ex) {
//			logger.warn(ex, "exception: " + ex);
			return false;
		}
		return true;
	}
	
	/**
	 * @return the serverService
	 */
	protected NSServiceSpi getService() {
		return service;
	}


	public InetSocketAddress getLocalAddress() {
		return localAddress;
	}
	
	public InetSocketAddress getRemoteAddress() {
		return remoteAddress;
	}
	
	public SocketChannel getChannel() {
		return channel;
	}

	/**
	 * @return the selectionKey
	 */
	public SelectionKey getSelectionKey() {
		return selectionKey;
	}


	public void register(Selector selector) throws IOException {
		if (channel == null) {
			channel = SocketChannel.open();
			if (localAddress != null)
				channel.bind(localAddress);
			try {
				if (channel.getOption(StandardSocketOptions.SO_RCVBUF) < (16*1024)) {
					channel.setOption(StandardSocketOptions.SO_RCVBUF, 16*1024);
				}
			}
			catch(UnsupportedOperationException ex) {
//				logger.warn(new SimpleLoggable("SO_RCVBUF not supported"));
				if (channel.socket() != null && channel.socket().getReceiveBufferSize() < 16*1024) {
					channel.socket().setReceiveBufferSize(16*1024);
				}
			}
			try {
				if (channel.getOption(StandardSocketOptions.SO_SNDBUF) < (16*1024)) { 
					channel.setOption(StandardSocketOptions.SO_SNDBUF, 16*1024);
				}
			}
			catch(UnsupportedOperationException ex) {
				logger.warn(new SimpleLoggable("SO_SNDBUF not supported"));
			}
			channel.configureBlocking(false);
			try {
				channel.setOption(StandardSocketOptions.SO_REUSEADDR, Boolean.TRUE);
			}
			catch(UnsupportedOperationException ex) {
				if (channel.socket() != null)
					channel.socket().setReuseAddress(true);
				else
					logger.warn(new SimpleLoggable("SO_REUSEADDR not supported"));
			}
			try {
				channel.setOption(StandardSocketOptions.SO_KEEPALIVE, Boolean.TRUE);
			}
			catch(UnsupportedOperationException ex) {
				logger.warn(new SimpleLoggable("SO_KEEPALIVE not supported"));
			}
			try {
				channel.setOption(StandardSocketOptions.SO_LINGER, -1);
			}
			catch(UnsupportedOperationException ex) {
				logger.warn(new SimpleLoggable("SO_LINGER not supported"));
			}
			try {
				channel.setOption(StandardSocketOptions.TCP_NODELAY, Boolean.TRUE);
			}
			catch(UnsupportedOperationException ex) {
				logger.warn(new SimpleLoggable("TCP_NODELAY not supported"));
			}
			channel.configureBlocking(false);
		}
		int ops = getInterestOps();
		if (selectionKey == null) {
			selectionKey = channel.register(selector, ops, this);
		} else {
			selectionKey.interestOps(ops);
		}
		
	}

	public void unregister() {
		if (selectionKey != null) {
			SelectionKey key = selectionKey;
			selectionKey = null;
			key.cancel();
		}
	}

	
	public void close() {
		unregister();
		if (channel != null) {
			try {
				channel.close();
			}
			catch (IOException ignored) {
				ignored = null;
			}
			channel = null;
		}
		connectedFlag = false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof NSConnectionSpi))
			throw new ClassCastException("obj is not instance of Connection");
		NSConnectionSpi other = (NSConnectionSpi) obj;
		if (this.remoteAddress == null && other.remoteAddress == null)
			return true;
		if (this.remoteAddress == null || other.remoteAddress == null)
			return false;
		return this.remoteAddress.equals(other.remoteAddress);
	}
	
	
	@Override
	public int compareTo(NSConnectionSpi obj) {
		if (obj == null)
			return 1;
		if (!(obj instanceof NSConnectionSpi))
			throw new ClassCastException("obj is not instance of Connection");
		NSConnectionSpi other = (NSConnectionSpi) obj;
		int c = this.remoteAddress.toString().compareTo(other.remoteAddress.toString());
		if (c != 0)
			return c;
		if (localAddress == null && other.localAddress == null)
			return 0;
		if (localAddress != null && other.localAddress == null)
			return 1;
		if (localAddress == null && other.localAddress != null)
			return -1;
		return this.localAddress.toString().compareTo(other.localAddress.toString());
	}
	
	
	@Override
	public String toString() {
		return "[local:" + localAddress + " remote:" + remoteAddress + "]";
	}
	
	@Override
	public int hashCode() {
		if (this.remoteAddress == null)
			return 0;
		return this.remoteAddress.hashCode();
	}


	@Override
	protected EventManager getEventManager() {
		return service.getEventManager();
	}


	@Override
	public NSOutputStream getOutputStream() {
		return outputStream;
	}


	@Override
	public NSInputStream getInputStream() {
		return inputStream;
	}

	public int bytesReadable() {
		synchronized(inputBuffer) {
			return inputBuffer.position();
		}
	}

	public int readInput(byte[] buffer, int offset, int length) throws IOException {
		if (buffer == null)
			throw new IllegalArgumentException("null buffer argument");
		if (offset >= buffer.length)
			throw new IllegalArgumentException("offset too big");
		if ((offset + length) > buffer.length)
			throw new IllegalArgumentException("length too big");
		int count = 0;
		synchronized(inputBuffer) {
			while (count < length) {
				if (fillInput() < 0) {
					connectedFlag = false;
				}
				if (inputBuffer.position() == 0)
					break;
				try {
					inputBuffer.flip();
					int tmp = length - count;
					if (tmp > inputBuffer.limit())
						tmp = inputBuffer.limit();
					inputBuffer.get(buffer, offset + count, tmp);
					count += tmp;
				}
				finally {
					inputBuffer.compact();
				}
			}
			if (inputBuffer.position() == inputBuffer.capacity()) {
				removeInterestOps(SelectionKey.OP_READ);
			} else {
				addInterestOps(SelectionKey.OP_READ);
			}

		}
		return count;
	}


	protected int fillInput() throws IOException {
		int count = 0;
		synchronized(inputBuffer) {
			while (channel != null && channel.isConnected() && channel.isOpen()) {
				int pos1 = inputBuffer.position();
				int c = channel.read(inputBuffer);
				if (c == -1) {
					connectedFlag = false;
				}
				int pos2 = inputBuffer.position();
				if (pos1 == pos2) {
					break;
				}
				count += (pos2 - pos1);
			}
		}
		return count;
	}

	
	public int writeOutput(byte[] buffer, int offset, int length) throws IOException {
		if (buffer == null)
			throw new IllegalArgumentException("null buffer argument");
		if (offset >= buffer.length)
			throw new IllegalArgumentException("offset too big");
		if ((offset + length) > buffer.length)
			throw new IllegalArgumentException("length too big");
		int count = 0;
		synchronized(outputBuffer) {
			while (count < length) {
				if (outputBuffer.position() < outputBuffer.limit()) {
					int tmp = length - count;
					int left = outputBuffer.limit() - outputBuffer.position(); 
					if (tmp > left)
						tmp = left;
					outputBuffer.put(buffer, offset + count, tmp);
					count += tmp;
				}
				if (flushOutputInternal() == 0)
					break;
			}
			if (outputBuffer.position() == 0) {
				removeInterestOps(SelectionKey.OP_WRITE);
			} else {
				addInterestOps(SelectionKey.OP_WRITE);
			}
				
		}
		return count;
	}

	public int flushOutput() throws IOException {
		synchronized(outputBuffer) {
			int count = flushOutputInternal();
			if (outputBuffer.position() == 0) {
				removeInterestOps(SelectionKey.OP_WRITE);
			} else {
				addInterestOps(SelectionKey.OP_WRITE);
			}
			return count;
		}
	}

	protected int flushOutputInternal() throws IOException {
		int count = 0;
		synchronized(outputBuffer) {
			while (true) {
				try {
					int pos1 = outputBuffer.position();
					outputBuffer.flip();
					channel.write(outputBuffer);
					int pos2 = outputBuffer.position();
					if (pos1 == pos2)
						break;
					count += (pos2 - pos1);
					if (outputBuffer.position() == outputBuffer.limit()) {
						break;
					}
				}
				finally {
					outputBuffer.compact();
				}
			}
		}
		return count;
	}


	/**
	 * @return the interestOps
	 */
	protected int getInterestOps() {
		synchronized(interestOpsMutex) {
			return interestOps;
		}
	}


	/**
	 * @param interestOps the interestOps to set
	 */
	protected void setInterestOps(int interestOps) {
		synchronized(interestOpsMutex) {
			int ops = this.interestOps;
			this.interestOps = interestOps;
			if (this.interestOps != ops) {
				logger.trace(new SimpleLoggable("changed interest ops from " + describeOps(ops) + " to " + describeOps(this.interestOps) + " connection: " + NSConnectionSpi.this));
				service.wakeupSelector();
			}
		}
	}


	/**
	 * @param interestOps the interestOps to set
	 */
	protected void addInterestOps(int interestOps) {
		synchronized(interestOpsMutex) {
			int ops = this.interestOps;
			this.interestOps |= interestOps;
			if (this.interestOps != ops) {
				logger.trace(new SimpleLoggable("changed interest ops from " + describeOps(ops) + " to " + describeOps(this.interestOps) + " connection: " + NSConnectionSpi.this));
				service.wakeupSelector();
			}
		}
	}

	/**
	 * @param interestOps the interestOps to set
	 */
	protected void removeInterestOps(int interestOps) {
		synchronized(interestOpsMutex) {
			int ops = this.interestOps;
			this.interestOps &= ~interestOps;
			if (this.interestOps != ops) {
				logger.trace(new SimpleLoggable("changed interest ops from " + describeOps(ops) + " to " + describeOps(this.interestOps) + " connection: " + NSConnectionSpi.this));
				service.wakeupSelector();
			}
		}
	}


	public static String describeOps(int ops) {
		StringBuffer sb = new StringBuffer();
		if ((ops & SelectionKey.OP_ACCEPT) != 0)
			sb.append("|ACCEPT");
		if ((ops & SelectionKey.OP_CONNECT) != 0)
			sb.append("|CONNECT");
		if ((ops & SelectionKey.OP_READ) != 0)
			sb.append("|READ");
		if ((ops & SelectionKey.OP_WRITE) != 0)
			sb.append("|WRITE");
		if (sb.length() < 1)
			return "<none>";
		return sb.substring(1);
	}

}