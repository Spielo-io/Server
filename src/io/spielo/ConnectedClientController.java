package io.spielo;

import java.util.logging.Logger;

import io.spielo.client.ServerClient;
import io.spielo.messages.server.ConnectMessage;
import io.spielo.messages.server.DisconnectMessage;
import io.spielo.messages.server.HeartbeatMessage;
import io.spielo.tasks.ReadMessagesTask;
import io.spielo.messages.Message;

public class ConnectedClientController implements Subscriber{

	private static final Logger LOG = Logger.getLogger(ConnectedClientController.class.getName());
	private ReadMessagesTask readMessagesTask;

	@Override
	public void onMessageReceived(final ServerClient sender, final Message message) {
		if (message instanceof ConnectMessage) {
			ConnectMessage connectMessage = (ConnectMessage) message;
			onNewClientConnected(sender, connectMessage);
		} 
		else if (message instanceof HeartbeatMessage) {
			onHeartbeatReceived(sender, (HeartbeatMessage) message);
		} 
		else if (message instanceof DisconnectMessage) {
			onDisconnectReceived(sender, (DisconnectMessage) message);
		}
	}

	@Override
	public void onClientLostConnection(final ServerClient sender) {
	}
	
	private void onNewClientConnected(final ServerClient sender, final ConnectMessage message) {
		LOG.info("The connected clients ID is " + sender.getID());
		
		ConnectMessage messageToReturn = new ConnectMessage(sender.getID(), System.currentTimeMillis());
		sender.send(messageToReturn);
	}

	private final void onHeartbeatReceived(final ServerClient sender, final HeartbeatMessage message) {
		LOG.fine("ClientID: " + sender.getID() + " Received heartbeat");
		
		sender.setLastHeartbeat(System.currentTimeMillis());
	}

	private final void onDisconnectReceived(final ServerClient sender, final DisconnectMessage message) {
		LOG.info("ClientID: " + sender.getID() + " just disconnected");
		
		readMessagesTask.removeSocket(sender);
	}

	public final void setReadMessageTask(final ReadMessagesTask readMessagesTask) {
		this.readMessagesTask = readMessagesTask;
	}
}
