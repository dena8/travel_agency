package final_project.travel_agency.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.DatatypeConverter;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public JwtUtil() {
    }

    //    //generate token for user
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }
//
//    //while creating the token -
//    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//    //2. Sign the JWT using the HS512 algorithm and secret key.
//    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//    //   compaction of the JWT to a URL-safe string
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, secret).compact();
//    }
//
//    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    public String generateToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        List<String> claims = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("http//:localhost5000")
                .withClaim("roles", claims)
                .withClaim("username",userDetails.getUsername())
                .sign(algorithm);
    }


//    //validate token
//    public Boolean validateToken(String token) {
//        Claims claims = getUsernameFromToken(token);
//       // List<Claims> username = getUsernameFromToken(token);
//        System.out.println(claims);
//        return false;
//       // return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    private Claims getUsernameFromToken(String token) {
//      return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//
//    }

    public Boolean verifyToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("http//:localhost5000").build();
        try {
         DecodedJWT jwt =  verifier.verify(token);
            String subject=jwt.getSubject();
            Map<String,Claim> claims=jwt.getClaims();
            System.out.println(subject);
            System.out.println(claims.get("username").asString());
            return true;

        } catch (JWTVerificationException ex) {
            throw new Exception("Invalid token", ex);
        }

    }


}
