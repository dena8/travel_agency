package final_project.travel_agency.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppCloudinaryConfig {
    private final Environment env;

    public AppCloudinaryConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public Cloudinary cloudinary(){
        return  new Cloudinary(ObjectUtils.asMap(
                "cloud_name", env.getProperty("CLOUD-NAME"),
                "api_key", env.getProperty("API-KEY"),
                "api_secret", env.getProperty("API-SECRET")));
    }

}
