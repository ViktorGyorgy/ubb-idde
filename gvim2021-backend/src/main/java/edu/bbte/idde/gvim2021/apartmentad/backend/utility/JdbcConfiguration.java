package edu.bbte.idde.gvim2021.apartmentad.backend.utility;

import lombok.Data;

@Data
public class JdbcConfiguration {

    private String driverClass;
    private String url;
    private String username;
    private String password;
}
