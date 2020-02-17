package com.stream_work.ch08.util;

import com.stream_work.ch08.job.TransactionEvent;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class InMemoryCache implements Cache, Serializable {

    private static final long serialVersionUID = -3916565829914045234L;
    private final ConcurrentHashMap<String, SoftReference<CacheObject>> cache = new ConcurrentHashMap<>();

    public InMemoryCache() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(10000);
                    cache.entrySet().removeIf(entry -> Optional.ofNullable(entry.getValue()).map(SoftReference::get).map(CacheObject::isExpired).orElse(false));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    @Override
    public void add(String key, Object value, long periodInMillis) {
        if (key == null) {
            return;
        }
        if (value == null) {
            cache.remove(key);
        } else {
            long expiryTime = System.currentTimeMillis() + periodInMillis;
            cache.put(key, new SoftReference<>(new CacheObject(value, expiryTime)));
        }
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }



    @Override
    public Object get(String key) {
        return Optional.ofNullable(cache.get(key))
        .map(SoftReference::get)
        .filter(cacheObject -> !cacheObject.isExpired())
        .map(CacheObject::getValue)
        .orElse(null);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public long size() {
        return cache.entrySet().stream()
        .filter(entry -> Optional.ofNullable(entry.getValue())
        .map(SoftReference::get)
        .map(cacheObject -> !cacheObject.isExpired())
        .orElse(false)).count();
    }

    @Override
    public String list() {
        Enumeration<SoftReference<InMemoryCache.CacheObject>> elements = cache.elements();
        Collections.list(elements).forEach(e -> {
            TransactionEvent event = (TransactionEvent) e.get().value;
            //sout
        });
        return cache.elements().toString();


    }

    private static class CacheObject {

        private Object value;
        private long expiryTime;

        public CacheObject(Object value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        public Object getValue() {
            return value;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }

        @Override
        public String toString() {
            return "CacheObject{" +
                    "value=" + value +
                    ", expiryTime=" + expiryTime +
                    '}';
        }
    }
}
