package ru.nchernetsov.encoder;

public interface Encoder {

    byte[] encode(byte[] bytes);

    byte[] decode(byte[] bytes);
}
