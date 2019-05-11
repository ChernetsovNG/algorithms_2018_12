package ru.nchernetsov;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Utils {

    public static <T extends Comparable<T>> T max(T... elements) {
        if (elements.length == 1) {
            return elements[0];
        }
        T max = elements[0];
        for (int i = 1; i < elements.length; i++) {
            if (elements[i].compareTo(max) > 0) {
                max = elements[i];
            }
        }
        return max;
    }

    public static <T extends Comparable<T>> T min(T... elements) {
        if (elements.length == 1) {
            return elements[0];
        }
        T min = elements[0];
        for (int i = 1; i < elements.length; i++) {
            if (elements[i].compareTo(min) < 0) {
                min = elements[i];
            }
        }
        return min;
    }

    /**
     * Копия заданного объекта посредством сериализации-десереализации
     * (в частности, глубокое копирование иерархических структур)
     *
     * @param object копируемый объект
     * @param <T>    тип копируемого объекта
     * @return копия
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T object) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            return (T) objInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readFileFromResources(String fileName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            return convertStreamToByteArray(Objects.requireNonNull(inputStream));
        }
    }

    private static byte[] convertStreamToByteArray(InputStream stream) throws IOException {
        int bufferSize = 10240;  // 10 kB
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int line = 0;
        // read bytes from stream, and store them in buffer
        while ((line = stream.read(buffer)) != -1) {
            // Writes bytes from byte array (buffer) into output stream.
            outputStream.write(buffer, 0, line);
        }
        stream.close();
        outputStream.flush();
        outputStream.close();
        return outputStream.toByteArray();
    }

    /**
     * Вспомогательная функция для разделения массива на массивы заданного размера
     *
     * @param arrayToSplit массив для разделения
     * @param chunkSize    размер одной части
     * @return массив частей после разбиения
     */
    public static byte[][] splitArray(byte[] arrayToSplit, int chunkSize) {
        if (chunkSize <= 0) {
            return null;
        }
        int rest = arrayToSplit.length % chunkSize;
        int chunks = arrayToSplit.length / chunkSize + (rest > 0 ? 1 : 0);
        byte[][] arrays = new byte[chunks][];
        for (int i = 0; i < (rest > 0 ? chunks - 1 : chunks); i++) {
            arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
        }
        if (rest > 0) {
            arrays[chunks - 1] = Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
        }
        return arrays;
    }
}
