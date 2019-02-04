package ru.nchernetsov.shell;

public class ShellSort {

    public static void shellSort(int[] array) {
        int nElements = array.length;

        int in, out;
        int temp;
        int h = 1;  // вычисление исходного значения h
        while (h < nElements / 3) {
            h = h * 3 + 1;  // 1, 4, 13, 40, 121, ...
        }

        while (h > 0) {  // последовательное уменьшение h до 1
            for (out = h; out < nElements; out++) {
                temp = array[out];
                in = out;
                while (in > h - 1 && array[in - h] >= temp) {
                    array[in] = array[in - h];
                    in -= h;
                }
                array[in] = temp;
            }
            h = (h - 1) / 3;  // уменьшение h
        }
    }
}
