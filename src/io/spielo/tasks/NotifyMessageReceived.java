package io.spielo.tasks;

import io.spielo.Publisher;
import io.spielo.ServerClient;
import io.spielo.messages.Message;

public class NotifyMessageReceived implements Runnable {

	private final Publisher publisher;

	private ServerClient sender;
	private Message message;
	
	public NotifyMessageReceived(final Publisher publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public void run() {
		publisher.notifySubscribers(sender, message);
	}

	public final void setParameter(final ServerClient sender, final Message message) {
		this.sender = sender;
		this.message = message;
	}
}
