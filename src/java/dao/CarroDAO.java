package dao;

import model.Carro;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public List<Carro> listarDisponiveis() throws SQLException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM Carro WHERE status = 'Disponível'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Carro c = new Carro(
                    rs.getString("modelo"), 
                    rs.getDouble("preco"),
                    rs.getString("placa"), 
                    rs.getString("cor"), 
                    rs.getInt("ano")
                );
                c.setIdCarro(rs.getInt("idCarro"));
                c.setStatus(rs.getString("status"));
                carros.add(c);
            }
        }
        return carros;
    }

    public void validarPlacaUnica(String placa) throws Exception {
        String sql = "SELECT COUNT(*) FROM Carro WHERE placa = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new Exception("Erro: A placa " + placa + " já está cadastrada.");
                }
            }
        }
    }

    public Carro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Carro WHERE idCarro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Carro c = new Carro(
                        rs.getString("modelo"),
                        rs.getDouble("preco"),
                        rs.getString("placa"),
                        rs.getString("cor"),
                        rs.getInt("ano")
                    );
                    c.setIdCarro(rs.getInt("idCarro"));
                    c.setStatus(rs.getString("status"));
                    return c;
                }
            }
        }
        return null;
    }

    public void salvar(Carro carro) throws SQLException {
        String sql = "INSERT INTO Carro (modelo, preco, placa, cor, ano, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carro.getModelo());
            stmt.setDouble(2, carro.getPreco());
            stmt.setString(3, carro.getPlaca());
            stmt.setString(4, carro.getCor());
            stmt.setInt(5, carro.getAno());
            stmt.setString(6, carro.getStatus());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Carro carro) throws SQLException {
        String sql = "UPDATE Carro SET modelo=?, preco=?, placa=?, cor=?, ano=?, status=? WHERE idCarro=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carro.getModelo());
            stmt.setDouble(2, carro.getPreco());
            stmt.setString(3, carro.getPlaca());
            stmt.setString(4, carro.getCor());
            stmt.setInt(5, carro.getAno());
            stmt.setString(6, carro.getStatus());
            stmt.setInt(7, carro.getIdCarro());
            stmt.executeUpdate();
        }
    }

    public List<Carro> listarTodos() throws SQLException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM Carro";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Carro c = new Carro(rs.getString("modelo"), rs.getDouble("preco"),
                                  rs.getString("placa"), rs.getString("cor"), rs.getInt("ano"));
                c.setIdCarro(rs.getInt("idCarro"));
                c.setStatus(rs.getString("status"));
                carros.add(c);
            }
        }
        return carros;
    }

    public void excluir(int id) throws Exception {
        String sql = "DELETE FROM Carro WHERE idCarro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            
            if (rows == 0) {
                throw new Exception("Nenhum carro encontrado com o ID: " + id);
            }
            System.out.println("DEBUG: Carro com ID " + id + " excluído.");
        } catch (SQLException e) {
            throw new Exception("Erro ao excluir no banco de dados: " + e.getMessage());
        }
    }
}