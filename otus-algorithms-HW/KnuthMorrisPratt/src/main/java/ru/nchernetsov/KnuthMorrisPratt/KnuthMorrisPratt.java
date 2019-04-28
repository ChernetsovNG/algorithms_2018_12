package ru.nchernetsov.KnuthMorrisPratt;

public class KnuthMorrisPratt {


    public static StringStateMachine knuthMorrisPrattStateMachine(char[] alphabet, String pattern) {
        return new StringStateMachine(alphabet, pattern);
    }

    /**
     * Функция суффиксов, которая для строки x рассчитывает значение
     * sigma(x) = max{k: P_k (префикс длины k) является суффиксом x}
     *
     * @param x строка
     * @return sigma(x)
     */
    public static int suffixFunctionSigma(String x) {
        if (x.isEmpty()) {
            return 0;
        }

        int size = x.length();

        int i = 0, j = size;

        int result = 0;
        while (i < size) {
            i++;
            j--;
            if (i == size) {
                break;
            }
            String prefix = x.substring(0, i);
            String suffix = x.substring(j, size);
            if (prefix.equals(suffix)) {
                result = i;
            }
        }

        return result;
    }
}
