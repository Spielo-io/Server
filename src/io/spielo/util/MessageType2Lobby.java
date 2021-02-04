package io.spielo.util;

public enum MessageType2Lobby implements GenericEnumMixin, MessageType2{
    CREATE((byte) 0), SETTINGS((byte) 1), JOIN((byte) 2);

    private final byte b;

    MessageType2Lobby(byte b) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }

}
