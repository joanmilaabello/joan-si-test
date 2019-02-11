package si.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CacheService {
    @Value("${cache.name}")
    private String cacheName;

    private final CacheManager cacheManager;

    @Autowired
    public CacheService(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public List<LocalDateTime> get(final String ip) {
        final ValueWrapper cached = cacheManager.getCache(cacheName).get(ip);
        return (cached != null)
                ? (List<LocalDateTime>) cached.get()
                : Collections.emptyList();
    }

    public void put(final String ip, final List<LocalDateTime> dateList) {
        cacheManager.getCache(cacheName).put(ip, dateList);
    }

    public void clear() {
        cacheManager.getCache(cacheName).clear();
    }
}
