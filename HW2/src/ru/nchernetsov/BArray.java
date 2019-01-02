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

    public BArray() {
        this(10, 5);
    }

    @SuppressWarnings("unchecked")
    BArray(int initialSize, int blockSize) {
        this.arr = (T[]) new Object[initialSize];
        this.blockSize = blockSize;
    }

    T get(int index) {
        return arr[index];
    }

    void add(int index, T element) {
        if (arr == null || arr.length <= index)
            relocate(index + blockSize, index);
        arr[index] = element;
    }

    public void set(int index, T element) {
        arr[index] = element;
    }

    public void remove(int index) {
        if (index >= arr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // сдвигаем все элементы справа на место удаляемого
        for (int i = index; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
    }

    int size() {
        return arr.length;
    }

    /**
     * Получить реальную "заполненность" массива
     *
     * @return сколько всего занято элементов в массиве
     */
    int occupancy() {
        int result = 0;
        for (T t : arr) {
            if (t != null) {
                result++;
            }
        }
        return result;
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
}
