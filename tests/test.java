import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

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
			byte[] buffer = shortToByteArray(length);
			s.write(buffer);
			buffer = new byte[length];
			buffer[0] = shortToByteArray(senderID)[0];
			buffer[1] = shortToByteArray(senderID)[1];
			buffer[2] = shortToByteArray(receiverID)[0];
			buffer[3] = shortToByteArray(receiverID)[1];
			buffer[4] = (byte)1;
			buffer[5] = (byte)0;
			buffer[6] = longToByteArray(timestamp)[0];
			buffer[7] = longToByteArray(timestamp)[1];
			buffer[8] = longToByteArray(timestamp)[2];
			buffer[9] = longToByteArray(timestamp)[3];
			buffer[10] = longToByteArray(timestamp)[4];
			buffer[11] = longToByteArray(timestamp)[5];
			buffer[12] = longToByteArray(timestamp)[6];
			buffer[13] = longToByteArray(timestamp)[7];
			
			s.write(buffer);
			s.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	private static byte[] shortToByteArray(final short value) {
		return new byte[] {
			(byte)(value >> 8),
			(byte)(value >> 0)
		};
	}
	
	private static byte[] longToByteArray(final long value) {
		return new byte[] {
			(byte)(value >> 56),
			(byte)(value >> 48),
			(byte)(value >> 40),
			(byte)(value >> 32),
			(byte)(value >> 24),
			(byte)(value >> 16),
			(byte)(value >> 8),
			(byte)(value >> 0)
		};
	}
}