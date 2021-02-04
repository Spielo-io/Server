package io.spielo.util;

public enum MessageType2Game implements GenericEnumMixin, MessageType2{
    TEST((byte) 0);
    private final byte b;

    MessageType2Game(byte b) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }
}
