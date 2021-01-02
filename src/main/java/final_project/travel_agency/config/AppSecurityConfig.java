package final_project.travel_agency.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder bcrypt;

    public AppSecurityConfig(PasswordEncoder bcrypt) {
        this.bcrypt = bcrypt;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(bcrypt.encode("user"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(bcrypt.encode("admin"))
                .roles("ADMIN");
    }

    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and().httpBasic();


        http.
                authorizeRequests()
                .antMatchers("/home")
                .permitAll()
               .antMatchers("/user_home").hasRole("USER")
                .antMatchers("/admin").hasRole(("ADMIN"))
                .and()
                .formLogin();
    }
}
