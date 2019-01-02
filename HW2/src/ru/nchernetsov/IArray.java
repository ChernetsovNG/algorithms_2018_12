package ru.nchernetsov;

/**
 * Динамический массив типа "Массив массивов с полным заполнением"
 *
 * @param <T> тип элементов
 */
public class IArray<T> {

    /**
     * Массив массивов
     */
    private BArray<BArray<T>> arr;

    /**
     * Размер блока
     */
    private int capacity;

    public IArray(int capacity) {
        this.capacity = capacity;
        arr = new BArray<>(capacity, capacity);
        arr.add(0, new BArray<>(capacity, 0));
    }

    public T get(int index) {
        int index1 = index / capacity;
        int index2 = index % capacity;
        return arr.get(index1).get(index2);
    }

    void add(int index, T element) {
        if (arr.size() * capacity <= index)
            relocate();
        int index1 = index / capacity;
        int index2 = index % capacity;
        arr.get(index1).add(index2, element);
    }

    private void relocate() {
        // добавляем к нашему массиву ещё одну строку
        arr.add(arr.size(), new BArray<>(capacity, 0));
    }
}
