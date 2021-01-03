package final_project.travel_agency.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder bcrypt;
    private final ModelMapper modelMapper;
    private final UserDetailsService userDetailsService;

    public AppSecurityConfig(PasswordEncoder bcrypt, ModelMapper modelMapper, UserDetailsService userDetailsService) {
        this.bcrypt = bcrypt;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth
                     .userDetailsService(this.userDetailsService)
                     .passwordEncoder(this.bcrypt);
    }

    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/admin").hasRole("admin");
        http.csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers("**").permitAll();

    }
}
