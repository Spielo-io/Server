package io.spielo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.spielo.events.SocketConnectedEvent;
import io.spielo.events.SocketMessageReceived;
import io.spielo.tasks.AcceptSocketTask;

public class Server implements SocketConnectedEvent, SocketMessageReceived {
	private static final int PORT = 8123;

	public static void main(String[] args) {
		System.out.println("Hello World!");
		Server server = new Server(PORT);
		server.start();
	}
	
	private final AcceptSocketTask acceptSocketTask;
	private final Thread acceptSocketsThread;
	
	public Server(final int port) {
		ServerSocket socket = createServerSocket(port);
		
		acceptSocketTask = new AcceptSocketTask(socket, this);
		acceptSocketsThread = new Thread(acceptSocketTask, "Accept-Socket-Thread");
	}	
	
	public final void start() {
		acceptSocketsThread.start();
	}
	
	private final ServerSocket createServerSocket(final int port) {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public final void onSocketConnected(final Socket socket) {
		System.out.println("New client connected!");
	}
	
	@Override
	public final void onSocketReceived(final Socket socket, final byte[] bytes) {
		
	}
}
