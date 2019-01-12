package ru.nchernetsov.fibonacci;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.fibonacci.Fibonacci.getFibonacciByMatrixCalc;

/**
 * Для проверки используется последовательность Фибоначчи: https://oeis.org/A000045/b000045.txt
 */
public class FibonacciTest {

    @Test
    public void getFibonacciByMatrixCalcTest1() {

        BigInteger result = getFibonacciByMatrixCalc(0);

        assertThat(result).isEqualTo(BigInteger.ZERO);
    }

    @Test
    public void getFibonacciByMatrixCalcTest2() {

        BigInteger result = getFibonacciByMatrixCalc(1);

        assertThat(result).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void getFibonacciByMatrixCalcTest3() {

        BigInteger result = getFibonacciByMatrixCalc(10);

        assertThat(result).isEqualTo(BigInteger.valueOf(55L));
    }

    @Test
    public void getFibonacciByMatrixCalcTest4() {

        BigInteger result = getFibonacciByMatrixCalc(30);

        assertThat(result).isEqualTo(BigInteger.valueOf(832040L));
    }

    @Test
    public void getFibonacciByMatrixCalcTest5() {

        BigInteger result = getFibonacciByMatrixCalc(85);

        assertThat(result).isEqualTo(new BigInteger("259695496911122585"));
    }

    @Test
    public void getFibonacciByMatrixCalcTest6() {

        BigInteger result = getFibonacciByMatrixCalc(200);

        assertThat(result).isEqualTo(new BigInteger("280571172992510140037611932413038677189525"));
    }

    @Test
    public void getFibonacciByMatrixCalcTest7() {

        BigInteger result = getFibonacciByMatrixCalc(500);

        assertThat(result).isEqualTo(new BigInteger("139423224561697880139724382870407283950070256587697307264108962948325571622863290691557658876222521294125"));
    }
}





