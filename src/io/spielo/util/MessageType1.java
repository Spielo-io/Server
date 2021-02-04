package io.spielo.util;

public enum MessageType1 implements GenericEnumMixin{
    LOBBY((byte) 0), SERVER((byte) 1), GAME((byte) 2);

    private final byte b;

    MessageType1(byte b) {
        this.b = b;
    }

    @Override
    public byte getByte() {
        return b;
    }

}
