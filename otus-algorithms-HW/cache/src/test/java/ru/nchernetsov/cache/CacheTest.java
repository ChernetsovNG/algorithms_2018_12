package ru.nchernetsov.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheTest {

    private SlidingTimedCache<Integer, String> cache;

    @Before
    public void setUp() {
        cache = new SlidingTimedCache<>(100);
    }

    @After
    public void shutDown() {
        cache.close();
    }

    @Test
    public void putTest() throws InterruptedException {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four");
        cache.put(5, "five");

        // проверяем, что все элементы находятся в кеше
        Set<Integer> keySet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        Map<Integer, String> allElements = cache.getAll(keySet);
        assertThat(allElements).hasSize(5);

        // Ждём 300 мс, и проверяем, что все элементы из кеша удалились
        TimeUnit.MILLISECONDS.sleep(200);

        allElements = cache.getAll(keySet);
        assertThat(allElements).isEmpty();
    }

    @Test
    public void getTest1() throws InterruptedException {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four");
        cache.put(5, "five");

        // проверяем, что элемент можно получить из кеша
        String element1 = cache.get(1);
        assertThat(element1).isEqualTo("one");

        String element10 = cache.get(10);
        assertThat(element10).isNull();

        // Ждём 200 мс, и проверяем, что все элементы из кеша удалились
        TimeUnit.MILLISECONDS.sleep(250);

        Set<Integer> keySet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Map<Integer, String> allElements = cache.getAll(keySet);
        assertThat(allElements).isEmpty();
    }

    @Test
    public void getElementShouldUpdateLastAccessTimeTest() throws InterruptedException {
        Element<Integer, String> element = Element.of(1, "one");
        cache.putElement(element);
        long lastAccessTime = element.getLastAccessTime();

        // Ждём 70 мс и получаем элемент, время последнего доступа у него обновляется
        TimeUnit.MILLISECONDS.sleep(70);

        element = cache.getElement(1);
        assertThat(element).isNotNull();
        assertThat(element.getValue()).isEqualTo("one");
        assertThat(element.getLastAccessTime()).isGreaterThan(lastAccessTime);
        lastAccessTime = element.getLastAccessTime();

        // Ждём ещё 70 мс => элемент на месте
        element = cache.getElement(1);
        assertThat(element).isNotNull();
        assertThat(element.getValue()).isEqualTo("one");
        assertThat(element.getLastAccessTime()).isGreaterThan(lastAccessTime);

        // Ждём 200 мс, и проверяем, что все элементы из кеша удалились
        TimeUnit.MILLISECONDS.sleep(200);

        Set<Integer> keySet = new HashSet<>(Collections.singletonList(1));
        Map<Integer, String> allElements = cache.getAll(keySet);
        assertThat(allElements).isEmpty();
    }

    @Test
    public void removeElementTest() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four");
        cache.put(5, "five");

        // проверяем, что все элементы находятся в кеше
        Set<Integer> keySet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        Map<Integer, String> allElements = cache.getAll(keySet);
        assertThat(allElements).hasSize(5);

        // удаляем элемент и проверяем, что он удалился
        cache.remove(3);

        allElements = cache.getAll(keySet);
        assertThat(allElements).hasSize(4);

        assertThat(cache.getElement(3)).isNull();
    }

    @Test
    public void closeTest() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four");
        cache.put(5, "five");

        // проверяем, что все элементы находятся в кеше
        Set<Integer> keySet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        Map<Integer, String> allElements = cache.getAll(keySet);
        assertThat(allElements).hasSize(5);

        cache.close();

        // Проверяем, что все элементы из кеша удалились
        allElements = cache.getAll(keySet);
        assertThat(allElements).isEmpty();
    }
}
