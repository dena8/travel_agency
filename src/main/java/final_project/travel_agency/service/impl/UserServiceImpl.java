package final_project.travel_agency.service.impl;

import final_project.travel_agency.exception.NotCorrectDataEx;
import final_project.travel_agency.exception.NotFoundEx;
import final_project.travel_agency.model.binding.UserBindingModel;
import final_project.travel_agency.model.entity.Authority;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.AuthorityServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.UserRepository;
import final_project.travel_agency.service.AuthorityService;
import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TourService tourService;
    private final PasswordEncoder bcrypt;
    private final AuthorityService authorityService;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, @Lazy TourService tourService, AuthorityService authorityService, PasswordEncoder bcrypt) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.tourService = tourService;
        this.bcrypt = bcrypt;
        this.authorityService = authorityService;
    }

    @Override
    public void register(UserServiceModel userService) {
        AuthorityServiceModel authority = this.modelMapper.map(this.authorityService.getAuthorityBuName((this.userRepository.count() < 1 ? "ADMIN_ROLE" : "USER_ROLE")), AuthorityServiceModel.class);
        User user = this.modelMapper.map(userService, User.class);
        user.setPassword(this.bcrypt.encode(userService.getPassword()));
        List<Authority> authorities = new ArrayList<>();
        authorities.add(this.modelMapper.map(authority, Authority.class));
        user.setAuthorities(authorities);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserServiceModel getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user exist"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel getUserById(String id) {
        return this.modelMapper.map(this.userRepository.findById(id), UserServiceModel.class);
    }

    @Override
    public void removeItemFromCart(String id, String tourId) throws NotFoundException {
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundEx("User not found"));
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        TourServiceModel tourServiceModel = this.tourService.getTourById(tourId);
        userServiceModel.getCart().removeIf(t -> t.getId().equals(tourServiceModel.getId()));
        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public List<String> getAuthorityNames() {
        return this.authorityService.getAuthorityNames();
    }

    @Override
    public void updateAuthority(UserBindingModel userBindingModel) {
        User user = this.userRepository.findByUsername(userBindingModel.getUsername()).orElseThrow(() -> new NotFoundEx("user not found"));
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        AuthorityServiceModel authorityServiceModel = this.authorityService.getAuthorityBuName(userBindingModel.getAuthority());
        userServiceModel.getAuthorities().remove(0);
        userServiceModel.getAuthorities().add(authorityServiceModel);
        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundEx("No such user exist"));
    }

    @Override
    public void emptiedCard(User currentUser) throws NotFoundException {
        User user = this.userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new NotFoundEx("User not found"));
        user.getCart().clear();
        userRepository.saveAndFlush(user);
    }

    @Override
    public void addTourToCart(String id) throws Exception {
        TourServiceModel tourServiceModel = this.tourService.getTourById(id);
        if (tourServiceModel.getParticipants() < 1) {
            throw new NotCorrectDataEx("No vacant places");
        }
        UserServiceModel user = this.getAuthenticatedUser();
        user.getCart().add(tourServiceModel);
        this.tourService.updateParticipants(tourServiceModel.getId());
        this.userRepository.saveAndFlush(this.modelMapper.map(user, User.class));
    }

    @Override
    public boolean checkIfUserExist(String username) {
       User user=  this.userRepository.findByUsername(username).orElse(null);
        return user != null;
    }


}
