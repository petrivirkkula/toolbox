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

 package com.petrivirkkula.toolbox.netsock.testng;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.testng.annotations.Test;

import com.petrivirkkula.toolbox.logger.Loggable;
import com.petrivirkkula.toolbox.logger.SimpleLoggable;
import com.petrivirkkula.toolbox.eventmgr.EventHandler;
import com.petrivirkkula.toolbox.eventmgr.EventManager;
import com.petrivirkkula.toolbox.netsock.NSConnection;
import com.petrivirkkula.toolbox.netsock.NSServerService;
import com.petrivirkkula.toolbox.netsock.NSServerServiceFactory;
import com.petrivirkkula.toolbox.netsock.NSService.ServiceState;
import com.petrivirkkula.toolbox.netsock.events.NSConnectionDisconnectedEvent;
import com.petrivirkkula.toolbox.netsock.events.NSConnectionReadableEvent;
import com.petrivirkkula.toolbox.netsock.events.NSServerConnectionAcceptedEvent;
import com.petrivirkkula.toolbox.netsock.events.NSServerListenerFailedEvent;

import static org.testng.AssertJUnit.*;

public class ServerAcceptTest
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
	private static final com.petrivirkkula.toolbox.logger.Logger logger = com.petrivirkkula.toolbox.logger.Logger.getLogger(ServerAcceptTest.class);

	static {
		logger.loaded(RCSID, ServerAcceptTest.class);
	}

	public static final int TEST_PORT = 45679;
	
	public static final int TEST_COUNT = 1000;
	
	public static final long TEST_DURATION = 10000;
	
	private EventManager eventManager;
	
	private NSServerService server;
	
	private InetSocketAddress socketAddress;
	
	private final Object counterMutex = new Object();
	private volatile int counter = 0;
	
	@Test(groups={"systemtest"})
	public void testConnectionAccept() throws IllegalStateException, InterruptedException, IOException {
		counter = 0;
		try {
			createServer();
			server.addListener(socketAddress);
			server.startService();
			Thread.sleep(800);
			connectToSocket(socketAddress, TEST_COUNT);
			assertTrue(TEST_COUNT < counter);
			Thread.sleep(1000);
		}
		finally {
			server.removeListener(socketAddress);
//			Thread.sleep(200);
			server.stopService();
//			Thread.sleep(100);
			server.waitForServiceState(ServiceState.STOPPED);
			server.close();
			server = null;
			logger.debug("printing event stats");
			CharArrayWriter writer = new CharArrayWriter();
			eventManager.getEventStats().printEventStats(writer);
			writer.flush();
			logger.info("event stats:\n" + writer.toString());
		}
	}

	private void connectToSocket(final InetSocketAddress socketAddress, final int count) {
		Thread threads[] = new Thread[count];
		for (int i = 0; i < count; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					Socket socket = null;
					try {
						socket = new Socket();
						socket.connect(socketAddress);
						final long startTime = System.currentTimeMillis();
						while (socket.isConnected() && (startTime + TEST_DURATION) > System.currentTimeMillis()) {
							logger.debug(new SimpleLoggable("connecting to " + socketAddress));
							OutputStream out = socket.getOutputStream();
							logger.debug(new SimpleLoggable("writing: Hello World!"));
							out.write("Hello World!".getBytes());
							out.flush();
							InputStream in = socket.getInputStream();
							byte buffer[] = new byte[100];
							int c = in.read(buffer);
							logger.debug(new SimpleLoggable("read back: " + new String(buffer, 0, c)));
//							Thread.sleep(500);
//							synchronized(counterMutex) {
//								while(counter < count) {
//									counterMutex.wait();
//								}
//							}
							logger.info(new SimpleLoggable("done"));
//							Thread.sleep(100);
						}
					}
					catch (Exception ex) {
						logger.error(new SimpleLoggable(ex, "exception: " + ex));
					}
					finally {
						if (socket != null) {
							try {
								socket.shutdownInput();
								socket.shutdownOutput();
								socket.close();
							}
							catch (Exception ex) {
								logger.error(new SimpleLoggable(ex, "exception in socket closing: " + ex));
							}
						}
					}
				}
				
			}, "Thread-" + (i + 1));
			threads[i].start();
			logger.debug(new SimpleLoggable("started thread #" + (i + 1)));
		}
		for (int i = 0; i < count; i++) {
			try {
				if (threads[i] != null) {
					threads[i].join();
					logger.debug(new SimpleLoggable("thread #" + (i + 1) + " completed"));
				}
			}
			catch (InterruptedException ex) {
				logger.error(new SimpleLoggable(ex, "interrupted exception: " + ex));
			}
		}
	}


	public void createServer() {
		socketAddress = new InetSocketAddress("127.0.0.1", TEST_PORT);
		eventManager = EventManager.getInstance();
		NSServerServiceFactory factory = NSServerServiceFactory.getInstance();
		server = factory.createServerService(eventManager);
		server.register(NSServerConnectionAcceptedEvent.class, new EventHandler<NSServerConnectionAcceptedEvent>() {
			@Override
			public void on(NSServerConnectionAcceptedEvent event) {
				logger.debug(new SimpleLoggable("got event: " + event));
				final NSConnection connection = event.getConnection();
				connection.register(NSConnectionDisconnectedEvent.class, new EventHandler<NSConnectionDisconnectedEvent>() {
					public void on(NSConnectionDisconnectedEvent event) {
						logger.info("disconnected: " + connection);
					}
				});
				connection.register(NSConnectionReadableEvent.class, new EventHandler<NSConnectionReadableEvent>() {
					public void on(NSConnectionReadableEvent event) {
						InputStream in = event.getConnection().getInputStream();
						OutputStream out = event.getConnection().getOutputStream();
						final byte buffer[] = new byte[100];
						try {
							final int c = in.read(buffer);
							logger.debug(new Loggable() { public String toLogMessage() { return "read " + c + " bytes: " + new String(buffer, 0, c); } });
							if (c > 0) {
								out.write(buffer, 0, c);
								logger.debug(new Loggable() { public String toLogMessage() { return "echoed back " + c + " bytes: " + new String(buffer, 0, c); } });
								synchronized(counterMutex) {
									counter++;
									counterMutex.notifyAll();
								}
							}
						} catch (IOException ex) {
							logger.error(ex, "exception: " + ex);
						}
					}
				});
				connection.register(NSConnectionDisconnectedEvent.class, new EventHandler<NSConnectionDisconnectedEvent>() {
					public void on(NSConnectionDisconnectedEvent event) {
						logger.debug("disconnected event");
					}
				});

			}
		});
		server.register(NSServerListenerFailedEvent.class, new EventHandler<NSServerListenerFailedEvent>() {
			@Override
			public void on(final NSServerListenerFailedEvent event) {
				logger.debug(new Loggable() { public String toLogMessage() { return "got event: " + event; } });
//				synchronized(counterMutex) {
//					counter++;
//					counterMutex.notify();
//				}
			}
		});
	}


}
