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
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.petrivirkkula.toolbox.logger.SimpleLoggable;
import com.petrivirkkula.toolbox.eventmgr.AbstractEventSource;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.netsock.NSServerListener;


/**
 * Service SPI class.
 * 
 * @author		Petri Virkkula
 * @version		$Id$
 */
public class NSServerListenerSpi extends AbstractEventSource implements NSServerListener, Serializable, Comparable<NSServerListenerSpi>
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(NSServerListenerSpi.class);

	static {
		logger.loaded(RCSID, NSServerListenerSpi.class);
	}

	private static final long serialVersionUID = 1L;

	
	public static final int DEFAULT_BACKLOG = 100;

	private final NSServerServiceSpi serverService;
	
	private final InetSocketAddress socketAddress;
	
	
	private ServerSocketChannel channel;
	
	//private ServerSocket socket;
	
	private SelectionKey selectionKey;
	
	private int backlog = DEFAULT_BACKLOG;
	
	public NSServerListenerSpi(NSServerServiceSpi serverService, InetSocketAddress socketAddress) {
		this.socketAddress = socketAddress;
		this.serverService = serverService;
	}

	
	/**
	 * @return the serverService
	 */
	protected NSServerServiceSpi getServerService() {
		return serverService;
	}


	@Override
	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}
	
	
	public ServerSocketChannel getChannel() {
		return channel;
	}

	/**
	 * @return the selectionKey
	 */
	public SelectionKey getSelectionKey() {
		return selectionKey;
	}


	public void register(Selector selector) throws IOException {
		register(selector, backlog);
	}
	
	public void register(Selector selector, int backlog) throws IOException {
		if (channel == null) {
			channel = ServerSocketChannel.open();
//			logger.debug(new SimpleLoggable("created channel for listener " + socketAddress));
			try {
				if (channel.getOption(StandardSocketOptions.SO_RCVBUF) < (16*1024)) {
					channel.setOption(StandardSocketOptions.SO_RCVBUF, 16*1024);
				}
			}
			catch(UnsupportedOperationException ex) {
				if (channel.socket() != null) {
					if (channel.socket().getReceiveBufferSize() < 16*1024) {
						channel.socket().setReceiveBufferSize(16*1024);
					}
				} else {
					logger.warn(new SimpleLoggable("SO_RCVBUF not supported"));
				}
			}
//			try {
//				if (channel.getOption(StandardSocketOptions.SO_SNDBUF) < (16*1024)) { 
//					channel.setOption(StandardSocketOptions.SO_SNDBUF, 16*1024);
//				}
//			}
//			catch(UnsupportedOperationException ex) {
//				logger.warn(new SimpleLoggable("SO_SNDBUF not supported"));
//			}
			channel.configureBlocking(false);
			try {
				channel.setOption(StandardSocketOptions.SO_REUSEADDR, Boolean.TRUE);
			}
			catch(UnsupportedOperationException ex) {
				if (channel.socket() != null) {
					channel.socket().setReuseAddress(true);
				} else {
					logger.warn(new SimpleLoggable("SO_REUSEADDR not supported"));
				}
			}
//			try {
//				channel.setOption(StandardSocketOptions.SO_KEEPALIVE, Boolean.TRUE);
//			}
//			catch(UnsupportedOperationException ex) {
//				logger.warn(new SimpleLoggable("SO_KEEPALIVE not supported"));
//			}
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
			this.backlog = backlog;
			channel.bind(socketAddress, backlog);
			logger.debug(new SimpleLoggable("created server socket: " + socketAddress));
		}
		if (selectionKey == null) {
			selectionKey = channel.register(selector, SelectionKey.OP_ACCEPT, this);
			logger.debug(new SimpleLoggable("selection key for " + socketAddress + " is " + selectionKey));
		}
	}

	
	
	public void unregister(Selector selector) {
		if (selectionKey != null) {
			selectionKey.cancel();
			selectionKey = null;
			logger.debug(new SimpleLoggable("selection key for " + socketAddress + " cancelled"));
		}
//		if (socket != null) {
//			try {
//				socket.close();
//			}
//			catch(Exception ignored) {
//				ignored = null;
//			}
//			socket = null;
//		}
		if (channel != null) {
			try {
				channel.close();
			}
			catch (IOException ignored) {
				ignored = null;
			}
			channel = null;
			logger.debug(new SimpleLoggable("closed channel for listener " + socketAddress));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof NSServerListenerSpi))
			throw new ClassCastException("obj is not instance of Listener: " + obj.getClass().getName());
		NSServerListenerSpi other = (NSServerListenerSpi) obj;
		return this.socketAddress.equals(other.socketAddress);
	}
	
	
	@Override
	public int compareTo(NSServerListenerSpi obj) {
		if (obj == null)
			return 1;
		if (!(obj instanceof NSServerListenerSpi))
			throw new ClassCastException("obj is not instance of Listener");
		NSServerListenerSpi other = (NSServerListenerSpi) obj;
		return this.socketAddress.toString().compareTo(other.socketAddress.toString());
	}
	
	
	@Override
	public int hashCode() {
		return this.socketAddress.hashCode();
	}


	public SocketChannel accept() throws IOException {
		return channel.accept();
	}


	@Override
	protected EventManager getEventManager() {
		return serverService.getEventManager();
	}



}