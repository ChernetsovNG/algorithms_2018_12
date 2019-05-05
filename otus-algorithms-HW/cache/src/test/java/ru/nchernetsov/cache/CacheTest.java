package ru.nchernetsov.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheTest {

    private Cache<Integer, String> cache;

    @Before
    public void setUp() {
        cache = new SlidingTimedCache<>(10, 100);
    }

    @After
    public void shutDown() {
        cache.dispose();
    }

    @Test
    public void putTest() throws InterruptedException {
        cache.put(Element.of(1, "one"));
        cache.put(Element.of(2, "two"));
        cache.put(Element.of(3, "three"));
        cache.put(Element.of(4, "four"));
        cache.put(Element.of(5, "five"));

        // проверяем, что все элементы находятся в кеше
        List<Element<Integer, String>> allElements = cache.getAll();
        assertThat(allElements).hasSize(5);

        // Ждём 300 мс, и проверяем, что все элементы из кеша удалились
        TimeUnit.MILLISECONDS.sleep(200);

        allElements = cache.getAll();
        assertThat(allElements).isEmpty();
    }

    @Test
    public void getTest() throws InterruptedException {
        Cache<Integer, String> cache = new SlidingTimedCache<>(10, 100);

        cache.put(Element.of(1, "one"));
        cache.put(Element.of(2, "two"));
        cache.put(Element.of(3, "three"));
        cache.put(Element.of(4, "four"));
        cache.put(Element.of(5, "five"));

        // проверяем, что элемент можно получить из кеша
        Element<Integer, String> element1 = cache.getElement(1);

        assertThat(element1.getKey()).isEqualTo(1);
        assertThat(element1.getValue()).isEqualTo("one");

        Element<Integer, String> element10 = cache.getElement(10);

        assertThat(element10).isNull();

        // Ждём 300 мс, и проверяем, что все элементы из кеша удалились
        TimeUnit.MILLISECONDS.sleep(200);

        List<Element<Integer, String>> allElements = cache.getAll();
        assertThat(allElements).isEmpty();
    }

    @Test
    public void removeElementTest() {
        Cache<Integer, String> cache = new SlidingTimedCache<>(10, 100);

        cache.put(Element.of(1, "one"));
        cache.put(Element.of(2, "two"));
        cache.put(Element.of(3, "three"));
        cache.put(Element.of(4, "four"));
        cache.put(Element.of(5, "five"));

        // проверяем, что все элементы находятся в кеше
        List<Element<Integer, String>> allElements = cache.getAll();
        assertThat(allElements).hasSize(5);

        // удаляем элемент и проверяем, что он удалился
        cache.removeElement(3);

        allElements = cache.getAll();
        assertThat(allElements).hasSize(4);

        assertThat(cache.getElement(3)).isNull();
    }

    @Test
    public void disposeTest() {
        Cache<Integer, String> cache = new SlidingTimedCache<>(10, 100);

        cache.put(Element.of(1, "one"));
        cache.put(Element.of(2, "two"));
        cache.put(Element.of(3, "three"));
        cache.put(Element.of(4, "four"));
        cache.put(Element.of(5, "five"));

        // проверяем, что все элементы находятся в кеше
        List<Element<Integer, String>> allElements = cache.getAll();
        assertThat(allElements).hasSize(5);

        cache.dispose();

        // Проверяем, что все элементы из кеша удалились
        allElements = cache.getAll();
        assertThat(allElements).isEmpty();
    }
}
