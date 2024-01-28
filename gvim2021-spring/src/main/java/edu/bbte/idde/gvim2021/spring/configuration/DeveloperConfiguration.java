package edu.bbte.idde.gvim2021.spring.configuration;

import edu.bbte.idde.gvim2021.spring.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.spring.dao.memory.ApartmentAdMemoryDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DeveloperConfiguration {

    @Bean
    public ApartmentAdDao buildApartmentAdDao() {
        return new ApartmentAdMemoryDao();
    }
}
