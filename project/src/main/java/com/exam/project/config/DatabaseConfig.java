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
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database connection successful!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL Driver not found: " + e.getMessage());
            throw new SQLException("Driver PostgreSQL introuvable", e);
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            System.err.println("   URL: " + url);
            throw new SQLException("Connexion à la base de données échouée", e);
        }
    }
}
