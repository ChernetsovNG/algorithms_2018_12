package ru.nchernetsov.BoyerMoore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class TestUtils {

    /**
     * Функция для чтения и разбора tsv-файла из ресурсов
     *
     * @param fileName имя файла
     * @return список разобранных тестовых примеров
     */
    public static List<BoyerMooreTestCase> readTestTsvFileFromResources(String fileName) {
        List<BoyerMooreTestCase> result = new ArrayList<>();
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
                result.add(new BoyerMooreTestCase(text, pattern, occurrencesIndices));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Функция для чтения и разбора txt-файла из ресурсов
     *
     * @param fileName имя файла
     * @return список разобранных тестовых примеров
     */
    public static List<FindPrefixesTestCase> readTestTxtFileFromResources(String fileName) {
        List<FindPrefixesTestCase> result = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream(fileName);
             BufferedReader tsvFile = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            // в первой строке общее число записей
            String firstRow = tsvFile.readLine();
            int rowsCount = Integer.parseInt(firstRow);
            /*
            Первая строка - количество записей, первый столбец - строка, далее через пробел -
            записанные последовательно значения из таблицы префиксов для данной строки
            */
            for (int i = 0; i < rowsCount; i++) {
                String dataRow = tsvFile.readLine();
                String[] rowSplit = dataRow.split(" ");
                String string = rowSplit[0];
                int[] prefixesTable = new int[rowSplit.length - 1];
                for (int j = 1; j < rowSplit.length; j++) {
                    prefixesTable[j - 1] = Integer.parseInt(rowSplit[j]);
                }
                result.add(new FindPrefixesTestCase(string, prefixesTable));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

class BoyerMooreTestCase {

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

    BoyerMooreTestCase(String text, String pattern, List<Integer> occurrencesIndices) {
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

class FindPrefixesTestCase {

    /**
     * Строка
     */
    private final String string;

    /**
     * Значения префиксов для данной строки
     */
    private final int[] prefixesTable;

    FindPrefixesTestCase(String string, int[] prefixesTable) {
        this.string = string;
        this.prefixesTable = prefixesTable;
    }

    public String getString() {
        return string;
    }

    public int[] getPrefixesTable() {
        return prefixesTable;
    }
}
