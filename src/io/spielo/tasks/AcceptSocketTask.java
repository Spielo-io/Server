package io.spielo.tasks;

import java.net.ServerSocket;
import java.net.SocketException;

import io.spielo.events.SocketConnectedEvent;


public class AcceptSocketTask implements Runnable {

	private final int CONNECTION_TIMEOUT = 1000;
	
	private Boolean isShutdown;
	
	private final ServerSocket serverSocket;
	private final SocketConnectedEvent eventHandler;
	
	public AcceptSocketTask(final ServerSocket serverSocket, final SocketConnectedEvent eventHandler) {
		this.serverSocket = serverSocket;	
		this.eventHandler = eventHandler;
		
		initialize();
	}

	private void initialize() {
		isShutdown = false;
		
		try {
			serverSocket.setSoTimeout(CONNECTION_TIMEOUT);
		} catch (SocketException e) {
			e.printStackTrace();
		}	
	}
	
	public void shutdown() {
		this.isShutdown = true;
	}
	
	@Override
	public void run() {
		while (!isShutdown) {
		}
	}
}
