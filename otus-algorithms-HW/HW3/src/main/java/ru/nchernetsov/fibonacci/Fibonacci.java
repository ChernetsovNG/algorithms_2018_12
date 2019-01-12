package ru.nchernetsov.fibonacci;

import java.math.BigInteger;

import static ru.nchernetsov.fibonacci.Power.matrixPower;

public class Fibonacci {

    /**
     * Матричный алгоритм вычисления n-го числа Фибоначчи
     * Можно показать, что F_n = Q^(n - 1)(1, 1), где F_n - n-е число Фибоначчи,
     * Q - матрица ([1, 1], [1, 0])
     *
     * @param n порядковый номер вычисляемого числа
     * @return n-e число Фибоначчи
     */
    public static BigInteger getFibonacciByMatrixCalc(int n) {
        if (n == 0) {
            return BigInteger.ZERO;
        } else if (n == 1) {
            return BigInteger.ONE;
        } else if (n > 1) {
            SquareMatrix matrixPower = matrixPower(SquareMatrix.Q, n - 1);
            return matrixPower.getA11();
        } else {
            throw new IllegalArgumentException("getFibonacciByMatrixCalc. n = " + n);
        }
    }
}
