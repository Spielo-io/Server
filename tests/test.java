import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Random;

import io.spielo.util.BufferHelper;

public class test {
	
	private final static String SERVER_IP = "127.0.0.1";
	//private final static String SERVER_IP = "20.52.147.95";
	
	public static void main(String[] args) {
		TestClient[] clients = new TestClient[10];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = new TestClient(SERVER_IP);
		}
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < clients.length; i++) {
				clients[i].send();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
		for (int i = 0; i < clients.length; i++) {
			clients[i].close();
		}
	}
}

class TestClient {
	private Socket socket;
	
	public TestClient(final String ip) {
		 try {
			socket = new Socket(ip, 8123);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
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
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}