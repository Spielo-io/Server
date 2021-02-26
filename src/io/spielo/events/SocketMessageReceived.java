package io.spielo.events;

import io.spielo.client.ServerClient;

public interface SocketMessageReceived {

	void onSocketReceived(final ServerClient socket, final byte[] bytes);
}