package io.spielo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private final int PORT = 8123;
	
	public Server() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(PORT);
			Socket s = serverSocket.accept();
			s.close();
			serverSocket.close();
			System.out.println("New client connected!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void start() {
		
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Server server = new Server();
		server.start();
	}
}
