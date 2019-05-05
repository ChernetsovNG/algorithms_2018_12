package ru.nchernetsov.cache;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public interface Cache<K, V> {

    /**
     * Поместить элемент в кеш
     *
     * @param element элемент
     */
    void put(Element<K, V> element);

    void put(K key, V value);

    /**
     * Получить элемент из кеша по ключу
     *
     * @param key ключ
     * @return элемент из кеша
     */
    Element<K, V> getElement(K key);

    V get(K key);

    /**
     * Удалить элемент по ключу
     *
     * @param key ключ
     */
    void removeElement(K key);

    /**
     * Получить все элементы в кеше
     *
     * @return список элементов
     */
    List<Element<K, V>> getAll();

    /**
     * Очистить кеш
     */
    void dispose();

    /**
     * Текущее время, UNIX-time, в мс
     *
     * @return текущее время
     */
    static long getCurrentTime() {
        return LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
