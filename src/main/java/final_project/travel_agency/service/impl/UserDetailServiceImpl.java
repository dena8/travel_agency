package final_project.travel_agency.service.impl;

import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl( UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     User user=   this.userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("No such user exist"));
        List<GrantedAuthority> roles = user.getAuthorities()
                .stream()
                .map(a-> new SimpleGrantedAuthority(a.getAuthority()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(roles)
                .build();
    }
}
