package com.tanmay.day12.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(Environment env) throws Exception {
        String databaseUrl = env.getProperty("DATABASE_URL");
        if (databaseUrl == null || databaseUrl.isBlank()) {return DataSourceBuilder.create().build();
        }

        URI dbUri = new URI(databaseUrl);
        String username = "";
        String password = "";
        String userInfo = dbUri.getUserInfo();
        if (userInfo != null) {
            String[] parts = userInfo.split(":", 2);
            username = parts[0];
            password = parts.length > 1 ? parts[1] : "";
        }

        HikariConfig cfg = getHikariConfig(dbUri, username, password);

        return new HikariDataSource(cfg);
    }

    private static HikariConfig getHikariConfig(URI dbUri, String username, String password) {
        String host = dbUri.getHost();
        int port = dbUri.getPort() == -1 ? 5432 : dbUri.getPort();
        String path = dbUri.getPath();

        String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + path + "?sslmode=require";

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername(username);
        cfg.setPassword(password);
        cfg.setDriverClassName("org.postgresql.Driver");
        cfg.setMaximumPoolSize(5);
        cfg.setMinimumIdle(1);
        return cfg;
    }
}

