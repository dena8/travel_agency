package final_project.travel_agency.service;

import final_project.travel_agency.model.binding.UserRegisterBindingModel;
import final_project.travel_agency.model.service.UserServiceModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void register(UserServiceModel userService);
}
