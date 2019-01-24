package ru.nchernetsov.eratosthenes;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.eratosthenes.EratosthenesSieve.getPrimeNumbersBitwise;
import static ru.nchernetsov.eratosthenes.EratosthenesSieve.getPrimeNumbersSimple;

public class EratosthenesSieveTest {

    @Test
    public void getPrimeNumbersSimpleTest1() {

        List<Integer> primeNumbers = getPrimeNumbersSimple(30);

        assertThat(primeNumbers).containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    }

    @Test
    public void getPrimeNumbersSimpleTest2() {

        List<Integer> primeNumbers = getPrimeNumbersSimple(150);

        assertThat(primeNumbers).containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149);
    }

    @Test
    public void getPrimeNumbersBitwiseTest1() {

        List<Integer> primeNumbers = getPrimeNumbersBitwise(30);

        assertThat(primeNumbers).containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    }

    @Test
    public void getPrimeNumbersBitwiseTest2() {

        List<Integer> primeNumbers = getPrimeNumbersBitwise(150);

        assertThat(primeNumbers).containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149);
    }
}
