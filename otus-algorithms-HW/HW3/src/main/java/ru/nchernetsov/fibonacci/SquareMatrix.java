package ru.nchernetsov.fibonacci;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Квадратная матрица 2 х 2
 */
public class SquareMatrix {

    private final BigInteger a11;

    private final BigInteger a12;

    private final BigInteger a21;

    private final BigInteger a22;

    /**
     * Умножение квадратных матриц размера 2 x 2
     *
     * @return произведение матриц
     */
    public static SquareMatrix multiplyMatrices(SquareMatrix matrix1, SquareMatrix matrix2) {
        BigInteger a11 = matrix1.getA11();
        BigInteger a12 = matrix1.getA12();
        BigInteger a21 = matrix1.getA21();
        BigInteger a22 = matrix1.getA22();

        BigInteger b11 = matrix2.getA11();
        BigInteger b12 = matrix2.getA12();
        BigInteger b21 = matrix2.getA21();
        BigInteger b22 = matrix2.getA22();

        BigInteger resultA11 = (a11.multiply(b11)).add(a12.multiply(b21));
        BigInteger resultA12 = (a11.multiply(b12)).add(a12.multiply(b22));
        BigInteger resultA21 = (a21.multiply(b11)).add(a22.multiply(b21));
        BigInteger resultA22 = (a21.multiply(b12)).add(a22.multiply(b22));

        return new SquareMatrix(resultA11, resultA12, resultA21, resultA22);
    }

    /**
     * Единичная матрица ([1, 0], [0, 1])
     */
    public static final SquareMatrix unitMatrix = new SquareMatrix(BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE);

    /**
     * Матрица Q из матричного алгоритма вычисления чисел Фибоначчи (которая возводится в степень)
     */
    public static final SquareMatrix Q = new SquareMatrix(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);

    public SquareMatrix(BigInteger a11, BigInteger a12, BigInteger a21, BigInteger a22) {
        this.a11 = a11;
        this.a12 = a12;
        this.a21 = a21;
        this.a22 = a22;
    }

    public BigInteger getA11() {
        return a11;
    }

    public BigInteger getA12() {
        return a12;
    }

    public BigInteger getA21() {
        return a21;
    }

    public BigInteger getA22() {
        return a22;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquareMatrix that = (SquareMatrix) o;
        return Objects.equals(a11, that.a11) &&
                Objects.equals(a12, that.a12) &&
                Objects.equals(a21, that.a21) &&
                Objects.equals(a22, that.a22);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a11, a12, a21, a22);
    }
}
