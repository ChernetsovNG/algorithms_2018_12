package ru.nchernetsov.cache;

import java.util.Objects;

import static ru.nchernetsov.cache.Cache.getCurrentTime;

/**
 * Элемент кеша
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class Element<K, V> {

    /**
     * Ключ
     */
    private final K key;

    /**
     * Значение
     */
    private final V value;

    /**
     * Время создания элемента, UTC, мс
     */
    private final long creationTime;

    /**
     * Время последнего доступа к элементу, UTC, мс
     */
    private long lastAccessTime;

    private Element(K key, V value) {
        this.key = key;
        this.value = value;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    public static <K, V> Element<K, V> of(K key, V value) {
        return new Element<>(key, value);
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    synchronized void setAccessTime() {
        lastAccessTime = getCurrentTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element<?, ?> element = (Element<?, ?>) o;

        if (!Objects.equals(key, element.key)) return false;
        return Objects.equals(value, element.value);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
