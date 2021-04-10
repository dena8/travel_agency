package final_project.travel_agency.service;

import final_project.travel_agency.model.binding.UserBindingModel;
import final_project.travel_agency.model.service.UserServiceModel;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    void register(UserServiceModel userService);

    UserServiceModel getAuthenticatedUser();

    UserServiceModel getUserById(String id);


    void removeItemFromCart(String id, String name) throws NotFoundException;

    List<String> getAuthorityNames();

   UserServiceModel updateAuthority(UserBindingModel userBindingModel);

    void emptiedCard(UserServiceModel user) throws NotFoundException;

    void addTourToCart(String id) throws Exception;

    boolean checkIfUserExist(String username);
}
