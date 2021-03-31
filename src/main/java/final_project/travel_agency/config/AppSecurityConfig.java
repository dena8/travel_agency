package final_project.travel_agency.config;

import final_project.travel_agency.service.UserService;
import final_project.travel_agency.util.filter.JwtAuthorizationFilter;


import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity()
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations())).permitAll()
                .antMatchers("/users/*").permitAll()
                .antMatchers("/tours/all").permitAll()
                .antMatchers("/galleries/view").permitAll()
                .antMatchers("/categories/*").hasAuthority("GUIDE_ROLE")
                .antMatchers("/cart/**").hasAuthority("USER_ROLE")
                .antMatchers("/orders/**").hasAuthority("ADMIN_ROLE")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore( jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

  }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


}
