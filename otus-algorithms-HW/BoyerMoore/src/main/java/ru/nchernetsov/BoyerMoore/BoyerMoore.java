package ru.nchernetsov.BoyerMoore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Алгоритм Бойера-Мура (-Хорспула)
 */
public class BoyerMoore {

    /**
     * В процессе работы алгоритма будем накапливать алфавит из использованных символов
     */
    private static final Map<Character, Integer> cachedAlphabet = new HashMap<>();

    /**
     * Алгоритм Бойера-Мура-Хорспула для вычисления вхождений заданного шаблона в текст
     *
     * @param text    текст
     * @param pattern шаблон
     * @return список позиций, начиная с которых заданный шаблон входит в текст
     */
    public static List<Integer> boyerMooreHorspoolAlg(String text, String pattern) {
        int patternLen = pattern.length();
        int textLen = text.length();

        fillCachedAlphabet(text);
        fillCachedAlphabet(pattern);

        Map<Character, Integer> alphabetMap = cachedAlphabet;

        List<Integer> matches = new ArrayList<>();

        int[][] badCharacterTable = badCharacterTable(pattern, alphabetMap);

        int checkToIndex = textLen - patternLen + 1;
        int shift = 0;
        while (shift < checkToIndex) {
            int j = patternLen - 1;
            // сравниваем шаблон с конца с текстом
            while (j >= 0 && text.charAt(shift + j) == pattern.charAt(j)) {
                j--;
            }
            if (j < 0) {
                matches.add(shift);
                shift++;
            } else {
                int patternShift = badCharacterTable[alphabetMap.get(text.charAt(shift + j))][j];
                shift += (j - patternShift);
            }
        }

        return matches;
    }

    /**
     * Функция для создания таблицы для работы эвристики "плохого символа"
     *
     * @param pattern     шаблон
     * @param alphabetMap таблица, в которой для каждого символа используемого алфавита хранится его индекс
     * @return подготовленная таблица
     */
    public static int[][] badCharacterTable(String pattern, Map<Character, Integer> alphabetMap) {
        int patternLen = pattern.length();
        int alphabetLen = alphabetMap.size();
        if (patternLen == 0) {
            return new int[0][alphabetLen];
        }
        int[] support = new int[alphabetLen];
        for (int i = 0; i < support.length; i++) {
            support[i] = -1;
        }
        int[][] table = new int[alphabetLen][1 + patternLen];
        for (int i = 0; i < alphabetLen; i++) {
            table[i][0] = -1;
        }
        for (int i = 0; i < patternLen; i++) {
            support[alphabetMap.get(pattern.charAt(i))] = i;
            for (int j = 0; j < alphabetLen; j++) {
                table[j][i + 1] = support[j];
            }
        }

        return table;
    }

    /**
     * Функция возвращает массив Z такой, что Z[i] содержит число, равное длине подстроки S[i:],
     * котороая при этом является и (максимально большим) префиксом S.
     * Если такого префикса нет, то Z[i] равен 0
     *
     * @param string строка
     * @return массив Z
     */
    public static int[] findPrefixes(String string) {
        int strLen = string.length();

        int[] Z = new int[strLen];
        for (int i = 0; i < strLen; i++) {
            Z[i] = maxCommonPrefixLen(string, i);
        }

        return Z;
    }

    /**
     * Функция находит длину наибольшего общего префикса заданной строки и строки,
     * полученной из заданной как подстроки, начиная с заданного индекса
     *
     * @param string             строка
     * @param substringFromIndex индекс для получения подстроки
     * @return длину наибольшего общего префикса
     */
    private static int maxCommonPrefixLen(String string, int substringFromIndex) {
        int minLength = string.length() - substringFromIndex;
        for (int i = 0; i < minLength; i++) {
            if (string.charAt(i) != string.charAt(i + substringFromIndex)) {
                return i;
            }
        }
        return minLength;
    }

    /**
     * Функция находит наибольший общий префикс в двух заданных строках
     *
     * @param a первая строка
     * @param b вторая строка
     * @return наибольший общий префикс
     */
    private static String maxCommonPrefix(String a, String b) {
        int minLength = Math.min(a.length(), b.length());
        for (int i = 0; i < minLength; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.substring(0, i);
            }
        }
        return a.substring(0, minLength);
    }

    private static void fillCachedAlphabet(String text) {
        int textLen = text.length();

        int maxCharIndex = 0;
        for (int i = 0; i < textLen; i++) {
            int textSymbolIndex = text.charAt(i);
            if (textSymbolIndex > maxCharIndex) {
                maxCharIndex = textSymbolIndex;
            }
        }

        for (int i = 0; i <= maxCharIndex; i++) {
            char unicodeChar = (char) i;
            cachedAlphabet.putIfAbsent(unicodeChar, i);
        }
    }
}
