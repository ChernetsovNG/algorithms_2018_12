package ru.nchernetsov;

import java.util.NoSuchElementException;

/**
 * Динамический массив типа "Список массивов с неполным заполнением"
 *
 * @param <T> тип элементов
 */
public class IArray2<T> {

    /**
     * Список массивов
     */
    private OList<BArray<T>> list;

    public IArray2(int capacity) {
        list = new OList<>();
        list.add(new ListItem<>(new BArray<>(capacity, 0)));
    }

    // for testing
    public IArray2(OList<BArray<T>> list) {
        this.list = list;
    }

    /**
     * Получить элемент по индексу
     *
     * @param index индекс
     * @return элемент
     */
    public T get(int index) {
        // Идём по списку и проверяем заполненность массивов
        int prevRowCount = 0;
        int elementsCount = 0;
        ListItem<BArray<T>> head = list.head();
        while (head != null) {
            BArray<T> item = head.getItem();
            int occupancy = item.occupancy();
            elementsCount += occupancy;
            if (index < elementsCount) {
                return item.get(index - prevRowCount);
            }
            head = head.getNext();
            prevRowCount = occupancy;
        }
        throw new NoSuchElementException();
    }
}
