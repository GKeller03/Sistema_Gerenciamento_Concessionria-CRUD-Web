package dao;

import model.Manutencao;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManutencaoDAO {

    public void registrarEntrada(Manutencao m) throws SQLException {
        String sql = "INSERT INTO Manutencao (data, descricao, revisaoObrigatoria, idCarro, idAdministrador, finalizada) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, m.getData());
                stmt.setString(2, m.getDescricao());
                stmt.setBoolean(3, m.isRevisaoObrigatoria());
                stmt.setInt(4, m.getIdCarro());
                stmt.setInt(5, m.getIdAdministrador());
                stmt.setBoolean(6, false); 
                stmt.executeUpdate();

                String sqlCarro = "UPDATE Carro SET status = 'Manutenção' WHERE idCarro = ?";
                try (PreparedStatement stmtCarro = conn.prepareStatement(sqlCarro)) {
                    stmtCarro.setInt(1, m.getIdCarro());
                    stmtCarro.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void finalizarManutencao(int idManutencao, int idCarro) throws SQLException {
        // CORREÇÃO: Adicionado data_saida = CURDATE() para salvar o dia que terminou
        String sqlManutencao = "UPDATE Manutencao SET finalizada = true, revisaoObrigatoria = true, data_saida = CURDATE() WHERE idManutencao = ?";
        String sqlCarro = "UPDATE Carro SET status = 'Disponível' WHERE idCarro = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtM = conn.prepareStatement(sqlManutencao);
                 PreparedStatement stmtC = conn.prepareStatement(sqlCarro)) {

                stmtM.setInt(1, idManutencao);
                stmtM.executeUpdate();

                stmtC.setInt(1, idCarro);
                stmtC.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public List<Manutencao> listarAtivas() throws SQLException {
        List<Manutencao> lista = new ArrayList<>();
        String sql = "SELECT * FROM Manutencao WHERE finalizada = false ORDER BY data DESC";
        return buscar(sql);
    }

    // NOVO MÉTODO: Essencial para alimentar o histórico
    public List<Manutencao> listarFinalizadas() throws SQLException {
        List<Manutencao> lista = new ArrayList<>();
        // Busca apenas quem já foi finalizado e traz a data de saída mais recente primeiro
        String sql = "SELECT * FROM Manutencao WHERE finalizada = true ORDER BY data_saida DESC";
        return buscar(sql);
    }

    // Método auxiliar para evitar repetição de código
    private List<Manutencao> buscar(String sql) throws SQLException {
        List<Manutencao> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Manutencao m = new Manutencao();
                m.setIdManutencao(rs.getInt("idManutencao"));
                m.setData(rs.getDate("data"));
                m.setDataSaida(rs.getDate("data_saida")); // Recupera a nova coluna
                m.setDescricao(rs.getString("descricao"));
                m.setRevisaoObrigatoria(rs.getBoolean("revisaoObrigatoria"));
                m.setIdCarro(rs.getInt("idCarro"));
                m.setIdAdministrador(rs.getInt("idAdministrador"));
                m.setFinalizada(rs.getBoolean("finalizada"));
                lista.add(m);
            }
        }
        return lista;
    }
}