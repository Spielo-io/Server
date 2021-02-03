package io.spielo.messages;

public interface MessageHeader {
    public short getSenderID();

    public short getReceiverID();

    public short getType1();

    public short getType2();

    public long getTimestamp();
}
