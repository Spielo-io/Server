package io.spielo.tasks;

import io.spielo.events.SocketMessageReceived;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadMessagesTask implements Runnable {

	private final Lock lock;
	private final List<Socket> socketsToRead;
	private final SocketMessageReceived eventHandler;
	
	public ReadMessagesTask(final SocketMessageReceived eventHandler) {
		lock = new ReentrantLock();
		this.eventHandler = eventHandler;
		socketsToRead = new ArrayList<>(50);
	}
	
	public final void addSocket(final Socket socket) {
		lock.lock();
		socketsToRead.add(socket);
		lock.unlock();
	}
	
	@Override
	public void run() {
		while (true) {
			lock.lock();
			for (Socket socket : socketsToRead) {
				try {
					InputStream in = socket.getInputStream();
					if (in.available() >= 2) {
						byte[] buffer = in.readNBytes(2);
						short length = byteArrayToShort(buffer, 0);
						
						buffer = in.readNBytes(length);
						eventHandler.onSocketReceived(socket, buffer);
					}	
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			lock.unlock();
		}
	}
	
	private short byteArrayToShort(byte[] buffer, int offset) {
		return (short)((buffer[0 + offset] & 0xFF) << 8 | 
					   (buffer[1 + offset] & 0xFF) << 0);
	}

}
