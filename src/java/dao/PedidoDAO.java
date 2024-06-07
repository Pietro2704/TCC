/**
 *
 * @author Pietro Rosolia
 */
package dao;

import Controller.DatabaseConnection.DatabaseConnector;
import Model.Pedido.Pedido;
import Model.Produto.Produto;
import dao.ProdutoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean inserirPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido (cpf_cliente, id_prod, qnt, DataPedido) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getCliente());
            stmt.setInt(2, pedido.getId());
            stmt.setInt(3, pedido.getQnt());
            stmt.setString(4, pedido.getData());
            

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedido SET qnt = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getQnt());
            stmt.setInt(2, pedido.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPedido(int idPedido) {
        String sql = "DELETE FROM pedido WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar pedido: " + e.getMessage());
            return false;
        }
    }

    public Pedido buscarPedidoPorId(int idPedido) {
        String sql = "SELECT * FROM pedido WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setCliente(rs.getString("cpf_cliente"));
                pedido.setQnt(rs.getInt("qnt"));
                pedido.setData(rs.getString("DataPedido"));
                return pedido;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido: " + e.getMessage());
            return null;
        }
    }

    public List<Pedido> listarPedidos(String id) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido where cpf_cliente = ? order by id desc";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pedido pedido = new Pedido();
                ProdutoDAO produtoDao = new ProdutoDAO();
                
                int produto_id = rs.getInt("id_prod");
                Produto produto = produtoDao.selecionarProduto(produto_id);
                
                pedido.setId(produto_id);
                pedido.setNome(produto.getNome());
                pedido.setDesc(produto.getDesc());
                pedido.setPreco(produto.getPreco()*rs.getInt("qnt"));
                pedido.setQnt(rs.getInt("qnt"));               
                pedido.setData(rs.getString("DataPedido"));
                
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos: " + e.getMessage());
        }
        return pedidos;
    }
}
