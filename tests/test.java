import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class test {
	
	private final static String SERVER_IP = "127.0.0.1";
	//private final static String SERVER_IP = "20.52.147.95";
	
	public static void main(String[] args) {
		TestClient[] clients = new TestClient[1];
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

			shortIntoByteArray(buffer, 0, senderID);
			shortIntoByteArray(buffer, 2, receiverID);
			buffer[4] = (byte) 0;
			buffer[5] = (byte) 1;
			longIntoByteArray(buffer, 6, timestamp);

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
	
	public static void shortIntoByteArray(final byte[] bytes, final int offset, final short value)
	{
		bytes[offset] = (byte)value;
		bytes[offset + 1] = (byte)(value >> 8);
	}

	public static void intIntoByteArray(final byte[] bytes, final int offset, final short value)
	{
		bytes[offset] = (byte)value;
		bytes[offset + 1] = (byte)(value >> 8);
		bytes[offset + 2] = (byte)(value >> 16);
		bytes[offset + 3] = (byte)(value >> 24);
	}
	
	public static void longIntoByteArray(final byte[] bytes, final int offset, final long value) {
		int shift = 0;
		for (int i = 0; i < 8; i++) {
			bytes[offset + i] = (byte)(value >> shift);
			shift += 8;
		}
	}
}