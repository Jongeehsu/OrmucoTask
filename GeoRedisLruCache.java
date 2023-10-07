package Assignment3;

import redis.clients.jedis.Jedis;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GeoRedisLruCache<K, V> {

    private final Map<K, CacheEntry<V>> cache;
    private final long cacheExpirationTimeMillis;
    private final ScheduledExecutorService evictionScheduler;
    private final Jedis jedis;
    private final int cacheSize;
    private final String region;

    public GeoRedisLruCache(int maxSize, long cacheExpirationTimeMillis, int cacheSize, String redisHost, int redisPort, String region) {
        this.cacheExpirationTimeMillis = cacheExpirationTimeMillis;
        this.cacheSize = cacheSize;
        this.region = region;
        this.cache = new LinkedHashMap<K, CacheEntry<V>>(maxSize, 0.8f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<V>> eldest) {
                return size() > maxSize;
            }
        };

        this.evictionScheduler = Executors.newScheduledThreadPool(1);
        this.evictionScheduler.scheduleAtFixedRate(this::evictExpiredEntries, 0, 1, TimeUnit.MINUTES);

        this.jedis = new Jedis(redisHost, redisPort);
    }

    public void put(K key, V value) {
        CacheEntry<V> entry = new CacheEntry<>(value, System.currentTimeMillis());
        cache.put(key, entry);


        String redisKey = generateRedisKey(key);
        jedis.set(redisKey, value.toString());
        jedis.expire(redisKey, (int) TimeUnit.MILLISECONDS.toSeconds(cacheExpirationTimeMillis));
    }

    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry != null) {
            entry.setLastAccessed(System.currentTimeMillis());
            return entry.getValue();
        }


        String redisKey = generateRedisKey(key);
        String redisValue = jedis.get(redisKey);
        if (redisValue != null) {
            V value = (V) redisValue;
            put(key, value);
            return value;
        }

        return null;
    }

    private void evictExpiredEntries() {
        long currentTime = System.currentTimeMillis();
        cache.entrySet().removeIf(entry -> (currentTime - entry.getValue().getLastAccessed()) >= cacheExpirationTimeMillis);
    }

    private String generateRedisKey(K key) {
        return region + ":" + key.toString();
    }

    private static class CacheEntry<V> {
        private final V value;
        private long lastAccessed;

        CacheEntry(V value, long lastAccessed) {
            this.value = value;
            this.lastAccessed = lastAccessed;
        }

        V getValue() {
            return value;
        }

        long getLastAccessed() {
            return lastAccessed;
        }

        void setLastAccessed(long lastAccessed) {
            this.lastAccessed = lastAccessed;
        }
    }

    public static void main(String[] args) {
        GeoRedisLruCache<String, String> cache = new GeoRedisLruCache<>(10, TimeUnit.MINUTES.toMillis(10), 100, "localhost", 6379, "us-east");

        cache.put("key1", "value1");
        cache.put("key2", "value2");

        System.out.println(cache.get("key1"));
        System.out.println(cache.get("key3"));
    }
}
