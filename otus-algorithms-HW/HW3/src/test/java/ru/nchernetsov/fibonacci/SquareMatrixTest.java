package ru.nchernetsov.fibonacci;

import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.fibonacci.SquareMatrix.multiplyMatrices;

public class SquareMatrixTest {

    @Test
    public void multiplyMatricesTest1() {

        SquareMatrix matrix1 = new SquareMatrix(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
        SquareMatrix matrix2 = new SquareMatrix(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);

        SquareMatrix result = multiplyMatrices(matrix1, matrix2);

        assertThat(result.getA11()).isEqualTo(BigInteger.valueOf(2));
        assertThat(result.getA12()).isEqualTo(BigInteger.ONE);
        assertThat(result.getA21()).isEqualTo(BigInteger.ONE);
        assertThat(result.getA22()).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void multiplyMatricesTest2() {
        /*
        (21 11  x (5 8  = (182 234
         17 -3)    7 6)     64 118)
         */

        SquareMatrix matrix1 = new SquareMatrix(BigInteger.valueOf(21), BigInteger.valueOf(11), BigInteger.valueOf(17), BigInteger.valueOf(-3));
        SquareMatrix matrix2 = new SquareMatrix(BigInteger.valueOf(5), BigInteger.valueOf(8), BigInteger.valueOf(7), BigInteger.valueOf(6));

        SquareMatrix result = multiplyMatrices(matrix1, matrix2);

        assertThat(result.getA11()).isEqualTo(BigInteger.valueOf(182));
        assertThat(result.getA12()).isEqualTo(BigInteger.valueOf(234));
        assertThat(result.getA21()).isEqualTo(BigInteger.valueOf(64));
        assertThat(result.getA22()).isEqualTo(BigInteger.valueOf(118));
    }
}
