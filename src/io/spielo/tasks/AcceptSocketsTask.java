package io.spielo.tasks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.spielo.events.SocketConnectedEvent;


public class AcceptSocketsTask implements Runnable {

	private static final int CONNECTION_TIMEOUT = 2000;
	
	private Boolean isShutdown;
	
	private final ServerSocket serverSocket;
	private final SocketConnectedEvent eventHandler;
	
	public AcceptSocketsTask(final ServerSocket serverSocket, final SocketConnectedEvent eventHandler) {
		this.serverSocket = serverSocket;	
		this.eventHandler = eventHandler;
		
		initialize();
	}

	private final void initialize() {
		isShutdown = false;
		
		try {
			serverSocket.setSoTimeout(CONNECTION_TIMEOUT);
		} catch (SocketException e) {
			e.printStackTrace();
		}	
	}
	
	public final void shutdown() {
		this.isShutdown = true;
	}
	
	@Override
	public final void run() {
		while (!isShutdown) {

			try {
				final Socket socket = serverSocket.accept();
				eventHandler.onSocketConnected(socket);
			} catch (SocketTimeoutException e) {
				
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
