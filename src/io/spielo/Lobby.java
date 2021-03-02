package io.spielo;

import io.spielo.client.ServerClient;
import io.spielo.messages.Message;
import io.spielo.messages.lobby.CreateLobbyMessage;
import io.spielo.messages.lobby.LobbySettingsMessage;
import io.spielo.messages.lobbysettings.LobbySettings;

import java.util.Map;
import java.util.Random;

public class Lobby {
    private ServerClient host;
    private String hostName;
    private String player2Name;
    private ServerClient player2;
    private String code;
    private LobbySettings lobbySettings;

    public Lobby(ServerClient host, CreateLobbyMessage message) {
        this.host = host;
        this.hostName = message.getUsername();
        this.lobbySettings = message.getLobbySettings();
    }

    public void onPlayerJoin(ServerClient player){
        this.player2 = player;
    }

    public String generateRandomCode() {
        int leftLimit = 'a'; // letter 'a'
        int rightLimit = 'z'; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String rand = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return this.code = rand;
    }
    public void sendOtherPlayer(short sender, Message message) {
        if(sender == host.getID()){
        	if (player2 != null)
        		player2.send(message);
        }
        else {
            host.send(message);
        }
    }

    public void leave(ServerClient sender, Map<String, Lobby> map){
        if(sender.getID() == host.getID()){
            host = player2;
            hostName = player2Name;
            player2 = null;
            player2Name = null;
        }
        else if(player2 == null){
            map.remove(this.code);
        }
    }

    public void editLobby(LobbySettingsMessage msg){
        this.lobbySettings = msg.getSettings();
    }

    public String getCode() {
        return this.code;
    }

    public ServerClient getHost() {
        return host;
    }

    public ServerClient getPlayer2() {
        return player2;
    }

    public LobbySettings getLobbySettings() {
        return lobbySettings;
    }

    public String getHostName() {
        return hostName;
    }

    public String getPlayer2Name() {
        return player2Name;
    }
}
