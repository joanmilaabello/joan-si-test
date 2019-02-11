package si.test.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class DateTimeParseExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity handleErrorsResponse(final DateTimeParseException exception) {
        return ResponseEntity.badRequest().body("Invalid time");
    }
}
