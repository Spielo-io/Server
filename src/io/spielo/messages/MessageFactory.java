package io.spielo.messages;

import io.spielo.util.MessageType1;
import io.spielo.util.MessageType2Game;
import io.spielo.util.MessageType2Lobby;
import io.spielo.util.MessageType2Server;

public class MessageFactory {
    public Message getMessage(byte[] bytes) {

        //Mache ein enum aus bytes für Type1
        MessageType1 type1 = null;
        for (MessageType1 a : MessageType1.values()) {
            if (a.getByte() == bytes[4]) {
                type1 = a;
                break;
            }
        }
        if (type1 == null) {
            throw new NullPointerException();
        }
        switch (type1) {
            case LOBBY:
                MessageType2Lobby type2 = null;
                for (MessageType2Lobby a : MessageType2Lobby.values()) {
                    if (a.getByte() == bytes[5]) {
                        type2 = a;
                        break;
                    }
                }
                if (type2 == null) {
                    throw new NullPointerException();
                }
                return Message.createMessage(bytes);
            case SERVER:
                MessageType2Server type3 = null;
                for (MessageType2Server a : MessageType2Server.values()) {
                    if (a.getByte() == bytes[5]) {
                        type3 = a;
                        break;
                    }
                }
                if (type3 == null) {
                    throw new NullPointerException();
                }
                return Message.createMessage(bytes);
            case GAME:
                MessageType2Game type4 = null;
                for (MessageType2Game a : MessageType2Game.values()) {
                    if (a.getByte() == bytes[5]) {
                        type4 = a;
                        break;
                    }
                }
                if (type4 == null) {
                    throw new NullPointerException();
                }
                return Message.createMessage(bytes);
            default:
                return null;
        }
    }
}
