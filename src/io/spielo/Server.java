package io.spielo;

import io.spielo.client.ServerClient;
import io.spielo.events.SocketConnectedEvent;
import io.spielo.events.SocketMessageReceived;
import io.spielo.messages.Message;
import io.spielo.messages.MessageFactory;
import io.spielo.tasks.AcceptSocketsTask;
import io.spielo.tasks.NotifyMessageReceived;
import io.spielo.tasks.ReadMessagesTask;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Server implements SocketConnectedEvent, SocketMessageReceived {
	private static final int PORT = 8123;
	
	private static final Logger LOG = Logger.getLogger(Server.class.getName());

	public static void main(String[] args) {
		LogManager manager = LogManager.getLogManager();
		try {
			manager.readConfiguration(new FileInputStream("logging.properties"));
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		
		Server server = new Server(PORT);
		server.start();
	}
	
	private short ids;
	
	private final Publisher publisher;
	
	private final Thread acceptSocketsThread;
	private final Thread receiveDataThread;

	private final AcceptSocketsTask acceptSocketTask;
	private final ReadMessagesTask readMessagesTask;
	private final NotifyMessageReceived notifyMessageReceivedTask;
	
	private final ExecutorService executorMessageTask;
	
	public Server(final int port) {
		ids = 0;
		
		ConnectedClientController clientController = new ConnectedClientController();
		publisher = new Publisher();
		LobbyController lobbyController = new LobbyController();
		publisher.subscribe(lobbyController);
		publisher.subscribe(clientController);
		
		ServerSocket socket = createServerSocket(port);
		
		acceptSocketTask = new AcceptSocketsTask(socket, this);
		readMessagesTask = new ReadMessagesTask(this);
		notifyMessageReceivedTask = new NotifyMessageReceived(publisher);
		
		acceptSocketsThread = new Thread(acceptSocketTask, "Accept-Socket-Thread");
		receiveDataThread = new Thread(readMessagesTask, "Receive-Data-Thread");
		
		executorMessageTask = Executors.newSingleThreadExecutor();
	}	
	
	public final void start() {		
		acceptSocketsThread.start();
		receiveDataThread.start();
		LOG.info("Server started on port: " + PORT);
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
		LOG.info("New client connected.");
	}
	
	@Override
	public final void onSocketReceived(final ServerClient sender, final byte[] bytes) {
		MessageFactory factory = new MessageFactory();
		Message message = factory.getMessage(bytes);
		
		notifyMessageReceivedTask.setParameter(sender, message);
		executorMessageTask.execute(notifyMessageReceivedTask);
	}
}
