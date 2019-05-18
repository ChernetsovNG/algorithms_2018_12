package ru.nchernetsov.GradientDescent;

import java.io.IOException;

/**
 * Алгоритм градиентного спуска для метода наименьших квадратов
 */
public class GradientDescent {

    /*
    Алгоритм должен принимать на вход массив значений float "количество примеров" x "количество признаков" и
    вектор float размера "количество примеров", начальный набор весов (из нормального распределения N(0, 1)$, learning rate (размер шага)
     */


    /*
    Написать функцию для нормализации данных x_i_norm = (x_i - mu)/sigma
     */

    public static void main(String[] args) throws IOException {
        GradientDescent gradientDescent = new GradientDescent();
        Dataset dataset = Dataset.readDatasetFromCsvFile("boston_housing-31272-bf744f.csv");
    }
}
