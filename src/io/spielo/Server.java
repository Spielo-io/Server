package io.spielo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.spielo.events.SocketConnectedEvent;
import io.spielo.events.SocketMessageReceived;
import io.spielo.messages.ConnectMessage;
import io.spielo.messages.HeartbeatMessage;
import io.spielo.messages.Message;
import io.spielo.messages.MessageFactory;
import io.spielo.tasks.AcceptSocketTask;
import io.spielo.tasks.ConnectMessageTask;
import io.spielo.tasks.HeartbeatTask;
import io.spielo.tasks.NotifyMessageReceived;
import io.spielo.tasks.ReadMessagesTask;

public class Server implements SocketConnectedEvent, SocketMessageReceived {
	private static final int PORT = 8123;

	public static void main(String[] args) {
		Server server = new Server(PORT);
		server.start();
		System.out.println("Server started on port: " + PORT);
	}
	
	private short ids;
	
	private final Publisher publisher;
	
	private final Thread acceptSocketsThread;
	private final Thread receiveDataThread;

	private final AcceptSocketTask acceptSocketTask;
	private final ReadMessagesTask readMessagesTask;
	private final NotifyMessageReceived notifyMessageReceivedTask;
	
	private final ExecutorService executorMessageTask;
	
	public Server(final int port) {
		ids = 0;
		
		publisher = new Publisher();
		
		ServerSocket socket = createServerSocket(port);
		
		acceptSocketTask = new AcceptSocketTask(socket, this);
		readMessagesTask = new ReadMessagesTask(this);
		notifyMessageReceivedTask = new NotifyMessageReceived(publisher);
		
		acceptSocketsThread = new Thread(acceptSocketTask, "Accept-Socket-Thread");
		receiveDataThread = new Thread(readMessagesTask, "Receive-Data-Thread");
		
		executorMessageTask = Executors.newSingleThreadExecutor();
	}	
	
	public final void start() {
		acceptSocketsThread.start();
		receiveDataThread.start();
	}
	
	private final ServerSocket createServerSocket(final int port) {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public final void onSocketConnected(final Socket socket) {
		ServerClient client = new ServerClient(socket, ++ids);
		readMessagesTask.addSocket(client);
	}
	
	@Override
	public final void onSocketReceived(final ServerClient sender, final byte[] bytes) {
		MessageFactory factory = new MessageFactory();
		Message message = factory.getMessage(bytes);
		
		notifyMessageReceivedTask.setParameter(sender, message);
		executorMessageTask.execute(notifyMessageReceivedTask);
		
		if (message instanceof ConnectMessage) {
			executorMessageTask.execute(new ConnectMessageTask(sender, message.getHeader()));
		} else if (message instanceof HeartbeatMessage) {
			executorMessageTask.execute(new HeartbeatTask(sender));
		}
	}
}
