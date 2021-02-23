package io.spielo;

import io.spielo.messages.ConnectMessage;

public class TestConnection {
	
	private final static String SERVER_IP = "127.0.0.1";
	//private final static String SERVER_IP = "spielo.lukesaltweather.de";
	
	public static void main(String[] args) {
		Client[] clients = new Client[1];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = new Client(SERVER_IP);
		}
		
		for (int i = 0; i < clients.length; i++) {
			long time = System.currentTimeMillis();
			clients[i].send(new ConnectMessage(time));
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		for (int i = 0; i < clients.length; i++) {
			clients[i].close();
		}
	}
}