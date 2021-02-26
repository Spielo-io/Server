package io.spielo;

import io.spielo.client.ServerClient;
import io.spielo.messages.lobby.CreateLobbyMessage;
import io.spielo.messages.Message;
import io.spielo.messages.lobbysettings.LobbyBestOf;
import io.spielo.messages.lobbysettings.LobbyGame;
import io.spielo.messages.lobbysettings.LobbyTimer;

import java.util.Random;

public class Lobby {
    private ServerClient host;
    private ServerClient player2;
    private String code;
    private Boolean isPublic;
    private LobbyGame game;
    private LobbyTimer timer;
    private LobbyBestOf bestOf;

    public Lobby(ServerClient host, CreateLobbyMessage message) {
        this.host = host;
        this.isPublic = message.getPublic();
        this.game = message.getGame();
        this.timer = message.getTimer();
        this.bestOf = message.getBestOf();
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

    public String getCode() {
        return this.code;
    }
}
