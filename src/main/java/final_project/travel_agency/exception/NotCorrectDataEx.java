package final_project.travel_agency.exception;

import java.util.List;

public class NotCorrectDataEx extends Exception {
    private List<String>validationList;

    public NotCorrectDataEx(String message, List<String>validationList ) {
        super(message);
        this.validationList=validationList;
    }

    public List<String> getValidationList() {
        return validationList;
    }

    public void setValidationList(List<String> validationList) {
        this.validationList = validationList;
    }
}
