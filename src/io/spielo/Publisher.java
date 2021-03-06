package io.spielo;

import io.spielo.client.ServerClient;
import io.spielo.messages.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Publisher {
	private final List<Subscriber> subscribers; 
	
	Publisher() {
		subscribers = Collections.synchronizedList(new ArrayList<Subscriber>());
	}
	
	public final void subscribe(final Subscriber subscriber) {
		subscribers.add(subscriber);
	}
	
	public final void unsubscribe(final Subscriber subscriber) {
		subscribers.add(subscriber);
	}
	
	public final void notifySubscribers(final ServerClient sender, final Message message) {
		for (Subscriber subscriber : subscribers) {
			subscriber.onMessageReceived(sender, message);
		}
	}
}
