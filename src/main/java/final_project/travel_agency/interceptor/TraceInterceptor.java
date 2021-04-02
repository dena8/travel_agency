package final_project.travel_agency.interceptor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class TraceInterceptor implements HandlerInterceptor {

    public TraceInterceptor() {
    }

    @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("trace-id", UUID.randomUUID().toString());
        return true;
    }
}
