package final_project.travel_agency.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final DefaultErrorAttributes defaultErrorAttributes;

    public GlobalExceptionHandler(DefaultErrorAttributes defaultErrorAttributes) {
        this.defaultErrorAttributes = defaultErrorAttributes;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NotFoundEx.class)
    public final ResponseEntity<Object> NotFoundExHandler(NotFoundEx ex) {
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(new NotCorrectDataEx("Provided data is not correct!", validationList), status);
    }

    @ExceptionHandler(NotCorrectDataEx.class)
    public final ResponseEntity<Object> NotCorrectDataHandler(NotCorrectDataEx ex) {
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }


}
