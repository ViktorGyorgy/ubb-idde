package edu.bbte.idde.gvim2021.apartmentad.backend.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigurationFactory {
    private static final String CONFIG_FILE = "/application.json";

    private static final ObjectMapper OBJECT_MAPPER;

    private static MainConfiguration mainConfiguration = new MainConfiguration();

    static {
        OBJECT_MAPPER = new ObjectMapper();

        try (InputStream inputStream = ConfigurationFactory.class.getResourceAsStream(CONFIG_FILE)) {
            mainConfiguration = OBJECT_MAPPER.readValue(inputStream, MainConfiguration.class);
            log.info("Read following configuration: {}", mainConfiguration);
        } catch (IOException e) {
            log.error("Error loading configuration", e);
        }
    }

    public static MainConfiguration getMainConfiguration() {
        return mainConfiguration;
    }

    public static JdbcConfiguration getJdbcConfiguration() {
        return mainConfiguration.getJdbcConfiguration();
    }

    public static ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return mainConfiguration.getConnectionPoolConfiguration();
    }
}
