package io.spielo;

import java.util.Scanner;

import io.spielo.types.MessageType1;
import io.spielo.types.MessageType2Lobby;

public class TestConnection {
	
	private final static String SERVER_IP = "127.0.0.1";
	//private final static String SERVER_IP = "20.52.147.95";
	
	public static void main(String[] args) {
		Client[] clients = new Client[1];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = new Client(SERVER_IP);
		}
		
		for (int i = 0; i < clients.length; i++) {
			long time = System.currentTimeMillis();
			clients[i].send(new ConnectMessage(time));
		}
		Scanner s = new Scanner(System.in);
		s.nextLine();
		
		MessageHeader header = new MessageHeader(clients[0].getID(), 0, MessageType1.LOBBY, MessageType2Lobby.CREATE, System.currentTimeMillis());
		clients[0].send(new CreateLobbyMessage(header, true, (byte) 0, (byte) 0, (byte) 0));

		s.nextLine();
		for (int i = 0; i < clients.length; i++) {
			clients[i].close();
		}
		s.close();
	}
}
