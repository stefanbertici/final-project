package ro.ubb.postuniv.musify.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        log.error("Illegal argument: " + e);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage());
        log.error("Unauthorized: " + e);

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
        log.error("Resource not found: " + e);

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errorMessages = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorMessages);
        log.error("Invalid argument: ", ex);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Generic server error");
        log.error("Generic error: " + ex);

        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
