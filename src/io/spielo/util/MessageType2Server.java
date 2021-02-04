package io.spielo.util;

public enum MessageType2Server implements GenericEnumMixin, MessageType2{
    CONNECT((byte) 0), HEARTBEAT((byte) 1), DISCONNECT((byte) 2);

    private final byte b;

    MessageType2Server(byte b) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }

}
