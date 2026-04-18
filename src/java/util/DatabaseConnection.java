package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Verifique se o nome do banco é exatamente banco_pedro_toledo
    private static final String URL = "jdbc:mysql://localhost:3306/banco_pedro_toledo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Banco_007";

    public static Connection getConnection() throws SQLException {
        try {
            // Garante que o driver esteja no classpath
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado nas bibliotecas do projeto.", e);
        }
    }
}