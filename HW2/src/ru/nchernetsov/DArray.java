package ru.nchernetsov;

/**
 * Динамический массив
 *
 * @param <T> тип элементов
 */
public class DArray<T> {

    /**
     * Размер блока, на который увеличивается размер массива
     */
    private final int deltaSize;

    /**
     * Массив
     */
    private T[] arr;

    @SuppressWarnings("unchecked")
    public DArray(int initialSize, int deltaSize) {
        this.arr = (T[]) new Object[initialSize];
        this.deltaSize = deltaSize;
    }

    T get(int index) {
        return arr[index];
    }

    @SuppressWarnings("unchecked")
    private void relocate(int newSize, int index) {
        Object[] tmp = new Object[newSize];
        if (arr != null)
            for (int i = 0; i < arr.length; i++)
                if (i < index)
                    tmp[i] = arr[i];
                else
                    tmp[i + 1] = arr[i];
        arr = (T[]) tmp;
    }

    public void add(int index, T element) {
        if (arr == null || arr.length <= index)
            relocate(index + deltaSize, index);
        arr[index] = element;
    }

    public void set(int index, T element) {
        arr[index] = element;
    }

    public int size() {
        return arr.length;
    }
}
