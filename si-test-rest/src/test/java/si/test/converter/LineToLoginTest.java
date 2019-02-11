package si.test.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import si.test.model.Login;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LineToLoginTest {

    @InjectMocks
    private LineToLogin lineToLogin;

    @Test
    public void shouldConvertLineToLogin() {
        final LocalDateTime now = LocalDateTime.now();
        final String ip = "127.25.25.0";
        final String time = String.valueOf(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        final String result = "SIGNIN_FAILURE";
        final String user = "user";
        final String line = ip + "," + time + "," + result + "," + user;

        final Login login = lineToLogin.convert(line);
        assertEquals(ip, login.getIp());
        assertEquals(now, login.getTime());
        assertEquals(result, login.getResult());
        assertEquals(user, login.getUser());
    }
}
