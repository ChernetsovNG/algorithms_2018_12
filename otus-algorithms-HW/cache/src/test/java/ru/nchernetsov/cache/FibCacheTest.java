package ru.nchernetsov.cache;

import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class FibCacheTest {

    @Test
    public void fibTest1() {
        FibCache fibCache = new FibCache();
        BigInteger fib = fibCache.fib(0);
        assertThat(fib).isEqualTo(BigInteger.ZERO);
    }

    @Test
    public void fibTest2() {
        FibCache fibCache = new FibCache();
        BigInteger fib = fibCache.fib(1);
        assertThat(fib).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void fibTest3() {
        FibCache fibCache = new FibCache();
        BigInteger fib = fibCache.fib(10);
        assertThat(fib).isEqualTo(new BigInteger("55"));
    }

    @Test
    public void fibTest4() {
        FibCache fibCache = new FibCache();
        BigInteger fib = fibCache.fib(50);
        assertThat(fib).isEqualTo(new BigInteger("12586269025"));
    }

    @Test
    public void fibTest5() {
        FibCache fibCache = new FibCache();
        BigInteger fib = fibCache.fib(200);
        assertThat(fib).isEqualTo(new BigInteger("280571172992510140037611932413038677189525"));
    }
}
