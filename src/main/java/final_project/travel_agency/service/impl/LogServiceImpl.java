package final_project.travel_agency.service.impl;

import final_project.travel_agency.model.dto.LogDtoModel;
import final_project.travel_agency.model.entity.Log;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.repository.LogRepository;
import final_project.travel_agency.service.LogService;
import final_project.travel_agency.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final LogRepository logRepository;

    public LogServiceImpl(UserService userService, ModelMapper modelMapper, LogRepository logRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.logRepository = logRepository;
    }

    @Override
    public void createLogForOrderErrorAdvice(JoinPoint jp, Throwable ex) {
        Log log = new Log();
        log.setCustomer(this.modelMapper.map(this.userService.getAuthenticatedUser(), User.class).getUsername());
        log.setError(ex.toString());
        log.setStacktrace(Arrays.toString(ex.getStackTrace()));
        log.setDate(Instant.now());
        this.logRepository.saveAndFlush(log);
    }

    @Override
    public List<LogDtoModel> getAllLogs() {
        return List.of( this.modelMapper.map(this.logRepository.findAllByOrderByDateAsc(), LogDtoModel[].class));
    }
}
