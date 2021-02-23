package io.spielo.tasks;

import io.spielo.ServerClient;
import io.spielo.messages.ConnectMessage;
import io.spielo.messages.MessageHeader;

public class ConnectMessageTask implements Runnable {

	private final ServerClient sender;
	private final MessageHeader header;
	
	public ConnectMessageTask(final ServerClient sender, final MessageHeader header) {
		this.sender = sender;
		this.header = header;
	}
	
	@Override
	public void run() {
		System.out.println("New client connected. ID: " + sender.getID() + " ConnectMessageTime: " + header.getTimestamp());
		ConnectMessage message = new ConnectMessage(sender.getID(), System.currentTimeMillis());
		sender.send(message);
	}
}
