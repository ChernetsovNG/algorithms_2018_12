package ru.nchernetsov.eratosthenes;

import java.util.ArrayList;
import java.util.List;

/**
 * Алгоритм "Решето Эратосфена" для вычисления простых чисел
 */
public class EratosthenesSieve {

    /**
     * Алгоритм нахождения простых чисел до заданного числа n
     *
     * @param n верхняя граница диапазона определения простых числе
     * @return список найденных простых чисел меньших заданного n
     */
    public static List<Integer> getPrimeNumbersSimple(int n) {

        boolean[] primes = new boolean[n + 1];
        for (int i = 0; i < primes.length; i++) {
            primes[i] = true;
        }

        for (int i = 2; i * i <= n; i++) {
            if (primes[i]) {  // если текущее число - простое, то помечаем все числа, кратные ему, как составные
                if ((long) i * i <= n) {
                    for (int j = i * i; j <= n; j += i) {
                        primes[j] = false;
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 2; i < primes.length; i++) {
            if (primes[i]) {
                result.add(i);
            }
        }

        return result;
    }

    /**
     * Алгоритм нахождения простых чисел до заданного числа n с использованием побитовых операций для оптимизации
     *
     * @param n верхняя граница диапазона определения простых числе
     * @return список найденных простых чисел, меньших заданного n
     */

    // Prints all prime numbers smaller than n.
    public static List<Integer> getPrimeNumbersBitwise(int n) {

        // в типе int Java содержится 32 бита. за счёт чего можно уменьшить требуемый для вычислений объём памяти
        int[] primes = new int[n / 64 + 1];

        // 2 - это единственноё чётное простое число, поэтому цикл можно начать с 3
        for (int i = 3; i * i <= n; i += 2) {
            // если i - простое число, то помечаем все кратные ему числа как составные
            if (ifNotPrime(primes, i) == 0) {
                // j = i^2, k = i*2
                for (int j = i * i, k = i << 1; j < n; j += k)
                    makeComposite(primes, j);
            }
        }

        List<Integer> result = new ArrayList<>();
        result.add(2);

        // Добавляем простые числа в список результатов
        for (int i = 3; i <= n; i += 2) {
            if (ifNotPrime(primes, i) == 0) {
                result.add(i);
            }
        }

        return result;
    }

    /**
     * проверка, является ли заданное число x простым или составным
     *
     * @param primes массив простых чисел
     * @param x      проверяемое число
     * @return значение бита в массиве primes[x / 64] в позиции x / 2:
     * 0 - если x - простое, 1 - если x - составное
     */
    private static int ifNotPrime(int[] primes, int x) {
        // x >> 1 - делим пополам
        // (x >> 1) & 31 - ограничиваем число первыми 5-ю битами справа
        // 1 << ((x >> 1) & 31) - сдвигаем 1-й бит справа на x/2 позиций влево
        // primes[x / 64] & (1 << ((x >> 1) & 31)) - находим в массиве primes бит, находящийся в позиции x/2
        return primes[x / 64] & (1 << ((x >> 1) & 31));
    }

    // Marks x composite in prime[]

    /**
     * Помечает число x как составное в массиве primes
     *
     * @param primes массив
     * @param x      число
     */
    private static void makeComposite(int[] primes, int x) {
        // Set a bit corresponding to given element.
        // Using prime[x/64], we find the slot
        // in prime array. To find the bit number,
        // we divide x by 2 and take its mod with 32.
        primes[x / 64] |= (1 << ((x >> 1) & 31));
    }
}
