package io.spielo.events;

import java.net.Socket;

/**
 * The {@code SocketConnectedEvent} interface should be used when you want to get notified that an new socket just 
 * connected to the server.<p>
 * @author  Marc Pfister
 * @see     io.spielo.tasks.AcceptSocketsTask
 */
public interface SocketConnectedEvent {

	/**
	 * This method is an event for the server. Call this to tell the server that a new socket just connected.
	 * @param socket	The just connected socket.
	 */
	void onSocketConnected(final Socket socket);
}
