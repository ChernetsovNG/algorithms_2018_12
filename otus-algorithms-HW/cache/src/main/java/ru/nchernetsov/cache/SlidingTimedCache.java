package ru.nchernetsov.cache;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Имплементация скользящего timed-кеша
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class SlidingTimedCache<K, V> implements Cache<K, V> {

    private static final int TIME_THRESHOLD_MS = 5;

    private final Map<K, Element<K, V>> elements = Collections.synchronizedMap(new LinkedHashMap<>());

    private final ScheduledExecutorService executorService;

    /**
     * Максимальное количество элементов в кеше
     */
    private final int maxElements;

    private final long lifeTimeMs;

    public SlidingTimedCache(int maxElements, long lifeTimeMs) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;

        // int availableProcessors = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newScheduledThreadPool(2);
    }

    @Override
    public void put(Element<K, V> element) {
        // если места уже нет, то выселяем первый добавленный элемент
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        K key = element.getKey();
        elements.put(key, element);

        if (lifeTimeMs != 0) {
            executorService.schedule(getTimerTask(key), lifeTimeMs, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void put(K key, V value) {
        put(Element.of(key, value));
    }

    @Override
    public Element<K, V> getElement(K key) {
        Element<K, V> element = elements.get(key);
        if (element != null) {
            element.setAccessTime();
        }
        return element;
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
    public void removeElement(K key) {
        elements.remove(key);
    }

    @Override
    public List<Element<K, V>> getAll() {
        return elements.values().stream()
            .peek(Element::setAccessTime)
            .collect(Collectors.toList());
    }

    @Override
    public void dispose() {
        elements.clear();
        executorService.shutdown();
    }

    /**
     * Отложенная задача по удалению элемента из кеша по ключу
     *
     * @param key ключ
     * @return отложенная задача
     */
    private Runnable getTimerTask(final K key) {
        return () -> {
            Element<K, V> checkedElement = elements.get(key);
            if (checkedElement == null || elementIsOutOfDate(checkedElement)) {
                elements.remove(key);
            }
        };
    }

    /**
     * Элемент устарел (по времени последнего доступа) и подлежит выселению из кеша
     *
     * @param element элемент
     * @return устарел ли элемент?
     */
    private boolean elementIsOutOfDate(Element<K, V> element) {
        return element.getLastAccessTime() + lifeTimeMs < Cache.getCurrentTime() + TIME_THRESHOLD_MS;
    }
}
