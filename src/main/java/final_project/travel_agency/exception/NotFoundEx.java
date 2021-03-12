package final_project.travel_agency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundEx extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundEx(String message) {
        super(message);
    }
}
