package io.spielo.messages;

public class LobbyCreateMessage extends Message {
    public LobbyCreateMessage(short senderID, short receiverID, short type1, short type2, long timestamp) {
        super(senderID, receiverID, type1, type2, timestamp);

    }
}
