package ru.nchernetsov.BloomFilter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MaliciousUrlCheckerTest {

    private static MaliciousUrlChecker checker;

    @BeforeClass
    public static void beforeAll() {
        checker = new MaliciousUrlChecker(0.001);
    }

    @Test
    public void checkUrlIsMaliciousTest1() {
        // проверим несколько адресов, которые содержатся в БД
        List<String> maliciousUrls = Arrays.asList(
            "http://www.jessica-nava.us",
            "http://www.parttimevanilla.blogspot.com.br",
            "http://www.shybghinnquc.ru",
            "http://www.zhaoylran.com.cn");

        assertThat(checker.checkUrlIsMalicious(maliciousUrls.get(0))).isTrue();
        assertThat(checker.checkUrlIsMalicious(maliciousUrls.get(1))).isTrue();
        assertThat(checker.checkUrlIsMalicious(maliciousUrls.get(2))).isTrue();
        assertThat(checker.checkUrlIsMalicious(maliciousUrls.get(3))).isTrue();
    }

    @Test
    public void checkUrlIsNotMaliciousTest1() {
        assertThat(checker.checkUrlIsMalicious("https://github.com/google/guava/blob/master/guava-tests/test/com/google/common/hash/BloomFilterTest.java")).isFalse();
    }

    // рассмотрим точность работы фильтра Блума (% ошибок, false positives и т.д.)
    @Test
    public void calcBloomFilterAccuracy() {
        // пройдём по всем элементам базы данных и проверим, что говорит на их счёт фильтр Блума
        Iterator<String> urlDatabaseIterator = MaliciousUrlDatabase.getUrlDatabaseIterator();

        int overallCount = 0;
        int truePositiveCount = 0;   // элемент есть во множестве, и фильтр говорит, что он есть
        int falseNegativeCount = 0;  // элемент есть во множестве, но фильтр говорит, что его нет

        while (urlDatabaseIterator.hasNext()) {
            String maliciousUrl = urlDatabaseIterator.next();
            boolean mightContain = checker.bloomFilterMightContain(maliciousUrl);
            overallCount++;
            if (mightContain) {
                truePositiveCount++;
            } else {
                falseNegativeCount++;
            }
        }

        System.out.printf("overall count = %d, true positive count = %d, false negative count = %d",
            overallCount, truePositiveCount, falseNegativeCount);

        // Видим, что ложно-отрицательных ответов нет, как и должно быть
    }
}
