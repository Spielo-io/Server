package io.spielo;

import io.spielo.client.ServerClient;
import io.spielo.messages.Message;
import io.spielo.messages.MessageHeader;
import io.spielo.messages.games.TicTacToeMessage;
import io.spielo.messages.games.Win4Message;
import io.spielo.messages.lobby.*;
import io.spielo.messages.types.MessageType1;
import io.spielo.messages.types.MessageType2Lobby;

import java.util.HashMap;
import java.util.Map;

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
        else if(message instanceof LobbySettingsMessage){
            this.handleSettingsMessage(sender, message);
        }
        else if(message instanceof TicTacToeMessage || message instanceof Win4Message){
            this.handleGameMessage(sender, message);
        }
        else if(message instanceof LobbyListRequestMessage){
            this.handleLobbyListRequest(sender, message);
        }
    }

    @Override
    public void onClientLostConnection(ServerClient sender) {

    }

    public void handleCreateLobbyMsg(ServerClient sender, CreateLobbyMessage message) {
        Lobby lobby = new Lobby(sender, message);
        String code = null;
        do {
            code = lobby.generateRandomCode();
        } while (codeLobbyMap.get(code) != null);
        codeLobbyMap.put(code, lobby);
        idLobbyMap.put(sender.getID(), lobby);
        MessageHeader header = new MessageHeader((short)0, sender.getID(), MessageType1.LOBBY, MessageType2Lobby.CREATERESPONSE, System.currentTimeMillis());
        sender.send(new CreateLobbyResponseMessage(header, code));
    }

    public void handleJoinLobbyMsg(ServerClient sender, JoinLobbyMessage message) {
        Lobby lobby = codeLobbyMap.get(message.getCode());
        if(lobby == null){
            sender.send(new JoinLobbyResponseMessage(sender.getID(), JoinLobbyResponseCode.Failed, "")); //failed
            return;
        }
        lobby.onPlayerJoin(sender);
        idLobbyMap.put(sender.getID(), lobby);

        //Send Responses to Player2 and Host
        sender.send(new JoinLobbyResponseMessage(sender.getID(), JoinLobbyResponseCode.Success, lobby.getHostName())); //success
        lobby.getHost().send(new JoinLobbyResponseMessage(lobby.getHost().getID(), JoinLobbyResponseCode.OtherPlayerJoined, message.getDisplayName()));

        //Send Lobby Settings to Player 2
        MessageHeader header = new MessageHeader((short)0, sender.getID(), MessageType1.LOBBY, MessageType2Lobby.SETTINGS, System.currentTimeMillis());
        sender.send(new LobbySettingsMessage(header, lobby.getLobbySettings()));
    }

    public void handleGameMessage(ServerClient sender, Message message) {
        Lobby lobby = idLobbyMap.get(message.getHeader().getReceiverID());
        lobby.sendOtherPlayer(sender.getID(), message);
    }

    public void handleSettingsMessage(ServerClient client, Message m){
        LobbySettingsMessage message = (LobbySettingsMessage) m;
        Lobby lobby = idLobbyMap.get(client.getID());
        lobby.editLobby(message);
    }

    public void handleLobbyListRequest(ServerClient client, Message m) {
        MessageHeader header = new MessageHeader((short)0, client.getID(), MessageType1.LOBBY, MessageType2Lobby.SETTINGS, System.currentTimeMillis());
        PublicLobbyListMessage list = new PublicLobbyListMessage(header, codeLobbyMap.size());
        for(Map.Entry<String, Lobby> pair : codeLobbyMap.entrySet()){
            list.addLobby(pair.getValue().getLobbySettings(), pair.getKey(), pair.getValue().getHostName());
        }
        client.send(list);
    }

}
