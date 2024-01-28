package edu.bbte.idde.gvim2021.spring.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
@Data
public class JdbcProperties {
    @Value("${jdbc.driverClass}")
    private String driverClass;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.poolSize}")
    private Integer poolSize;

    @Value("${jdbc.password}")
    private String password;
}
