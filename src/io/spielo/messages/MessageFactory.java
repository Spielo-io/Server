package io.spielo.messages;

public class MessageFactory {
    public Message getMessage(byte[] bytes) {
        byte type1 = (byte) (bytes[2] >>> 4);
        byte type2 = (byte) (bytes[2] & 15);
        return new Message();
    }
}
