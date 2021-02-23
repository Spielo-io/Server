package io.spielo;

import io.spielo.client.ServerClient;
import io.spielo.messages.*;
import io.spielo.messages.types.MessageType1;
import io.spielo.messages.types.MessageType2Lobby;

import java.util.HashMap;

public class LobbyController implements Subscriber{

    private final HashMap<String, Lobby> codeLobbyMap;
    private final HashMap<Short, Lobby> idLobbyMap;

    LobbyController() {
        this.codeLobbyMap = new HashMap<>();
        this.idLobbyMap = new HashMap<>();
    }

    @Override
    public void onMessageReceived(ServerClient sender, Message message) {
        if (message instanceof CreateLobbyMessage) {
            this.handleCreateLobbyMsg(sender, (CreateLobbyMessage) message);
        }
        else if(message instanceof JoinLobbyMessage) {
            this.handleJoinLobbyMsg(sender, (JoinLobbyMessage) message);
        }
        else {
            this.handleGameMessage(sender, message);
        }
    }

    @Override
    public void onClientLostConnection(ServerClient sender) {

    }

    public void handleCreateLobbyMsg(ServerClient sender, CreateLobbyMessage message) {
        Lobby lobby = new Lobby(sender, message);
        String code = lobby.generateRandomCode();
        codeLobbyMap.put(code, lobby);
        idLobbyMap.put(sender.getID(), lobby);
        MessageHeader header = new MessageHeader((short)0, sender.getID(), MessageType1.LOBBY, MessageType2Lobby.JOINRESPONSE, System.currentTimeMillis());
        sender.send(new CreateLobbyResponseMessage(header, code));
    }

    public void handleJoinLobbyMsg(ServerClient sender, JoinLobbyMessage message) {
        Lobby lobby = codeLobbyMap.get(message.getCode());
        if(lobby == null){
            sender.send(new JoinLobbyResponseMessage(sender.getID(), 0)); //failed
            return;
        }
        lobby.onPlayerJoin(sender);
        idLobbyMap.put(sender.getID(), lobby);
        sender.send(new JoinLobbyResponseMessage(sender.getID(), 1)); //success
    }

    public void handleGameMessage(ServerClient sender, Message message) {
        Lobby lobby = idLobbyMap.get(message.getHeader().getReceiverID());
        lobby.sendOtherPlayer(sender.getID(), message);
    }

}
