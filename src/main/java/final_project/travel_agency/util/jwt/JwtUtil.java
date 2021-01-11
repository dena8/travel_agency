package final_project.travel_agency.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import final_project.travel_agency.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

import static final_project.travel_agency.constant.SecurityConstant.EXPIRATION_TIME;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;


    public JwtUtil() {

    }

    public String generateToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        List<String> claims = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("http//:localhost5000")
                .withClaim("roles", claims)
                .withClaim("username", userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }


    public Boolean verifyToken(String token) throws Exception {
        JWTVerifier verifier = getJwtVerifier();
        try {
            DecodedJWT jwt = verifier.verify(token);
            String subject = jwt.getSubject();
            Map<String, Claim> claims = jwt.getClaims();
            System.out.println(subject);
            System.out.println(claims.get("username").asString());
            return true;

        } catch (JWTVerificationException ex) {
            throw new Exception("Invalid token", ex);
        }

    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getSubject();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String userName = getSubject(token);
        return userName.equals(userDetails.getUsername()) && isExpiredToken(token);

    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claim = getJwtVerifier().verify(token).getClaim("roles").asArray(String.class);
        return Arrays.stream(claim).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }


    private Boolean isExpiredToken(String token) {
        Date expiration = getJwtVerifier().verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private JWTVerifier getJwtVerifier() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).withIssuer("http//:localhost5000").build();
    }


}
