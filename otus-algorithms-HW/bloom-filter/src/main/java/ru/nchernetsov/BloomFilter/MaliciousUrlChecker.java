package ru.nchernetsov.BloomFilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class MaliciousUrlChecker {

    private final BloomFilter<String> bloomFilter;

    /**
     * @param fpp ожидаемая вероятность ложно-положительных ответов в фильтре Блума
     */
    public MaliciousUrlChecker(double fpp) {
        int urlDatabaseSize = MaliciousUrlDatabase.getUrlDatabaseSize();
        Iterator<String> urlDatabaseIterator = MaliciousUrlDatabase.getUrlDatabaseIterator();

        // количество элементов в фильтре Блума задаём с некоторым запасом
        int expectedInsertions = urlDatabaseSize + 100_000;
        // создаём фильтр Блума и наполняем его данными
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), expectedInsertions, fpp);
        urlDatabaseIterator.forEachRemaining(bloomFilter::put);
    }

    public boolean checkUrlIsMalicious(String url) {
        try {
            String domainNameToCheck = getDomainName(url);
            // добавим точку вначале, т.к. в таком виде доменные имена хранятся в БД
            domainNameToCheck = "." + domainNameToCheck;
            boolean mightContain = bloomFilter.mightContain(domainNameToCheck);
            if (!mightContain) {  // элемент точно не содержится в множестве
                return false;
            }
            // если элемент может содержаться в множестве, то нужно его там поискать
            return MaliciousUrlDatabase.urlContainsInDatabase(domainNameToCheck);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean bloomFilterMightContain(String stringToCheck) {
        return bloomFilter.mightContain(stringToCheck);
    }

    private String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
