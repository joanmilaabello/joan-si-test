package si.test.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class SiTestConfigurationTest {

    @InjectMocks
    private SiTestConfiguration siTestConfiguration;

    @Test
    public void shouldGetACacheManagerBean() {
        assertNotNull(siTestConfiguration.cacheManager(""));
    }
}
