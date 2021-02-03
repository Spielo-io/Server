package io.spielo.events;

import java.net.Socket;

public interface SocketMessageReceived {

	void onSocketReceived(final Socket socket, final byte[] bytes);
}