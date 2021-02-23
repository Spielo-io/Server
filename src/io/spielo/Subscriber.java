package io.spielo;

import io.spielo.messages.Message;

public interface Subscriber {
	void onMessageReceived(final ServerClient sender, final Message message);
	
	void onClientLostConnection(final ServerClient sender);
}