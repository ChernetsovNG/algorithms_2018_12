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
    IArray2(OList<BArray<T>> list) {
        this.list = list;
    }

    /**
     * Получить элемент по индексу
     *
     * @param index индекс
     * @return элемент
     */
    T get(int index) {
        // Идём по списку и проверяем заполненность массивов
        int prevRowsElementsCount = 0;  // количество элементов в предыдущих N-1 строках
        ListItem<BArray<T>> head = list.head();
        while (head != null) {
            BArray<T> item = head.getItem();
            int occupancy = item.size();
            if (index - prevRowsElementsCount < occupancy) {
                return item.get(index - prevRowsElementsCount);
            }
            head = head.getNext();
            prevRowsElementsCount += occupancy;
        }
        throw new NoSuchElementException();
    }
}
