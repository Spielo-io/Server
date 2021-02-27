package io.spielo;

import io.spielo.client.Client;
import io.spielo.client.events.ClientEventSubscriber;
import io.spielo.messages.Message;
import io.spielo.messages.lobby.CreateLobbyResponseMessage;
import io.spielo.messages.lobby.JoinLobbyResponseMessage;
import io.spielo.messages.lobbysettings.LobbyBestOf;
import io.spielo.messages.lobbysettings.LobbyGame;
import io.spielo.messages.lobbysettings.LobbyTimer;

import java.util.Scanner;

public class TestConnection implements ClientEventSubscriber{
	
	private final static String SERVER_IP = "127.0.0.1";
	//private final static String SERVER_IP = "spielo.lukesaltweather.de";
	
	public static void main(String[] args) {
		try {
			new TestConnection();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public TestConnection() throws InterruptedException {
		Scanner s = new Scanner(System.in);
		
		Client client = new Client();
		client.subscribe(this);
		client.connect(SERVER_IP);

		s.nextLine();

		client.createLobby(false, LobbyGame.TicTacToe, LobbyBestOf.BestOf_3, LobbyTimer.Minute_3, "lukesalt");

		s.nextLine();

		Client client2 = new Client();
		client2.subscribe(new Subscriber2());
		client2.connect(SERVER_IP);

		client2.joinLobby("lukesalt Client 2", s.nextLine());

		s.nextLine();

		client.close();
		client2.close();
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

	@Override
	public void onMessageReceived(Message message) {
		System.out.println("Client 2 empfangen");
		if(message instanceof JoinLobbyResponseMessage){
			System.out.println("JoinLobbyResponse Client 2"+((JoinLobbyResponseMessage) message).getPlayerName()+" "+((JoinLobbyResponseMessage) message).getResponseCode());
		}
	}

	@Override
	public void onDisconnect() {

	}
}