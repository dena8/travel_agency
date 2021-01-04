package final_project.travel_agency.jwt;

import final_project.travel_agency.model.service.UserServiceModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JwtProvider {

    String generateJwtToken(UserServiceModel user);

    List<GrantedAuthority> getAuthorities(String token);

    Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request);

    boolean isTokenValid(String username, String token);

    String getSubject(String token);
}
