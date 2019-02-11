package si.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import si.test.service.CacheService;
import si.test.service.SiTestService;

import java.time.format.DateTimeParseException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {
        SiTestController.class
})
public class SiTestControllerTest {

    private static final String CHECK_RL = "/si-test/check";
    private static final String INTERVAL_URL = "/si-test/interval";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SiTestService siTestService;

    @Mock
    private CacheService cacheService;

    @Mock
    private ConversionService conversionService;

    @Test
    public void checkShouldNotAcceptNullRequest() throws Exception {
        mockMvc.perform(get(CHECK_RL))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void checkShouldNotAcceptInvalidRequest() throws Exception {
        final String request = "invalid format";
        mockMvc.perform(get(CHECK_RL)
                .param("line", request))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void checkShouldManageRuntimeException() throws Exception {
        final String request = "192.168.1.1,123,SIGNIN_FAILURE,user";
        given(siTestService.parseLine(request)).willThrow(RuntimeException.class);
        mockMvc.perform(get(CHECK_RL)
                .param("line", request))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void shouldCheck() throws Exception {
        final String request = "192.168.1.1,123,SIGNIN_FAILURE,user";
        mockMvc.perform(get(CHECK_RL)
                .param("line", request))
                .andExpect(status().isOk());
    }

    @Test
    public void getIntervalManageRuntimeException() throws Exception {
        final String time1 = "Thu, 21 Dec 2000 16:01:07 +0200";
        final String time2 = "Thu, 22 Dec 2000 16:01:07 +0200";
        mockMvc.perform(get(INTERVAL_URL)
                .param("time1", time1)
                .param("time2", time2))
                .andExpect(status().isOk());
    }

    @Test
    public void getIntervalShouldManageArithmeticExceptionHandler() throws Exception {
        final String time1 = "Thu, 21 Dec 2000 16:01:07 +0200";
        final String time2 = "Thu, 22 Dec 2000 16:01:07 +0200";
        given(siTestService.getInterval(time1, time2)).willThrow(ArithmeticException.class);
        mockMvc.perform(get(INTERVAL_URL)
                .param("time1", time1)
                .param("time2", time2))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getIntervalShouldManageDateTimeParseExceptionHandler() throws Exception {
        final String time1 = "Thu, 21 Dec 2000 16:01:07 +0200";
        final String time2 = "Thu, 22 Dec 2000 16:01:07 +0200";
        given(siTestService.getInterval(time1, time2)).willThrow(DateTimeParseException.class);
        mockMvc.perform(get(INTERVAL_URL)
                .param("time1", time1)
                .param("time2", time2))
                .andExpect(status().is4xxClientError());
    }
}
