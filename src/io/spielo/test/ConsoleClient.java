package io.spielo.test;

import java.time.ZonedDateTime;
import java.util.Scanner;

import io.spielo.client.Client;
import io.spielo.client.events.ClientEventSubscriber;
import io.spielo.messages.Message;
import io.spielo.messages.lobbysettings.LobbyBestOf;
import io.spielo.messages.lobbysettings.LobbyGame;
import io.spielo.messages.lobbysettings.LobbySettings;
import io.spielo.messages.lobbysettings.LobbyTimer;
import io.spielo.messages.server.ConnectMessage;

public class ConsoleClient implements ClientEventSubscriber {
	private final static String DEFAULT_SERVER_IP = "spielo.lukesaltweather.de";
	
	private String name;
	
	public static void main(String[] args) {
		ConsoleClient client = new ConsoleClient();
		client.start();
	}

	private final Scanner s;
	private final Client client;
	
	public ConsoleClient() {
		s = new Scanner(System.in);
		
		System.out.print("Enter your name: ");
		name = s.nextLine();
		
		client = new Client();
		client.subscribe(this);
	}

	void start() {
		System.out.println("Started console client!");
		
		String line;
		do {
			 line = s.nextLine();
			 String[] words = line.split(" ");
			 String command = words[0];
			 
			 if (command.equals("connect")) {
				 if (words.length == 1)
					 client.connect(DEFAULT_SERVER_IP);
				 else 
					 client.connect(words[1]);
			 } else if (command.equals("create")) {
				 LobbySettings settings = getSettings();
				 client.createLobby(settings.getPublic(), settings.getGame(), settings.getBestOf(), settings.getTimer(), name);
			 } 
			 
			 
		} while (!line.equals("exit"));
		
		client.close();
		s.close();
	}
	
	@Override
	public void onMessageReceived(Message message) {
		if (message instanceof ConnectMessage) {
			
		}
		printMessage(message.getClass().toString());
	}

	@Override
	public void onDisconnect() {
		
	}
	
	private final LobbySettings getSettings() {
		System.out.print("Is public? (True or False): ");
		Boolean isPublic = s.nextBoolean();
		s.nextLine();
		
		System.out.print("Lobby Game? (TicTacToe / 4Wins): ");
		String game = s.nextLine();
		LobbyGame lobbygame;
		if (game.equals("TicTacToe")) {
			lobbygame = LobbyGame.TicTacToe;
		} else if (game.equals("4Wins")) {
			lobbygame = LobbyGame.Win4;
		}
		else {
			return null;
		}
		
		System.out.print("Timer? (30s / 1min / 3min): ");
		String timer = s.nextLine();
		LobbyTimer lobbytimer;
		if (timer.equals("30s")) {
			lobbytimer = LobbyTimer.Seconds_30;
		} else if (timer.equals("1min")) {
			lobbytimer = LobbyTimer.Minute_1;
		} else if (timer.equals("3min")) {
			lobbytimer = LobbyTimer.Minute_3;
		} else {
			return null;
		}				 

		System.out.print("BestOf? (1 / 3 / 5 / 7 / 9 / *): ");
		String bestof = s.nextLine();
		LobbyBestOf lobbybestof;
		if (bestof.equals("1")) {
			lobbybestof = LobbyBestOf.BestOf_1;
		} else if (bestof.equals("3")) {
			lobbybestof = LobbyBestOf.BestOf_3;
		} else if (bestof.equals("5")) {
			lobbybestof = LobbyBestOf.BestOf_5;
		} else if (bestof.equals("7")) {
			lobbybestof = LobbyBestOf.BestOf_7;
		} else if (bestof.equals("9")) {
			lobbybestof = LobbyBestOf.BestOf_9;
		} else if (bestof.equals("*")) {
			lobbybestof = LobbyBestOf.Indefinite;
		} else {
			return null;
		}				
		
		return new LobbySettings(isPublic, lobbygame, lobbytimer, lobbybestof);
	}
	
	private final void printMessage(String message) {
		System.out.printf("[%tH:%<tM:%<tS] > %s: %s\n", ZonedDateTime.now(), name, message);
	}
}
