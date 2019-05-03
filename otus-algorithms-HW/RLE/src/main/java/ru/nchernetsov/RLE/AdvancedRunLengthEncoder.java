package ru.nchernetsov.RLE;

import ru.nchernetsov.encoder.Encoder;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Objects;

import static ru.nchernetsov.Utils.splitArray;

/**
 * Улучшенный алгоритм кодирования по длинам серий
 */
public class AdvancedRunLengthEncoder implements Encoder {

    private State state;

    @Override
    public byte[] encode(byte[] bytes) {
        ByteArrayOutputStream lastSeries = new ByteArrayOutputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        byte lastByte = bytes[0];
        lastSeries.write(lastByte);

        if (bytes.length < 2) {
            result.write(-1);
            result.write(bytes[0]);
            return new byte[]{-1, bytes[0]};
        }

        // определяем начальное состояние, сравнивая 2 первых байта
        byte secondByte = bytes[1];
        this.state = secondByte == lastByte ? State.REPEAT : State.NON_REPEAT;

        for (int i = 1; i < bytes.length; i++) {
            byte thisByte = bytes[i];
            if (thisByte == lastByte) {
                thisByteEqualsPrevious(lastSeries, result, thisByte);
            } else {
                thisByteNotEqualsPrevious(lastSeries, result, thisByte);
            }
            lastByte = thisByte;
        }

        // дописываем то, что осталось
        byte[] remainingBytes = lastSeries.toByteArray();
        if (remainingBytes.length > 0) {
            switch (state) {
                case NON_REPEAT:
                    writeNonRepeatSeries(remainingBytes, result);
                    break;
                case REPEAT:
                    writeRepeatSeries(remainingBytes, result);
                    break;
            }
        }

        return result.toByteArray();
    }

    @Override
    public byte[] decode(byte[] bytes) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        for (int i = 0; i < bytes.length - 1; ) {
            byte thisByte = bytes[i];
            if (thisByte < 0) {  // неповторяющаяся серия
                int len = thisByte * -1;
                for (int j = 0; j < len; j++) {
                    byte nonRepeatSeriesByte = bytes[i + 1 + j];
                    result.write(nonRepeatSeriesByte);
                }
                i += (len + 1);
            } else {  // повторяющаяся серия
                int len = thisByte;            // количество повторений
                byte lastByte = bytes[i + 1];  // повторяющийся байт
                for (int j = 0; j < len; j++) {
                    result.write(lastByte);
                }
                i += 2;
            }
        }
        return result.toByteArray();
    }

    // PRIVATE section

    private void thisByteEqualsPrevious(ByteArrayOutputStream lastSeries, ByteArrayOutputStream result, byte thisByte) {
        if (state.equals(State.NON_REPEAT)) {  // началась повторяющаяся серия после неповторяющейся
            byte[] lastBytes = lastSeries.toByteArray();
            // все байты, кроме последнего, образуют неповторяющуюся серию
            byte[] nonRepeatSeries = Arrays.copyOf(lastBytes, lastBytes.length - 1);
            // последний байт образует начало повторяющейся серии
            byte firstByteOfRepeatSeries = lastBytes[lastBytes.length - 1];
            if (nonRepeatSeries.length > 0) {
                writeNonRepeatSeries(nonRepeatSeries, result);
            }
            lastSeries.reset();
            lastSeries.write(firstByteOfRepeatSeries);
            lastSeries.write(thisByte);
            state = State.REPEAT;
        } else if (state.equals(State.REPEAT)) {  // продолжается повторяющаяся серия
            lastSeries.write(thisByte);
        }
    }

    private void thisByteNotEqualsPrevious(ByteArrayOutputStream lastSeries, ByteArrayOutputStream result, byte thisByte) {
        if (state.equals(State.NON_REPEAT)) {  // продолжается неповторяющаяся серия
            lastSeries.write(thisByte);
        } else if (state.equals(State.REPEAT)) {  // закончилась повторяющаяся серия
            byte[] repeatSeries = lastSeries.toByteArray();
            if (repeatSeries.length > 0) {
                writeRepeatSeries(repeatSeries, result);
            }
            lastSeries.reset();
            lastSeries.write(thisByte);
            state = State.NON_REPEAT;
        }
    }

    // сохраняем неповторяющуюся серию
    private void writeNonRepeatSeries(byte[] nonRepeatSeries, ByteArrayOutputStream result) {
        int nonRepeatSeriesLen = nonRepeatSeries.length;
        // если неповторяющаяся серия длинная, то разбиваем её на последовательность коротких серий
        if (nonRepeatSeriesLen > Byte.MAX_VALUE) {
            byte[][] nonRepeatSeriesSplit = splitArray(nonRepeatSeries, Byte.MAX_VALUE);
            for (byte[] chunk : Objects.requireNonNull(nonRepeatSeriesSplit)) {
                result.write(-1 * chunk.length);
                result.write(chunk, 0, chunk.length);
            }
        } else {
            result.write(-1 * nonRepeatSeriesLen);
            result.write(nonRepeatSeries, 0, nonRepeatSeries.length);
        }
    }

    // сохраняем повторяющуюся серию
    private void writeRepeatSeries(byte[] repeatSeries, ByteArrayOutputStream result) {
        int repeatSeriesLen = repeatSeries.length;
        // если повторяющаяся серия длинная, то разбиваем её на последовательность коротких серий
        if (repeatSeriesLen > Byte.MAX_VALUE) {
            byte[][] repeatSeriesSplit = splitArray(repeatSeries, Byte.MAX_VALUE);
            for (byte[] chunk : Objects.requireNonNull(repeatSeriesSplit)) {
                result.write(chunk.length);
                result.write(chunk[0]);  // записываем 1-й байт повторяющейся серии
            }
        } else {
            result.write(repeatSeriesLen);
            result.write(repeatSeries[0]);
        }
    }

    private enum State {

        /**
         * Идём по повторяющейся серии
         */
        REPEAT,

        /**
         * Идём по неповторяющейся серии
         */
        NON_REPEAT
    }
}
