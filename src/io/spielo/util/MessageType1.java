package io.spielo.util;

public enum MessageType1 {
    LOBBY((byte) 1), SERVER((byte) 2), GAME((byte) 3);

    private final byte b;

    MessageType1(byte b) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }

}
