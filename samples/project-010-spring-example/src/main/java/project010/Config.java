package project010;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application.properties")
public class Config {

    @Value("${hello.spring}")
    private String spring;

    @Bean("special-string")
    public String greet() {
        return "hello from " + spring;
    }
}
