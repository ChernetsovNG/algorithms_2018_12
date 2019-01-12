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
     * @return список найденных простых чисел
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
     * @return список найденных простых чисел
     */
    public static List<Integer> getPrimeNumbersBitwise(int n) {

        return null;
    }
}



/*

// Checks whether x is prime or composite
    static int ifnotPrime(int prime[], int x)
    {
        // checking whether the value of element
        // is set or not. Using prime[x/64],
        // we find the slot in prime array.
        // To find the bit number, we divide x
        // by 2 and take its mod with 32.
        return (prime[x/64] & (1 << ((x >> 1) & 31)));
    }

    // Marks x composite in prime[]
    static void makeComposite(int prime[], int x)
    {
        // Set a bit corresponding to given element.
        // Using prime[x/64], we find the slot
        // in prime array. To find the bit number,
        // we divide x by 2 and take its mod with 32.
        prime[x/64] |= (1 << ((x >> 1) & 31));
    }

    // Prints all prime numbers smaller than n.
    static void bitWiseSieve(int n)
    {
        // Assuming that n takes 32 bits,
        // we reduce size to n/64 from n/2.
        int prime[] = new int[n/64 + 1];


        // 2 is the only even prime so we
        // can ignore that loop starts from
        // 3 as we have used in sieve of
        // Eratosthenes .
        for (int i = 3; i * i <= n; i += 2) {

            // If i is prime, mark all its
            // multiples as composite
            if (ifnotPrime(prime, i)==0)
                for (int j = i * i, k = i << 1;
                                  j < n; j += k)
                    makeComposite(prime, j);
        }

        // writing 2 separately
        System.out.printf("2 ");

        // Printing other primes
        for (int i = 3; i <= n; i += 2)
            if (ifnotPrime(prime, i) == 0)
                System.out.printf("%d ", i);
    }

public static void main(String[] args)
{
    int n = 30;
    bitWiseSieve(n);
}

 */
