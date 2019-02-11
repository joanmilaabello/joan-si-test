package si.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Login {
    private String ip;
    private LocalDateTime time;
    private String result;
    private String user;
}
