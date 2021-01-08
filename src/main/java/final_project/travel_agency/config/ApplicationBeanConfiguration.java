package final_project.travel_agency.config;

import final_project.travel_agency.util.jwt.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {



    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder bcrypt(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil (){
        return new JwtUtil();
    }


}
