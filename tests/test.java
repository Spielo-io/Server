import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class test {
	
	public static void main(String[] args) {
		TestClient[] clients = new TestClient[10];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = new TestClient();
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
	
	public TestClient() {
		 try {
			socket = new Socket("127.0.0.1", 8123);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {

		try {
			OutputStream s = socket.getOutputStream();
			short length = 1000;
			byte[] buffer = shortToByteArray(length);
			s.write(buffer);
			Random rand = new Random();
			buffer = new byte[1000];
			rand.nextBytes(buffer);
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
}