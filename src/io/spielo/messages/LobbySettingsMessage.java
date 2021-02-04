package io.spielo.messages;

public class LobbySettingsMessage extends Message {
    private final short isPubl, game, timer, bestOf;

    public LobbySettingsMessage(short senderID, short receiverID, short type1, short type2, long timestamp, short isPubl, short game, short timer, short bestOf) {
        super(senderID, receiverID, type1, type2, timestamp);
        this.isPubl = isPubl;
        this.game = game;
        this.timer = timer;
        this.bestOf = bestOf;
    }

    public static LobbySettingsMessage createMessage(byte[] bytes) {
        short isPubl = bytes[14];
        short game = bytes[15];
        short timer = bytes[16];
        short bestOf = bytes[17];
        short senderID = byteArrayToShort(bytes, 0);
        short receiverID = byteArrayToShort(bytes, 2);
        short type1 = bytes[4];
        short type2 = bytes[5];
        long timestamp = byteArrayToLong(bytes, 6);
        return new LobbySettingsMessage(senderID, receiverID, type1, type2, timestamp, isPubl, game, timer, bestOf);
    }

    public short getIsPubl() {
        return isPubl;
    }

    public short getGame() {
        return game;
    }

    public short getTimer() {
        return timer;
    }

    public short getBestOf() {
        return bestOf;
    }
}
