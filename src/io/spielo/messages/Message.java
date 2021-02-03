package io.spielo.messages;

public class Message implements MessageHeader {
    private short senderID;
    private short receiverID;
    private short type1;
    private short type2;
    private long timestamp;

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
        return new Message();
    }
}
