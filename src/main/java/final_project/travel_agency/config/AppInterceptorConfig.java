package final_project.travel_agency.config;

import final_project.travel_agency.interceptor.ReqProcessingTimeInterceptor;
import final_project.travel_agency.interceptor.TraceInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Component
public class AppInterceptorConfig implements WebMvcConfigurer {
    private final TraceInterceptor traceInterceptor;
    private final ReqProcessingTimeInterceptor reqProcessingTimeInterceptor;

    public AppInterceptorConfig(TraceInterceptor traceInterceptor, ReqProcessingTimeInterceptor reqProcessingTimeInterceptor) {
        this.traceInterceptor = traceInterceptor;
        this.reqProcessingTimeInterceptor = reqProcessingTimeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceInterceptor);
        registry.addInterceptor(reqProcessingTimeInterceptor);
    }
}
