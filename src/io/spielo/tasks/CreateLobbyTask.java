package io.spielo.tasks;

import io.spielo.CreateLobbyMessage;
import io.spielo.ServerClient;

public class CreateLobbyTask implements Runnable {

	private final ServerClient sender;
	private final CreateLobbyMessage message;
	
	public CreateLobbyTask(final ServerClient sender, final CreateLobbyMessage message) {
		this.sender = sender;
		this.message = message;
	}

	@Override
	public void run() {
		System.out.println("Clients wants to create a lobby.");
	}
}
