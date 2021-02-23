package io.spielo;

import io.spielo.messages.ConnectMessage;
import io.spielo.messages.HeartbeatMessage;
import io.spielo.messages.Message;

public class ConnectedClientController implements Subscriber{

	@Override
	public void onMessageReceived(final ServerClient sender, final Message message) {
		if (message instanceof ConnectMessage) {
			ConnectMessage connectMessage = (ConnectMessage) message;
			onNewClientConnected(sender, connectMessage);
		} 
		else if (message instanceof HeartbeatMessage) {
			onHeartbeatReceived(sender, (HeartbeatMessage) message);
		}
	}

	@Override
	public void onClientLostConnection(final ServerClient sender) {
	}
	
	private void onNewClientConnected(final ServerClient sender, final ConnectMessage message) {
		System.out.println("New client connected. ID: " + sender.getID() + " ConnectMessageTime: " + message.getHeader().getTimestamp());
		
		ConnectMessage messageToReturn = new ConnectMessage(sender.getID(), System.currentTimeMillis());
		sender.send(messageToReturn);
	}

	private final void onHeartbeatReceived(final ServerClient sender, final HeartbeatMessage message) {
		sender.setLastHeatbeat(System.currentTimeMillis());
	}
}
