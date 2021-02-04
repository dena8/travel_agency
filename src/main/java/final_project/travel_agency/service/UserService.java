package final_project.travel_agency.service;

import final_project.travel_agency.model.binding.UserRegisterBindingModel;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    void register(UserServiceModel userService);


    void addTourToCart(User user, TourServiceModel tourServiceModel);


}
