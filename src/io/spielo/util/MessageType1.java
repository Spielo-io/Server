package io.spielo.util;

public enum MessageType1 {
    LOBBY((byte) 0), SERVER((byte) 1), GAME((byte) 2);

    private final byte b;

    MessageType1(byte b) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }

}
