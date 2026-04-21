package com.exam.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @Bean
    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/federation_db";
        String user = "federation_user";
        String pass = "1234";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL introuvable", e);
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
