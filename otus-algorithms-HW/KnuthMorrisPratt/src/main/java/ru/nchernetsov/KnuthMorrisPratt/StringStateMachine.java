package ru.nchernetsov.KnuthMorrisPratt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Конечный автомат для работы со строками
 */
public class StringStateMachine {

    /**
     * Паттерн
     */
    private final String pattern;

    /**
     * Множество конечных состояний Q = {0, 1, ..., m}
     * i-е состояние автомата соответствует (ожидаемой) проверке i-го
     * символа паттерна, последнее - найденному совпадению
     */
    private final int[] Q;

    /**
     * Начальное состояние
     */
    private final int q0 = 0;

    /**
     * Текущее состояние конечного автомата
     */
    private int q;

    /**
     * Множество конечных (допускающих) состояний. В нашем случае одно состояние - конец паттерна
     */
    private final int[] A;

    /**
     * Конечный входной алфавит
     */
    private final char[] Sigma;

    /**
     * Отображение Q x Sigma -> Sigma, функция перехода
     * delta(q, a) = sigma(P[: q]a)
     * Функция принимает на вход следующий символ входной строки a,
     * и, зная текущее состояние конечного автомата q,
     * выдаёт следующее состояние конечного автомата
     */
    private final Map<Integer, Map<Character, Integer>> delta;

    public StringStateMachine(char[] alphabet, String pattern) {
        this.Sigma = alphabet;
        this.pattern = pattern;

        int patternLen = pattern.length();

        Q = new int[patternLen + 1];

        char[] chars = pattern.toCharArray();
        for (int i = 0; i <= chars.length; i++) {
            Q[i] = i;
        }

        A = new int[]{patternLen};

        q = q0;

        delta = new HashMap<>();

        // вычисление функции (таблицы) перехода
        computeDelta();
    }

    /**
     * Функция находит вхождения паттерна в заданный текст
     *
     * @param text текст
     * @return список позиций вхождения
     */
    public List<Integer> matcher(String text) {
        int m = pattern.length();
        int n = text.length();
        List<Integer> matches = new ArrayList<>();
        int q = 0;  // начальное состояние
        for (int i = 0; i < n; i++) {
            q = delta.get(q).get(text.charAt(i));
            if (q == m) {
                matches.add(i - m + 1);
            }
        }
        return matches;
    }

    /**
     * Алгоритм вычисления функции переходов (конечного автомата) для паттерна,
     * работающий за время O(m^3 * |Sigma|),
     * где m - длина паттерна, Sigma - набор входных данных (алфавит)
     */
    private void computeDelta() {
        int m = pattern.length();
        for (int q = 0; q <= m; q++) {
            for (char a : Sigma) {  // каждый символ алфавита
                int sigma = suffixFunction(pattern.substring(0, q) + a);
                delta.putIfAbsent(q, new HashMap<>());
                delta.get(q).put(a, sigma);
            }
        }
    }

    /**
     * Перейти в следующее состояние конечного автомата
     */
    public void goToNextState(char nextSymbol) {
        q = delta.get(q).get(nextSymbol);
    }

    /**
     * Находимся ли мы сейчас в конечном состоянии?
     *
     * @return находимся или нет?
     */
    public boolean itIsFinalState() {
        for (int elem : A) {
            if (q == elem) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, Map<Character, Integer>> getDelta() {
        return delta;
    }

    public char[] getSigma() {
        return Sigma;
    }

    public String getPattern() {
        return pattern;
    }

    public int[] getQ() {
        return Q;
    }

    public int get_q() {
        return q;
    }

    /**
     * Суффикс-функция, которая для строки x вычисляет длину максимального
     * суффикса x, являющегося префиксом pattern
     *
     * @param x строка
     * @return sigma(x)
     */
    public int suffixFunction(String x) {
        if (x.isEmpty()) {
            return 0;
        }

        int result = 0;

        int xLen = x.length();
        int j = xLen - 1;

        while (j >= 0) {
            String suffix = x.substring(j, xLen);
            if (pattern.startsWith(suffix)) {
                result = suffix.length();
            }
            j--;
        }

        return result;
    }
}
