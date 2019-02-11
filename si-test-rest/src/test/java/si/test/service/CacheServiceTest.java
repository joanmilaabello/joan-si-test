package si.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CacheServiceTest {

    @InjectMocks
    private CacheService cacheService;

    @Mock
    public CacheManager cacheManager;

    @Test
    public void getShouldReturnEmptyCollection() {
        final Cache cache = mock(Cache.class);
        given(cacheManager.getCache(any())).willReturn(cache);
        //given(cache.get(anyString())).willReturn(null);
        assertEquals(Collections.emptyList(), cacheService.get(null));
    }

    @Test
    public void getShouldReturnCachedValue() {
        final String id = "test";
        final Cache cache = mock(Cache.class);
        final Cache.ValueWrapper wrapper = mock(Cache.ValueWrapper.class);
        final List<LocalDateTime> value = Collections.singletonList(LocalDateTime.now());

        given(cacheManager.getCache(any())).willReturn(cache);
        given(cache.get(id)).willReturn(wrapper);
        given(wrapper.get()).willReturn(value);
        assertEquals(value, cacheService.get(id));
    }

    @Test
    public void shouldPut() {
        final Cache cache = mock(Cache.class);
        given(cacheManager.getCache(any())).willReturn(cache);
        cacheService.put("test", Collections.singletonList(LocalDateTime.now()));
    }

    @Test
    public void shouldClear() {
        final Cache cache = mock(Cache.class);
        given(cacheManager.getCache(any())).willReturn(cache);
        cacheService.clear();
    }
}
