package ru.nchernetsov.BoyerMoore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

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
     * Функция для чтения и разбора tsv-файла из ресурсов
     *
     * @param fileName имя файла
     * @return список разобранных тестовых примеров
     */
    public static List<TestCase> readTsvFileFromResources(String fileName) {
        List<TestCase> result = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream(fileName);
             BufferedReader tsvFile = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            // в первой строке общее число записей
            String firstRow = tsvFile.readLine();
            int rowsCount = Integer.parseInt(firstRow);
            /*
            Первый столбец - текст (содержит пробелы),
            после первого таба - столбец с паттерном (также может содержать пробелы),
            в последнем столбце через пробел все вхождения паттерна в текст (позиции вхождений),
            в том числе, перекрывающиеся частично. Если вхождений нет, столбец пуст
             */
            StringTokenizer st;
            for (int i = 0; i < rowsCount; i++) {
                String dataRow = tsvFile.readLine();
                st = new StringTokenizer(dataRow, "\t");
                String text = st.nextElement().toString();
                String pattern = st.nextElement().toString();
                List<Integer> occurrencesIndices = new ArrayList<>();
                if (st.hasMoreElements()) {
                    String occurrencesIndicesAsString = st.nextElement().toString();
                    String[] occurrencesIndicesAsArray = occurrencesIndicesAsString.split(" ");
                    occurrencesIndices = Arrays.stream(occurrencesIndicesAsArray)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                }
                result.add(new TestCase(text, pattern, occurrencesIndices));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

class TestCase {

    /**
     * текст (содержит пробелы)
     */
    private final String text;

    /**
     * паттерн (может содержать пробелы)
     */
    private final String pattern;

    /**
     * индексы вхождений паттерна в текст
     */
    private final List<Integer> occurrencesIndices;

    TestCase(String text, String pattern, List<Integer> occurrencesIndices) {
        this.text = text;
        this.pattern = pattern;
        this.occurrencesIndices = Collections.unmodifiableList(occurrencesIndices);
    }

    public String getText() {
        return text;
    }

    public String getPattern() {
        return pattern;
    }

    public List<Integer> getOccurrencesIndices() {
        return occurrencesIndices;
    }
}
