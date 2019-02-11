package si.test.converter;

import org.springframework.core.convert.converter.Converter;
import si.test.model.Login;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LineToLogin implements Converter<String, Login> {

    public Login convert(final String source) {
        final List<String> data = Stream.of(source.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        final LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(data.get(1))), ZoneId.systemDefault());
        return new Login(data.get(0), time, data.get(2), data.get(3));
    }
}
