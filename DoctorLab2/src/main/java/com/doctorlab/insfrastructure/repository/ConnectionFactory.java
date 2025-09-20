package br.com.doctorlab.infrastructure.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() {
        String url = "jdbc:h2:mem:doctorlab;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";
        String user = "sa";
        String password = "";

        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}