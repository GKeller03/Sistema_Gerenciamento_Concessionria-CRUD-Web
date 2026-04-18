package dao;

import model.Pedido;
import util.DatabaseConnection;
import java.sql.*;

public class PedidoDAO {
    
    public void finalizarPedido(Pedido pedido) throws SQLException {
        String sqlPedido = "INSERT INTO Pedido (data, valor, idCarro, idUsuario) VALUES (?, ?, ?, ?)";
        String sqlCarro = "UPDATE Carro SET status = 'Vendido' WHERE idCarro = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Inicia transação (Importante!)

            // 1. Insere o Pedido
            try (PreparedStatement stmtP = conn.prepareStatement(sqlPedido)) {
                stmtP.setDate(1, new java.sql.Date(pedido.getData().getTime()));
                stmtP.setDouble(2, pedido.getValor());
                stmtP.setInt(3, pedido.getIdCarro());
                stmtP.setInt(4, pedido.getIdUsuario());
                stmtP.executeUpdate();
            }

            // 2. Atualiza o status do Carro (RN02)
            try (PreparedStatement stmtC = conn.prepareStatement(sqlCarro)) {
                stmtC.setInt(1, pedido.getIdCarro());
                stmtC.executeUpdate();
            }

            conn.commit(); // Finaliza com sucesso
        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Cancela tudo se der erro
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }// No seu PedidoDAO.java
public void excluirPorCarro(int idCarro) throws SQLException {
    String sql = "DELETE FROM pedido WHERE idCarro = ?";
    try (Connection conn = util.DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idCarro);
        stmt.executeUpdate();
    }
}
}