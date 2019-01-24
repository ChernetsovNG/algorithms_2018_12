package ru.nchernetsov.insertion;

public class InsertionSort {

    public static void insertionSort(int[] array) {
        int nElements = array.length;

        int in, out;

        for (out = 1; out < nElements; out++) {  // out - разделительный маркер
            int temp = array[out];  // скопировать помеченный элемент
            in = out;                // начать перемещение с out
            while (in > 0 && array[in - 1] >= temp) {  // пока не найден меньший элемент
                array[in] = array[in - 1];  // сдвинуть элемент вправо
                --in;                       // перейти на одну позицию влево
            }
            array[in] = temp;  // вставить помеченный элемент
        }
    }
}
