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
