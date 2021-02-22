package io.spielo;

public interface Subscriber {
	void onMessageReceived(final ServerClient sender, final Message message);
}