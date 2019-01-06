package ru.nchernetsov;

/**
 * Динамический массив, в котором размер увеличивается блоками
 *
 * @param <T> тип элементов
 */
public class BArray<T> {

    /**
     * Размер блока, на который увеличивается размер массива
     */
    private final int blockSize;

    /**
     * Массив
     */
    private T[] arr;

    private int size;

    public BArray() {
        this(10, 5);
    }

    @SuppressWarnings("unchecked")
    BArray(int initialSize, int blockSize) {
        this.arr = (T[]) new Object[initialSize];
        this.blockSize = blockSize;
        this.size = 0;
    }

    T get(int index) {
        return arr[index];
    }

    void add(int index, T element) {
        if (arr == null) {
            relocate(blockSize, index);
        }
        if (index >= arr.length) {
            relocate(arr.length + blockSize, index);
        }
        arr[index] = element;
        size++;
    }

    public void set(int index, T element) {
        arr[index] = element;
    }

    public void remove(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // сдвигаем все элементы справа на место удаляемого
        for (int i = index; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        size--;
    }

    int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void relocate(int newSize, int index) {
        Object[] tmp = new Object[newSize];
        if (arr != null) {
            for (int i = 0; i < arr.length; i++)
                if (i < index)
                    tmp[i] = arr[i];
                else
                    tmp[i + 1] = arr[i];
        }
        arr = (T[]) tmp;
    }
}
