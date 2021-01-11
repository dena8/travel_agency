package final_project.travel_agency.config;

import final_project.travel_agency.service.UserService;
import final_project.travel_agency.util.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder bcrypt;
    private final UserService userService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;



    public AppSecurityConfig(PasswordEncoder bcrypt, UserService userService, JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.bcrypt = bcrypt;
        this.userService = userService;

        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth
                     .userDetailsService(this.userService)
                     .passwordEncoder(this.bcrypt);

    }

    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/admin").hasRole("admin");
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/users/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

  }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
