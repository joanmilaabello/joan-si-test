package si.test.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArithmeticExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity handleErrorsResponse(final ArithmeticException exception) {
        return ResponseEntity.badRequest().body("Invalid time");
    }
}
