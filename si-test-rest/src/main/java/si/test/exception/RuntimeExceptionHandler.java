package si.test.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RuntimeExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleErrorsResponse(final RuntimeException exception) {
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error");
    }
}
