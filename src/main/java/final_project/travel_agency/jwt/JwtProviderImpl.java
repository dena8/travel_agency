package final_project.travel_agency.jwt;

import final_project.travel_agency.model.service.UserServiceModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class JwtProviderImpl implements JwtProvider {


    @Override
    public String generateJwtToken(UserServiceModel user) {
        return null;
    }

    @Override
    public List<GrantedAuthority> getAuthorities(String token) {
        return null;
    }

    @Override
    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean isTokenValid(String username, String token) {
        return false;
    }

    @Override
    public String getSubject(String token) {
        return null;
    }
}
