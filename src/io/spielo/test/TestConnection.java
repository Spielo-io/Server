package io.spielo.test;

import java.time.ZonedDateTime;
import java.util.Scanner;

import io.spielo.client.Client;
import io.spielo.client.events.ClientEventSubscriber;
import io.spielo.messages.Message;
import io.spielo.messages.lobby.CreateLobbyResponseMessage;
import io.spielo.messages.lobby.JoinLobbyResponseMessage;
import io.spielo.messages.lobbysettings.LobbyBestOf;
import io.spielo.messages.lobbysettings.LobbyGame;
import io.spielo.messages.lobbysettings.LobbyTimer;
import io.spielo.messages.server.ConnectMessage;

public class TestConnection implements ClientEventSubscriber{
	
	//private final static String SERVER_IP = "127.0.0.1";
	private final static String SERVER_IP = "spielo.lukesaltweather.de";
	
	public static void main(String[] args) {
		try {
			new TestConnection();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public TestConnection() throws InterruptedException {
		Scanner s = new Scanner(System.in);
		
		Client host = new Client();
		host.subscribe(new Subscriber2("Host"));
		
		Client player2 = new Client();
		player2.subscribe(new Subscriber2("Player2"));
		
		host.connect(SERVER_IP);

		s.nextLine();
		host.createLobby(false, LobbyGame.TicTacToe, LobbyBestOf.BestOf_3, LobbyTimer.Minute_3, "Host");
		
		s.nextLine();
		player2.connect(SERVER_IP);
		
		String code = s.nextLine();
		player2.joinLobby("Player 2", code);

		System.out.println("Press enter to exit.");
		s.nextLine();

		host.close();
		player2.close();
		s.close();
	}

	@Override
	public void onDisconnect() {
	}

	@Override
	public void onMessageReceived(Message message) {
		if(message instanceof CreateLobbyResponseMessage) {
			System.out.println(((CreateLobbyResponseMessage) message).getCode());
		}
		if(message instanceof JoinLobbyResponseMessage){
			System.out.println("JoinLobbyResponse Client 1"+((JoinLobbyResponseMessage) message).getPlayerName()+" "+((JoinLobbyResponseMessage) message).getResponseCode());
		}
	}
}
class Subscriber2 implements ClientEventSubscriber{

	private final String name;
	public Subscriber2(final String name) {
		this.name = name;
	}
	
	@Override
	public void onMessageReceived(Message message) {
		if (message instanceof ConnectMessage) {
			printMessage("Succesfully connected to the server");
			if (name.equals("Host")) {
				System.out.println("Press enter to create lobby.");
			} else if (name.equals("Player2")) {
				System.out.println("Enter the lobby code to join:");
			}
		} else if (message instanceof CreateLobbyResponseMessage) {
			String text = String.format("Sucessfully created a lobby. Lobbycode: %s\n", ((CreateLobbyResponseMessage) message).getCode());
			printMessage(text);
			System.out.println("Press enter to connect with the second client.");
		} else if (message instanceof JoinLobbyResponseMessage) {
			String text = String.format("Sucessfully", null);
		} else {
			printMessage("Received message " + message.getClass());
		}
	}

	@Override
	public void onDisconnect() {

	}
	
	private final void printMessage(String message) {
		System.out.printf("[%tH:%<tM:%<tS] > %s: %s\n", ZonedDateTime.now(), name, message);
	}
}