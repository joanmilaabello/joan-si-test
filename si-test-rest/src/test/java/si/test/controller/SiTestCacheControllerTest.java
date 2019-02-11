package si.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import si.test.service.CacheService;

@RunWith(MockitoJUnitRunner.class)
public class SiTestCacheControllerTest {

    @InjectMocks
    private SiTestCacheController siTestCacheController;

    @Mock
    private CacheService cacheService;

    @Test
    public void shouldClearCache() {
        siTestCacheController.clear();
    }
}
