package io.spielo.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.spielo.client.ServerClient;
import io.spielo.events.SocketMessageReceived;
import io.spielo.messages.util.BufferHelper;

public class ReadMessagesTask implements Runnable {

	private final Lock lock;
	private final List<ServerClient> socketsToRead;
	private final SocketMessageReceived eventHandler;
	
	public ReadMessagesTask(final SocketMessageReceived eventHandler) {
		lock = new ReentrantLock();
		this.eventHandler = eventHandler;
		socketsToRead = new ArrayList<>(50);
	}
	
	public final void addSocket(final ServerClient socket) {
		lock.lock();
		socketsToRead.add(socket);
		lock.unlock();
	}
	
	public final void removeSocket(final ServerClient socket) {
		lock.lock();
		for (int i = 0; i < socketsToRead.size(); i++) {
			if (socket == socketsToRead.get(i)) {
				socketsToRead.remove(i);
				return;
			}
		}
		lock.unlock();
	}
	
	@Override
	public void run() {
		while (true) {
			lock.lock();
			for (ServerClient client : socketsToRead) {
				try {
					InputStream in = client.getInputStream();
					if (in.available() >= 2) {
						byte[] buffer = in.readNBytes(2);
						short length = BufferHelper.fromBufferIntoShort(buffer, 0);
						
						buffer = in.readNBytes(length);
						eventHandler.onSocketReceived(client, buffer);
					}	
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			lock.unlock();
		}
	}
}
