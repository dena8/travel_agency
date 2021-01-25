package final_project.travel_agency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CategoryNotFound extends RuntimeException {

    public CategoryNotFound(String message) {
        super(message);
    }
}
