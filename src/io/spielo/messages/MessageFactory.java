package io.spielo.messages;

import io.spielo.util.MessageType1;

public class MessageFactory {
    public Message getMessage(byte[] bytes) {
        MessageType1 type1 = null;
        for (MessageType1 a : MessageType1.values()) {
            if (a.getByte() == bytes[2]) {
                type1 = a;
                break;
            }
        }
        if (type1 == null) {
            throw new NullPointerException();
        }
        switch (type1) {
            case LOBBY:
                break;
            case SERVER:
                break;
            case GAME:
                break;
        }
        return new Message();
    }
}
