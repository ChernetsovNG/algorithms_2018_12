package ru.nchernetsov.GradientDescent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.nchernetsov.GradientDescent.VectorUtils.*;

/**
 * Алгоритм градиентного спуска для метода наименьших квадратов
 */
public class GradientDescent {

    /**
     * Значения MSE по итерациям в процессе обучения
     */
    private final List<Double> errorsByIterations = new ArrayList<>();

    /**
     * Значения модуля градиента по итерациям в процессе обучения
     */
    private final List<Double> gradientByIterations = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Dataset dataset = Dataset.readDatasetFromCsvFile(14, "boston_housing-31272-bf744f.csv");
        GradientDescent gradientDescent = new GradientDescent();
        Double[] vectorW = gradientDescent.calcVectorW(dataset, 0.1, 0.001, 1000);
    }

    /**
     * Вычислить параметры модели
     *
     * @param dataset              набор данных
     * @param learningRate         множитель для градиента
     * @param errorValue           желаемое значение MSE
     * @param iterationsCountLimit ограничение максимального кол-ва итераций по подбору параметров модели
     */
    private Double[] calcVectorW(Dataset dataset, double learningRate, double errorValue, int iterationsCountLimit) {
        int xSize = dataset.getXSize();
        List<Double[]> vectorXList = dataset.getNormalizeX();
        List<Double> vectorY = dataset.getNormalizeY();

        Double[] vectorW = new Double[xSize + 1];  // последнее значение вектора - свободный параметр w0
        for (int i = 0; i <= xSize; i++) {
            vectorW[i] = 0.0;
        }

        double meanSquaredError = getMeanSquaredError(vectorY, calcYPredictedVector(vectorXList, vectorW));
        int iterationCount = 0;

        while (meanSquaredError > errorValue && iterationCount < iterationsCountLimit) {
            vectorW = calcNewVectorW(vectorXList, vectorY, vectorW, learningRate);
            meanSquaredError = getMeanSquaredError(vectorY, calcYPredictedVector(vectorXList, vectorW));
            iterationCount++;
            errorsByIterations.add(meanSquaredError);
        }

        return vectorW;
    }

    private Double[] calcNewVectorW(List<Double[]> vectorXList, List<Double> vectorY, Double[] vectorW, double learningRate) {
        Double[] gradient = calcGradient(vectorXList, vectorY, vectorW);
        gradientByIterations.add(vectorNorm(gradient));
        Double[] gradientMultiplyLearningRate = multiplyVectorOnNumber(gradient, learningRate);
        return substractVectors(vectorW, gradientMultiplyLearningRate);
    }

    /**
     * Вычисление вектора градиента функции ошибки по параметрам модели df/dW
     *
     * @return n-мерный вектор градиента
     */
    private Double[] calcGradient(List<Double[]> vectorXList, List<Double> vectorY, Double[] vectorW) {
        int n = vectorW.length;
        Double[] result = new Double[n];
        for (int j = 0; j < n; j++) {
            Double gradientJ = calcGradientJ(j, vectorXList, vectorY, vectorW);
            result[j] = gradientJ;
        }
        return result;
    }

    /**
     * Частичный градиент - производная функции ошибки относительно одного веса
     * (средний градиент по всем обучающим примерам)
     *
     * @param j номер элемента вектора W, по которому берётся производная
     *          W = [w1, w2, ..., wn, w0]
     */
    private Double calcGradientJ(int j, List<Double[]> vectorXList, List<Double> vectorY, Double[] vectorW) {
        if (j < 0 || j >= vectorW.length) {
            throw new IllegalArgumentException("wrong value j = " + j);
        }
        int n = vectorXList.size();
        double sum = 0.0;
        List<Double> yPredictedVector = calcYPredictedVector(vectorXList, vectorW);
        // цикл по всем обучающим примерам
        for (int i = 0; i < n; i++) {
            Double yI = vectorY.get(i);
            Double yPredictedI = yPredictedVector.get(i);
            Double[] vectorXI = vectorXList.get(i);
            if (j < vectorW.length - 1) {
                Double xIJ = vectorXI[j];
                sum += (yI - yPredictedI) * xIJ;
            } else if (j == (vectorW.length - 1)) {  // для свободного члена w0
                sum += yI - yPredictedI;
            }
        }
        return -2.0 * sum / n;
    }

    /**
     * Для всех входных данных рассчитать вектор предсказаний модели
     *
     * @param vectorXList набор входных данных
     * @param vectorW     набор коэффициентов модели
     */
    private List<Double> calcYPredictedVector(List<Double[]> vectorXList, Double[] vectorW) {
        return vectorXList.stream()
            .map(vectorX -> calcYPredicted(vectorX, vectorW))
            .collect(Collectors.toList());
    }

    /**
     * Для одного элемента набора данных рассчитать предсказание модели
     *
     * @param vectorX набор входных данных
     * @param vectorW набор коэффициентов модели
     */
    // f(x, w) = w1*x1 + w2*x2 + ... + wn*xn + w0
    private Double calcYPredicted(Double[] vectorX, Double[] vectorW) {
        int wSize = vectorW.length;
        Double[] vectorWi = Arrays.copyOf(vectorW, wSize - 1);
        Double w0 = vectorW[wSize - 1];
        return scalarProduct(vectorWi, vectorX) + w0;
    }

    /**
     * Функция ошибок MSE, с усреднением
     *
     * @param yTrue      вектор истинных значений
     * @param yPredicted вектор предсказанных значений
     */
    private double getMeanSquaredError(List<Double> yTrue, List<Double> yPredicted) {
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
