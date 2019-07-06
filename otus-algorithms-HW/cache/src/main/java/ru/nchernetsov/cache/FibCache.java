package ru.nchernetsov.cache;

import java.math.BigInteger;

/**
 * Вычисление числа Фибоначчи с использованием кеширования для мемоизации
 */
public class FibCache {

    private final javax.cache.Cache<Integer, BigInteger> cache = new SlidingTimedCache<>(10_000L);

    /**
     * Функция вычисления n-го числа Фибоначчи
     *
     * @param n параметр функции
     * @return n-е число Фибоначчи
     */
    public BigInteger fib(int n) {
        if (n == 0) {
            return BigInteger.ZERO;
        } else if (n == 1) {
            return BigInteger.ONE;
        }

        // Если результат есть в кеше, то возвращаем его
        BigInteger cachedFib = cache.get(n);
        if (cachedFib != null) {
            return cachedFib;
        }

        // Вычисляем и кешируем результат
        BigInteger result = fib(n - 1).add(fib(n - 2));
        cache.put(n, result);

        return result;
    }
}
