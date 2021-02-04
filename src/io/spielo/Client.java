/**
 * 
 */
package io.spielo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import io.spielo.util.BufferHelper;

public class Client {
	private final static int PORT = 8123;

	private final Socket socket;
	
	public Client(final String ip) {
		socket = connectSocket(ip);
	}

	public final void send() {
		short senderID = 1, receiverID = 0;
		long timestamp = System.currentTimeMillis();
		try {
			OutputStream s = socket.getOutputStream();
			short length = 14;
			byte[] buffer = new byte[length + 2];
			BufferHelper.shortIntoBuffer(buffer, 0, length); 
			BufferHelper.shortIntoBuffer(buffer, 2, senderID);
			BufferHelper.shortIntoBuffer(buffer, 4, receiverID);
			buffer[6] = (byte)1;
			buffer[7] = (byte)0;
			BufferHelper.longIntoBuffer(buffer, 8, timestamp);
			
			s.write(buffer);
			s.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public final void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final Socket connectSocket(final String ip) {
		try {
			return new Socket(ip, PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
