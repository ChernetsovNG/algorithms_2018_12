package ru.nchernetsov;

/**
 * Очередь с приоритетом на основе массива списков
 *
 * @param <T>
 */
public class PQueue<T> {

    /**
     * Индексы массива соответствуют приоритетам, а в каждой
     * ячейке хранится список элементов заданного приоритета
     * в порядке их вставки в очередь
     */
    private BArray<OList<T>> priority;

    public PQueue() {
        this.priority = new BArray<>(10, 5);
    }

    /**
     * Поместить элемент в очередь
     *
     * @param priority приоритет элемента
     * @param item     элемент
     */
    public void enqueue(int priority, T item) {
        OList<T> prioritiesList = this.priority.get(priority);
        if (prioritiesList == null) {
            OList<T> list = new OList<>();
            list.add(new ListItem<>(item));
            this.priority.add(priority, list);
        } else {
            prioritiesList.add(new ListItem<>(item));
        }
    }

    /**
     * Извлечь элемент из очереди
     *
     * @return элемент
     */
    public T dequeue() {
        // возвращаем голову из списка с максимальным приоритетом
        OList<T> maxPriorityList = getMaxPriorityList();
        if (maxPriorityList == null) {
            return null;
        }
        // возвращаем голову списка и сдвигаем указатель
        return maxPriorityList.remove();
    }

    private OList<T> getMaxPriorityList() {
        int prioritySize = priority.size();
        for (int i = prioritySize - 1; i > 0; i--) {
            OList<T> priorityList = priority.get(i);
            if (priorityList != null) {
                // если список пуст, то удаляем его из очереди, т.к. из него были извлечены все элементы
                if (priorityList.isEmpty()) {
                    priority.add(i, null);
                } else {
                    return priorityList;
                }
            }
        }
        return null;
    }
}
