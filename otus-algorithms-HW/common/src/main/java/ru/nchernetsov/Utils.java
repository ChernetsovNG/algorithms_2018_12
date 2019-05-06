package ru.nchernetsov;

import java.io.*;

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
}
