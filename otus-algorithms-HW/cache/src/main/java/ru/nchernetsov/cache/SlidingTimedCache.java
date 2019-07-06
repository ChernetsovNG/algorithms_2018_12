package ru.nchernetsov.cache;

import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Имплементация скользящего timed-кеша
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class SlidingTimedCache<K, V> implements javax.cache.Cache<K, V> {

    private static final int TIME_THRESHOLD_MS = 5;

    private final Map<K, Element<K, V>> elements = new ConcurrentHashMap<>();

    private final ScheduledExecutorService executorService;

    private final long lifeTimeMs;

    public SlidingTimedCache(long lifeTimeMs) {
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        executorService = Executors.newScheduledThreadPool(2);
    }

    @Override
    public V get(K key) {
        Element<K, V> element = getElement(key);
        if (element != null) {
            return element.getValue();
        }
        return null;
    }

    @Override
    public Map<K, V> getAll(Set<? extends K> keys) {
        Map<K, V> result = new HashMap<>();
        for (K key : keys) {
            V value = get(key);
            if (value != null) {
                result.put(key, value);
            }
        }
        return result;
    }

    @Override
    public boolean containsKey(K key) {
        return elements.containsKey(key);
    }

    @Override
    public void loadAll(Set<? extends K> keys, boolean replaceExistingValues, CompletionListener completionListener) {
        throw new NotImplementedException();
    }

    @Override
    public void put(K key, V value) {
        putElement(Element.of(key, value));
    }

    @Override
    public V getAndPut(K key, V value) {
        synchronized (this) {
            V oldValue = get(key);
            put(key, value);
            return oldValue;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach(this::put);
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            put(key, value);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(K key) {
        if (!containsKey(key)) {
            return false;
        }
        elements.remove(key);
        return true;
    }

    @Override
    public boolean remove(K key, V oldValue) {
        if (containsKey(key) && get(key).equals(oldValue)) {
            remove(key);
            return true;
        }
        return false;
    }

    @Override
    public V getAndRemove(K key) {
        if (containsKey(key)) {
            V oldValue = get(key);
            remove(key);
            return oldValue;
        }
        return null;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if (containsKey(key) && get(key).equals(oldValue)) {
            put(key, newValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean replace(K key, V value) {
        if (containsKey(key)) {
            put(key, value);
            return true;
        }
        return false;
    }

    @Override
    public V getAndReplace(K key, V value) {
        if (containsKey(key)) {
            V oldValue = get(key);
            put(key, value);
            return oldValue;
        }
        return null;
    }

    @Override
    public void removeAll(Set<? extends K> keys) {
        for (K key : keys) {
            remove(key);
        }
    }

    @Override
    public void removeAll() {
        elements.clear();
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public <C extends Configuration<K, V>> C getConfiguration(Class<C> clazz) {
        throw new NotImplementedException();
    }

    @Override
    public <T> T invoke(K key, EntryProcessor<K, V, T> entryProcessor, Object... arguments) throws EntryProcessorException {
        throw new NotImplementedException();
    }

    @Override
    public <T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> keys, EntryProcessor<K, V, T> entryProcessor, Object... arguments) {
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        throw new NotImplementedException();
    }

    @Override
    public CacheManager getCacheManager() {
        throw new NotImplementedException();
    }

    @Override
    public void close() {
        elements.clear();
        executorService.shutdown();
    }

    @Override
    public boolean isClosed() {
        return executorService.isShutdown();
    }

    @Override
    public <T> T unwrap(Class<T> clazz) {
        throw new NotImplementedException();
    }

    @Override
    public void registerCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
        throw new NotImplementedException();
    }

    @Override
    public void deregisterCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        List<Entry<K, V>> entries = new ArrayList<>(elements.values());
        return entries.iterator();
    }

    // PRIVATE section

    void putElement(Element<K, V> element) {
        K key = element.getKey();
        elements.put(key, element);
        if (lifeTimeMs != 0) {
            // планируем задачу на выселение элемента через lifeTimeMs
            planRemoveElementTask(key);
        }
    }

    Element<K, V> getElement(K key) {
        Element<K, V> element = elements.get(key);
        if (element != null) {
            element.setAccessTime();
            // после обновления lastAccessTime планируем задачу на выселение элемента через lifeTimeMs
            planRemoveElementTask(key);
        }
        return element;
    }

    private void planRemoveElementTask(final K key) {
        executorService.schedule(removeElementTask(key), lifeTimeMs, TimeUnit.MILLISECONDS);
    }

    // Отложенная задача по удалению элемента из кеша по ключу
    private Runnable removeElementTask(final K key) {
        return () -> {
            Element<K, V> checkedElement = elements.get(key);
            if (checkedElement == null || isElementOutOfDate(checkedElement)) {
                // удаляем элемент из кеша
                elements.remove(key);
            }
        };
    }

    // Элемент устарел (по времени последнего доступа) и подлежит выселению из кеша
    private boolean isElementOutOfDate(Element<K, V> element) {
        return element.getLastAccessTime() + lifeTimeMs < Cache.getCurrentTime() + TIME_THRESHOLD_MS;
    }
}
