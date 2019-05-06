package ru.nchernetsov.RLE;

import ru.nchernetsov.encoder.Encoder;

import java.io.ByteArrayOutputStream;

/**
 * Простой алгоритм кодирования по длинам серий
 */
public class SimpleRunLengthEncoder implements Encoder {

    @Override
    public byte[] encode(byte[] bytes) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte lastByte = bytes[0];
        int matchCount = 1;
        for (int i = 1; i < bytes.length; i++) {
            byte thisByte = bytes[i];
            if (lastByte == thisByte && matchCount < Byte.MAX_VALUE) {
                matchCount++;
            } else {
                result.write((byte) matchCount);
                result.write(lastByte);
                matchCount = 1;
                lastByte = thisByte;
            }
        }
        result.write((byte) matchCount);
        result.write(lastByte);
        return result.toByteArray();
    }

    @Override
    public byte[] decode(byte[] bytes) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        for (int i = 0; i < bytes.length - 1; i += 2) {
            byte matchCount = bytes[i];    // количество повторений
            byte lastByte = bytes[i + 1];  // повторяющийся байт
            for (int j = 0; j < matchCount; j++) {
                result.write(lastByte);
            }
        }
        return result.toByteArray();
    }
}
