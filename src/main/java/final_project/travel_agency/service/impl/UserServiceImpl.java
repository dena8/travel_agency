package final_project.travel_agency.service.impl;
import final_project.travel_agency.model.entity.Authority;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.AuthorityRepository;
import final_project.travel_agency.repository.TourRepository;
import final_project.travel_agency.repository.UserRepository;
import final_project.travel_agency.service.UserService;
import org.modelmapper.ModelMapper;
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
    private final TourRepository tourRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder bcrypt;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, TourRepository tourRepository, AuthorityRepository authorityRepository, PasswordEncoder bcrypt) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.authorityRepository = authorityRepository;
        this.bcrypt = bcrypt;
    }

    @Override
    public void register(UserServiceModel userService) {

        Authority authority = this.authorityRepository.findByAuthority(this.userRepository.count()<1?"GUIDE_ROLE":"USER_ROLE")
                .orElse(null);
//        Authority guide = this.authorityRepository.findByAuthority("GUIDE_ROLE")
//                .orElse(null);

        User user = this.modelMapper.map(userService, User.class);
        user.setPassword(this.bcrypt.encode(userService.getPassword()));
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void addTourToCart(User user, TourServiceModel tourServiceModel) {
       this.userRepository.updateUserCart(user.getId(),tourServiceModel.getId());
       this.tourRepository.updateParticipants(tourServiceModel.getId());
        System.out.println("ID WHILE UPDATE: "+tourServiceModel.getId());
      //  this.userRepository.updateU(user.getId(),"new@abv.bg");
    }

    @Override
    public UserServiceModel getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("No such user exist"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("No such user exist"));

        /*User user=   this.userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("No such user exist"));
        List<GrantedAuthority> roles = user.getAuthorities()
                .stream()
                .map(a-> new SimpleGrantedAuthority(a.getAuthority()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(roles)
                .build();*/
    }







}
