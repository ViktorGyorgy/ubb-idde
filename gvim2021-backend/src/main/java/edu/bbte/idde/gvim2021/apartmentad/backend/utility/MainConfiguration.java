package edu.bbte.idde.gvim2021.apartmentad.backend.utility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MainConfiguration {
    @JsonProperty("jdbc")
    private JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();

    @JsonProperty("connectionPool")
    private ConnectionPoolConfiguration connectionPoolConfiguration = new ConnectionPoolConfiguration();
}
