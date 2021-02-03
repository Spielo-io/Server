package io.spielo.messages;

public class Message implements MessageHeader {
    private short senderID;
    private short receiverID;
    private short type1;
    private short type2;
    private long timestamp;

    public Message(short senderID, short receiverID, short type1, short type2, long timestamp) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.type1 = type1;
        this.type2 = type2;
        this.timestamp = timestamp;
    }

    @Override
    public short getSenderID() {
        return senderID;
    }

    @Override
    public short getReceiverID() {
        return receiverID;
    }

    @Override
    public short getType1() {
        return type1;
    }

    @Override
    public short getType2() {
        return type2;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public static Message createMessage(byte[] bytes) {
        short senderID = byteArrayToShort(bytes, 0);
        short receiverID = byteArrayToShort(bytes, 2);
        short type1 = byteArrayToShort(bytes, 4);
        short type2 = byteArrayToShort(bytes, 6);
        short timestamp = 0;


        return new Message(senderID, receiverID, type1, type2, timestamp);
    }

    private static short byteArrayToShort(byte[] buffer, int offset) {
        return (short) ((buffer[0 + offset] & 0xFF) << 8 |
                (buffer[1 + offset] & 0xFF) << 0);
    }
}
