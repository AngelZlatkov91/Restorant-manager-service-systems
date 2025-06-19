package User.management.user.management.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "user.ui")
public class UserAppConfig {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public  UserAppConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
