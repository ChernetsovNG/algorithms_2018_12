package ru.nchernetsov.fibonacci;

import java.math.BigInteger;

import static ru.nchernetsov.fibonacci.SquareMatrix.multiplyMatrices;

public class Power {

    /**
     * Рекурсивный алгоритм быстрого возведения в положительную степень
     *
     * @param x число
     * @param n степень
     * @return x в степени n
     */
    public static BigInteger power(BigInteger x, int n) {
        if (n == 0) {
            return BigInteger.ONE;
        } else if (n == 1) {
            return x;
        } else if (n % 2 == 0) {  // если степень чётная
            return power(x.multiply(x), n / 2);
        } else if (n % 2 == 1) {  // если степень нечётная
            return x.multiply(power(x.multiply(x), (n - 1) / 2));
        } else {
            throw new IllegalArgumentException("power - illegal arguments: x = " + x + ", n = " + n);
        }
    }

    /**
     * Рекурсивный алгоритм быстрого возведения в положительную степень матрицы 2 х 2
     *
     * @param x матрица
     * @param n степень
     * @return x в степени n
     */
    public static SquareMatrix matrixPower(SquareMatrix x, int n) {
        if (n == 0) {
            return SquareMatrix.unitMatrix;
        } else if (n == 1) {
            return x;
        } else if (n % 2 == 0) {  // если степень чётная
            return matrixPower(multiplyMatrices(x, x), n / 2);
        } else if (n % 2 == 1) {  // если степень нечётная
            return multiplyMatrices(x, matrixPower(multiplyMatrices(x, x), (n - 1) / 2));
        } else {
            throw new IllegalArgumentException("power - illegal arguments: x = " + x + ", n = " + n);
        }
    }
}
