package final_project.travel_agency.constant;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstant {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds

}
