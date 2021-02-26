package io.spielo;

import io.spielo.client.Client;
import io.spielo.client.events.ClientEventSubscriber;
import io.spielo.messages.lobby.CreateLobbyResponseMessage;
import io.spielo.messages.Message;
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

		client.createLobby(false, LobbyGame.TicTacToe, LobbyBestOf.BestOf_3, LobbyTimer.Minute_3, "lukesalt");

		s.nextLine();
		

		
		/*
		 * game5Win(int i)
		 * gameTicTacToe(int i)
		 * createLobby(isPublic, LobbyGame, BestOf, LobbyTimer, string username)
		 * lobbySettings(isPublic, LobbyGame, BestOf, LobbyTimer)
		 * joinRandomLobby(string username)
		 * joinLobby(string username, String code)
		 * isReady(bool is)
		 *
		 * LobbyListMessage
		 * Message4Wins
		 * MessageTicTacToe
		 * MessageLobbyJoined
		 * MessageLobbyLeaved
		 * LobbySettingsMessage
		 * GameStartMessage
		 */
			
		client.close();
		s.close();
	}

	@Override
	public void onDisconnect() {
	}

	@Override
	public void onMessageReceived(Message message) {
		if(message instanceof CreateLobbyResponseMessage) {
			System.out.println(message.toString());
		}
	}
}
