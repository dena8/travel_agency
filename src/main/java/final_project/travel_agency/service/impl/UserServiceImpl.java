package final_project.travel_agency.service.impl;


import final_project.travel_agency.model.entity.Authority;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.AuthorityRepository;
import final_project.travel_agency.repository.UserRepository;
import final_project.travel_agency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void register(UserServiceModel userService) {
//        Authority authority=null;
//
//        if(this.userRepository.count()<1){
//            authority = this.authorityRepository.findByAuthority("ADMIN_ROLE").orElse(null);
//        }
        Authority authority = this.authorityRepository.findByAuthority(this.userRepository.count()<1?"ADMIN_ROLE":"USER_ROLE")
                .orElse(null);

        User user = this.modelMapper.map(userService, User.class);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        this.userRepository.saveAndFlush(user);
    }
}
