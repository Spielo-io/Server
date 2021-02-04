package io.spielo;

public class TestConnection {
	
	private final static String SERVER_IP = "127.0.0.1";
	//private final static String SERVER_IP = "20.52.147.95";
	
	public static void main(String[] args) {
		Client[] clients = new Client[10];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = new Client(SERVER_IP);
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
