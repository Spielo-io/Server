package io.spielo.messages;

import io.spielo.util.*;

public class MessageFactory {
    public Message getMessage(byte[] bytes) {

        //Mache ein enum aus bytes für Type1
        MessageType1 type1 = getTypeFromByte(MessageType1.class, bytes, 4);
        switch (type1) {
            case LOBBY:
                MessageType2Lobby type2 = getTypeFromByte(MessageType2Lobby.class, bytes, 5);
                return Message.createMessage(bytes);
            case SERVER:
                MessageType2Server type3 = getTypeFromByte(MessageType2Server.class, bytes, 5);
                return Message.createMessage(bytes);
            case GAME:
                MessageType2Game type4 = getTypeFromByte(MessageType2Game.class, bytes, 5);
                return Message.createMessage(bytes);
            default:
                return null;
        }
    }
    public static<T extends Enum<T> & GenericEnumMixin> T getTypeFromByte(Class<T> enumClass, byte[] bytes, int offset){
        T type = null;
        for (T a : enumClass.getEnumConstants()) {
            if (a.getByte() == bytes[offset]) {
                type = a;
                break;
            }
        }
        if (type == null) {
            throw new NullPointerException();
        }
        return type;
    }
}
