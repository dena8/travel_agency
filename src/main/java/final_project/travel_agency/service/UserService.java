package final_project.travel_agency.service;

import final_project.travel_agency.model.binding.UserBindingModel;
import final_project.travel_agency.model.binding.UserRegisterBindingModel;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    void register(UserServiceModel userService);

    void addTourToCart(User user, TourServiceModel tourServiceModel);

    UserServiceModel getAuthenticatedUser();

    UserServiceModel getUserById(String id);


    void removeItemFromCart(String id, String name) throws NotFoundException;

    List<String> getAuthorityNames();

    void updateAuthority(UserBindingModel userBindingModel);
}
