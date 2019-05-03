package ru.nchernetsov.RLE;

import org.junit.Test;
import ru.nchernetsov.encoder.Encoder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.Utils.readFileFromResources;


public class RunLengthEncoderTest {

    // Синтетические тесты на заранее известных байтовых массивах

    @Test
    public void encodeSyntheticTest1() {
        byte[] bytes = {1, 2, 3, 4, 5, 3, 3, 3, 6, 7, 8, 1, 1, 1};

        Encoder simpleEncoder = new RunLengthEncoder(RLEType.SIMPLE);
        Encoder advancedEncoder = new RunLengthEncoder(RLEType.ADVANCED);

        byte[] simpleEncode = simpleEncoder.encode(bytes);
        byte[] advancedEncode = advancedEncoder.encode(bytes);

        assertThat(simpleEncode).containsExactly(1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 3, 3, 1, 6, 1, 7, 1, 8, 3, 1);
        assertThat(advancedEncode).containsExactly(-5, 1, 2, 3, 4, 5, 3, 3, -3, 6, 7, 8, 3, 1);

        byte[] simpleDecode = simpleEncoder.decode(simpleEncode);
        byte[] advancedDecode = advancedEncoder.decode(advancedEncode);

        assertThat(simpleDecode).isEqualTo(bytes);
        assertThat(advancedDecode).isEqualTo(bytes);
    }

    @Test
    public void encodeSyntheticTest2() {
        byte[] bytes = {2, 2, 2, 2, 2, 2, 2, 2, 2};

        Encoder simpleEncoder = new RunLengthEncoder(RLEType.SIMPLE);
        Encoder advancedEncoder = new RunLengthEncoder(RLEType.ADVANCED);

        byte[] simpleEncode = simpleEncoder.encode(bytes);
        byte[] advancedEncode = advancedEncoder.encode(bytes);

        assertThat(simpleEncode).containsExactly(9, 2);
        assertThat(advancedEncode).containsExactly(9, 2);

        byte[] simpleDecode = simpleEncoder.decode(simpleEncode);
        byte[] advancedDecode = advancedEncoder.decode(advancedEncode);

        assertThat(simpleDecode).isEqualTo(bytes);
        assertThat(advancedDecode).isEqualTo(bytes);
    }

    @Test
    public void encodeSyntheticTest3() {
        byte[] bytes = {5, 4, 3, 2, 1};

        Encoder simpleEncoder = new RunLengthEncoder(RLEType.SIMPLE);
        Encoder advancedEncoder = new RunLengthEncoder(RLEType.ADVANCED);

        byte[] simpleEncode = simpleEncoder.encode(bytes);
        byte[] advancedEncode = advancedEncoder.encode(bytes);

        assertThat(simpleEncode).containsExactly(1, 5, 1, 4, 1, 3, 1, 2, 1, 1);
        assertThat(advancedEncode).containsExactly(-5, 5, 4, 3, 2, 1);

        byte[] simpleDecode = simpleEncoder.decode(simpleEncode);
        byte[] advancedDecode = advancedEncoder.decode(advancedEncode);

        assertThat(simpleDecode).isEqualTo(bytes);
        assertThat(advancedDecode).isEqualTo(bytes);
    }

    // Кодирование текстового файла

    @Test
    public void encodeTextFileSimpleTest() throws IOException {
        Encoder encoder = new RunLengthEncoder(RLEType.SIMPLE);

        byte[] bytes = readFileFromResources("BinaryStdIn.txt");

        byte[] encodedBytes = encoder.encode(bytes);
        byte[] decodedBytes = encoder.decode(encodedBytes);

        assertThat(decodedBytes).isEqualTo(bytes);

        System.out.printf("Simple encode text file. Original size: %d bytes, " +
                "encoded size: %d bytes, compression: %.1f %%",
            bytes.length, encodedBytes.length, encodedBytes.length * 100.0 / bytes.length);
    }

    @Test
    public void encodeTextFileAdvancedTest() throws IOException {
        Encoder encoder = new RunLengthEncoder(RLEType.ADVANCED);

        byte[] bytes = readFileFromResources("BinaryStdIn.txt");

        byte[] encodedBytes = encoder.encode(bytes);
        byte[] decodedBytes = encoder.decode(encodedBytes);

        assertThat(decodedBytes).isEqualTo(bytes);

        System.out.printf("Simple encode text file. Original size: %d bytes, " +
                "encoded size: %d bytes, compression: %.1f %%",
            bytes.length, encodedBytes.length, encodedBytes.length * 100.0 / bytes.length);
    }
}
