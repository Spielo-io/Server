package io.spielo.tasks;

import io.spielo.ServerClient;

public class HeartbeatTask implements Runnable {

	private final ServerClient sender;
	
	public HeartbeatTask(final ServerClient sender) {
		this.sender = sender;
	}

	@Override
	public void run() {
		sender.setLastHeatbeat(System.currentTimeMillis());
	}
}
