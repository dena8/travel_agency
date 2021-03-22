package final_project.travel_agency.service;

import final_project.travel_agency.model.dto.LogDtoModel;
import org.aspectj.lang.JoinPoint;

import java.util.List;

public interface LogService {
    void createLogForOrderErrorAdvice(JoinPoint jp, Throwable ex);

    List<LogDtoModel> getAllLogs();
}
