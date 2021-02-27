package io.spielo;

import io.spielo.client.ServerClient;
import io.spielo.messages.lobby.CreateLobbyMessage;
import io.spielo.messages.Message;
import io.spielo.messages.lobby.LobbySettingsMessage;
import io.spielo.messages.lobbysettings.LobbyBestOf;
import io.spielo.messages.lobbysettings.LobbyGame;
import io.spielo.messages.lobbysettings.LobbyTimer;

import java.util.Random;

public class Lobby {
    private ServerClient host;
    private String hostName;
    private String player2Name;
    private ServerClient player2;
    private String code;
    private Boolean isPublic;
    private LobbyGame game;
    private LobbyTimer timer;
    private LobbyBestOf bestOf;

    public Lobby(ServerClient host, CreateLobbyMessage message) {
        this.host = host;
        this.hostName = message.getUsername();
        this.isPublic = message.getLobbySettings().getPublic();
        this.game = message.getLobbySettings().getGame();
        this.timer = message.getLobbySettings().getTimer();
        this.bestOf = message.getLobbySettings().getBestOf();
    }

    public void onPlayerJoin(ServerClient player){
        this.player2 = player;
    }

    public String generateRandomCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
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
            player2.send(message);
        }
        else {
            host.send(message);
        }
    }

    public void editLobby(LobbySettingsMessage msg){
        this.bestOf = msg.getSettings().getBestOf();
        this.isPublic = msg.getSettings().getPublic();
        this.timer = msg.getSettings().getTimer();
        this.game = msg.getSettings().getGame();
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

    public Boolean getPublic() {
        return isPublic;
    }

    public LobbyGame getGame() {
        return game;
    }

    public LobbyTimer getTimer() {
        return timer;
    }

    public LobbyBestOf getBestOf() {
        return bestOf;
    }

    public String getHostName() {
        return hostName;
    }

    public String getPlayer2Name() {
        return player2Name;
    }
}
