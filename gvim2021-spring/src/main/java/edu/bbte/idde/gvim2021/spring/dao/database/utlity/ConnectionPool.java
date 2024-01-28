package edu.bbte.idde.gvim2021.spring.dao.database.utlity;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.gvim2021.spring.properties.JdbcProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
@Profile("prod")
public final class ConnectionPool {
    private HikariDataSource dataSource;

    @Autowired
    private JdbcProperties jdbcProperties;

    @PostConstruct
    public void postConstruct() {
        HikariConfig conf = new HikariConfig();
        conf.setJdbcUrl(jdbcProperties.getUrl());
        conf.setDriverClassName(jdbcProperties.getDriverClass());
        conf.setUsername(jdbcProperties.getUsername());
        conf.setMaximumPoolSize(jdbcProperties.getPoolSize());
        conf.setPassword(jdbcProperties.getPassword());

        dataSource = new HikariDataSource(conf);
        log.info("Connection pool to database created.");
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
