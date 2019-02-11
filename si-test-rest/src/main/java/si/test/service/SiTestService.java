package si.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import si.test.model.Login;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SiTestService implements HackerDetector {
    private static final String SIGNIN_FAILURE = "SIGNIN_FAILURE";
    private static final int MINUTES = 5;
    private static final int MAX_FAILURE_ATTEMPTS = 4;

    private final CacheService cacheService;
    private final ConversionService conversionService;

    @Autowired
    public SiTestService(final CacheService cacheService, final ConversionService conversionService) {
        this.cacheService = cacheService;
        this.conversionService = conversionService;
    }

    public String parseLine(final String line) {
        if (line.contains(SIGNIN_FAILURE)) {
            final Login data = conversionService.convert(line, Login.class);
            final List<LocalDateTime> timeList = cacheService.get(data.getIp());
            if (!CollectionUtils.isEmpty(timeList)) {
                final List<LocalDateTime> currentFailureTimes = Stream.concat(timeList.stream(), Stream.of(data.getTime()))
                        .filter(item -> item.isAfter(data.getTime().minusMinutes(MINUTES)))
                        .collect(Collectors.toList());
                cacheService.put(data.getIp(), currentFailureTimes);

                if (currentFailureTimes.size() > MAX_FAILURE_ATTEMPTS) {
                    return data.getIp();
                }
            }
            else {
                cacheService.put(data.getIp(), Collections.singletonList(data.getTime()));
            }
        }
        return null;
    }

    public long getInterval(final String time1, final String time2) throws DateTimeParseException, ArithmeticException {
        final LocalDateTime dateTime1 = LocalDateTime.parse(time1, DateTimeFormatter.RFC_1123_DATE_TIME);
        final LocalDateTime dateTime2 = LocalDateTime.parse(time2, DateTimeFormatter.RFC_1123_DATE_TIME);
        final Duration duration = Duration.between(dateTime1, dateTime2);
        return duration.toMinutes();
    }
}
