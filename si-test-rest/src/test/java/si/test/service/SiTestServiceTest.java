package si.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import si.test.model.Login;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SiTestServiceTest {

    @InjectMocks
    private SiTestService siTestService;

    @Mock
    private CacheService cacheService;

    @Mock
    private ConversionService conversionService;

    @Test
    public void parseLineShouldSkipSuccesLog() {
        assertNull(siTestService.parseLine("SIGNIN_SUCCESS"));
    }

    @Test
    public void parseLineShouldSkipFirstFailure() {
        final String request = "127.25.25.0,123,SIGNIN_FAILURE,user";
        final Login login = new Login("127.25.25.0", LocalDateTime.now(), "SIGNIN_FAILURE", "user");

        given(conversionService.convert(request, Login.class)).willReturn(login);
        given(cacheService.get(login.getIp())).willReturn(Collections.emptyList());
        assertNull(siTestService.parseLine(request));
    }

    @Test
    public void parseLineShouldSkipFailure() {
        final String request = "127.25.25.0,123,SIGNIN_FAILURE,user";
        final Login login = new Login("127.25.25.0", LocalDateTime.now(), "SIGNIN_FAILURE", "user");
        final LocalDateTime time = LocalDateTime.now().minusMinutes(1);

        given(conversionService.convert(request, Login.class)).willReturn(login);
        given(cacheService.get(login.getIp())).willReturn(Collections.singletonList(time));
        assertNull(siTestService.parseLine(request));
    }

    @Test
    public void parseLineShouldCheckFailure() {
        final String request = "127.25.25.0,123,SIGNIN_FAILURE,user";
        final Login login = new Login("127.25.25.0", LocalDateTime.now(), "SIGNIN_FAILURE", "user");
        final LocalDateTime time1 = LocalDateTime.now().minusMinutes(1);
        final LocalDateTime time2 = LocalDateTime.now().minusMinutes(2);
        final LocalDateTime time3 = LocalDateTime.now().minusMinutes(3);
        final LocalDateTime time4 = LocalDateTime.now().minusMinutes(4);

        given(conversionService.convert(request, Login.class)).willReturn(login);
        given(cacheService.get(login.getIp())).willReturn(Arrays.asList(time1, time2, time3, time4));
        assertEquals(login.getIp(), siTestService.parseLine(request));
    }

    @Test
    public void getIntervalShouldReturnDifference() {
        final String date1 = "Thu, 21 Dec 2000 16:01:07 +0200";
        final String date2 = "Thu, 21 Dec 2000 16:02:06 +0200";
        final String date3 = "Thu, 21 Dec 2000 16:02:07 +0200";
        final String date4 = "Thu, 21 Dec 2000 17:02:07 +0200";
        assertEquals(0, siTestService.getInterval(date1, date2));
        assertEquals(1, siTestService.getInterval(date1, date3));
        assertEquals(60, siTestService.getInterval(date3, date4));
        assertEquals(-60, siTestService.getInterval(date4, date3));
    }
}
