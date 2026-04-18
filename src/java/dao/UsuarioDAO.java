package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;

public class UsuarioDAO {

    public Usuario autenticar(String email, String senha) throws SQLException {
        // Usamos TRIM para evitar que espaços invisíveis no banco causem erro de senha
        String sql = "SELECT * FROM Usuario WHERE TRIM(email) = ? AND TRIM(senha) = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email.trim());
            stmt.setString(2, senha.trim());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipoUsuario");
                    
                    if ("Administrador".equals(tipo)) {
                        // RN07: Retorna um Administrador com permissões totais
                        return new Administrador(
                            rs.getInt("idUsuario"), 
                            rs.getString("nome"), 
                            rs.getString("cargo")
                        );
                    } else if ("Cliente".equals(tipo)) {
                        // RN07: Retorna um Cliente, limitado ao PedidoCommand
                        return new Cliente(
                            rs.getInt("idUsuario"), 
                            rs.getString("nome"), 
                            rs.getString("cpf")
                        );
                    }
                }
            }
        }
        return null; // Retorna null se as credenciais não baterem
    }

    public void salvar(String nome, String email, String senha, String tipo, String cpf, String telefone, String cargo) throws SQLException {
        String sql = "INSERT INTO Usuario (nome, email, senha, tipoUsuario, cpf, telefone, cargo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, tipo);
            stmt.setString(5, cpf);
            stmt.setString(6, telefone);
            stmt.setString(7, cargo);
            stmt.executeUpdate();
        }
    }
}