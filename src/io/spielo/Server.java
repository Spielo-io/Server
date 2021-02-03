package io.spielo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.spielo.events.SocketConnectedEvent;
import io.spielo.events.SocketMessageReceived;
import io.spielo.tasks.AcceptSocketTask;
import io.spielo.tasks.ReadMessagesTask;

public class Server implements SocketConnectedEvent, SocketMessageReceived {
	private static final int PORT = 8123;

	public static void main(String[] args) {
		System.out.println("Hello World!");
		Server server = new Server(PORT);
		server.start();
	}
	
	private final Thread acceptSocketsThread;
	private final Thread receiveDataThread;

	private final AcceptSocketTask acceptSocketTask;
	private final ReadMessagesTask readMessagesTask;
	
	public Server(final int port) {
		ServerSocket socket = createServerSocket(port);
		
		acceptSocketTask = new AcceptSocketTask(socket, this);
		readMessagesTask = new ReadMessagesTask(this);
		
		acceptSocketsThread = new Thread(acceptSocketTask, "Accept-Socket-Thread");
		receiveDataThread = new Thread(readMessagesTask, "Receive-Data-Thread");
	}	
	
	public final void start() {
		acceptSocketsThread.start();
		receiveDataThread.start();
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
		readMessagesTask.addSocket(socket);
	}
	
	@Override
	public final void onSocketReceived(final Socket socket, final byte[] bytes) {
	}
}
