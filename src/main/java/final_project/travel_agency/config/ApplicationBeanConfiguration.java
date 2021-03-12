package final_project.travel_agency.config;

import final_project.travel_agency.exception.NotCorrectDataEx;
import final_project.travel_agency.service.UserService;
import final_project.travel_agency.util.filter.JwtAuthorizationFilter;
import final_project.travel_agency.util.jwt.JwtUtil;
import org.modelmapper.ModelMapper;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder bcrypt() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(JwtUtil jwtUtil, UserService userService) {
        return new JwtAuthorizationFilter(jwtUtil, userService);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
   public DefaultErrorAttributes defaultErrorAttributes(){
        return new DefaultErrorAttributes(){
            @Override
            public Map<String,Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options){
                Map<String,Object> errorAttributes = super.getErrorAttributes(webRequest,options);

                Throwable error = getError(webRequest);

                if (error instanceof NotCorrectDataEx) {
                    NotCorrectDataEx myException = (NotCorrectDataEx) error;
                    errorAttributes.put("validationList", myException.getValidationList());

                }


                 return errorAttributes;
            }
        };
   }

}



