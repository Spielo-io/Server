package io.spielo.tasks;

import io.spielo.ConnectMessage;
import io.spielo.MessageHeader;
import io.spielo.ServerClient;

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
