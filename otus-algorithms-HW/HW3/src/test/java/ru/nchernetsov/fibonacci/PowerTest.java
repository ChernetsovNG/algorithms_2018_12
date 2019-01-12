package ru.nchernetsov.fibonacci;

import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.fibonacci.Power.matrixPower;
import static ru.nchernetsov.fibonacci.Power.power;

public class PowerTest {

    @Test
    public void powerTest1() {
        BigInteger x = new BigInteger("2");
        int n = 10;

        BigInteger result = power(x, n);

        assertThat(result).isEqualTo(BigInteger.valueOf(1024L));
    }

    @Test
    public void powerTest2() {
        BigInteger x = new BigInteger("35");
        int n = 0;

        BigInteger result = power(x, n);

        assertThat(result).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void powerTest3() {
        BigInteger x = new BigInteger("35");
        int n = 1;

        BigInteger result = power(x, n);

        assertThat(result).isEqualTo(BigInteger.valueOf(35L));
    }

    @Test
    public void powerTest4() {
        BigInteger x = new BigInteger("35");
        int n = 11;

        BigInteger result = power(x, n);

        assertThat(result).isEqualTo(new BigInteger("96549157373046875"));
    }

    @Test
    public void matrixPowerTest1() {

        SquareMatrix matrix = SquareMatrix.Q;

        SquareMatrix result = matrixPower(matrix, 0);

        assertThat(result).isEqualTo(SquareMatrix.unitMatrix);
    }

    @Test
    public void matrixPowerTest2() {

        SquareMatrix matrix = SquareMatrix.Q;

        SquareMatrix result = matrixPower(matrix, 1);

        assertThat(result).isEqualTo(matrix);
    }

    @Test
    public void matrixPowerTest3() {

        SquareMatrix matrix = SquareMatrix.Q;

        SquareMatrix result = matrixPower(matrix, 5);

        assertThat(result.getA11()).isEqualTo(BigInteger.valueOf(8));
        assertThat(result.getA12()).isEqualTo(BigInteger.valueOf(5));
        assertThat(result.getA21()).isEqualTo(BigInteger.valueOf(5));
        assertThat(result.getA22()).isEqualTo(BigInteger.valueOf(3));
    }
}
