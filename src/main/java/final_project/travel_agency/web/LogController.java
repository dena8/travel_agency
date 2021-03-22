package final_project.travel_agency.web;

import final_project.travel_agency.model.dto.LogDtoModel;
import final_project.travel_agency.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LogDtoModel>> getAllLogs(){
        List<LogDtoModel> logs= this.logService.getAllLogs();
        return new ResponseEntity<>(logs,HttpStatus.OK);
    }
}
