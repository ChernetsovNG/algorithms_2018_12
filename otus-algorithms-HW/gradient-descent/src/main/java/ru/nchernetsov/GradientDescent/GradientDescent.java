package ru.nchernetsov.GradientDescent;

import java.io.IOException;
import java.util.List;

/**
 * Алгоритм градиентного спуска для метода наименьших квадратов
 */
public class GradientDescent {

    public static void main(String[] args) throws IOException {
        GradientDescent gradientDescent = new GradientDescent();
        Dataset dataset = Dataset.readDatasetFromCsvFile(14, "boston_housing-31272-bf744f.csv");
    }

    /**
     * Функция ошибок MSE, с усреднением
     *
     * @param yTrue      вектор истинных значений
     * @param yPredicted вектор предсказанных значений
     */
    public static double getMeanSquaredError(List<Double> yTrue, List<Double> yPredicted) {
        int n = yTrue.size();
        if (yPredicted.size() != n) {
            throw new IllegalArgumentException("Sizes of vectors yTrue and yPredicted are not equals");
        }
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            double yTrueI = yTrue.get(i);
            double yPredictedI = yPredicted.get(i);
            double deltaY = yTrueI - yPredictedI;
            sum += deltaY * deltaY;
        }

        return sum / n;
    }
}
