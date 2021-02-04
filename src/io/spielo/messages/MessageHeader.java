package io.spielo.messages;

public interface MessageHeader {
    short getSenderID();

    short getReceiverID();

    short getType1();

    short getType2();

    long getTimestamp();
}
