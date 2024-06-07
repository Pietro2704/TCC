/**
 *
 * @author Pietro Rosolia
 */
package Model.Produto;

import Controller.DatabaseConnection.DatabaseConnector;
import Model.Carrinho.Carrinho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean inserirProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, descricao, imagem) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getDesc());
            stmt.setString(4, produto.getImagem());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
            return false;
        }
    }

    public Produto selecionarProduto(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        Produto produto = null;

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setDesc(rs.getString("descricao"));
                produto.setImagem(rs.getString("imagem"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao selecionar produto: " + e.getMessage());
        }

        return produto;
    }

    public List<Produto> selecionarTodosProdutos() {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setDesc(rs.getString("descricao"));
                produto.setImagem(rs.getString("imagem"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao selecionar todos os produtos: " + e.getMessage());
        }

        return produtos;
    }

    public boolean atualizarProduto(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, descricao = ?, imagem = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getDesc());
            stmt.setString(4, produto.getImagem());
            stmt.setInt(5, produto.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
            return false;
        }
    }

    public List<Carrinho> selecionarProdutosCarrinho(ArrayList<Carrinho> listaCarrinho) {
        List<Carrinho> produtos = new ArrayList<Carrinho>();

        try {
            if (listaCarrinho.size() > 0) {
                for (Carrinho item : listaCarrinho) {
                    String sql = "SELECT * FROM produto WHERE id = ?";

                    try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, item.getId());
                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {
                            Carrinho linha = new Carrinho();
                            linha.setId(rs.getInt("id"));
                            linha.setNome(rs.getString("nome"));
                            linha.setPreco(rs.getDouble("preco") * item.getQnt());
                            linha.setDesc(rs.getString("descricao"));
                            linha.setQnt(item.getQnt());

                            produtos.add(linha);
                        }

                    } catch (SQLException e) {
                        System.err.println("Erro ao listar produtos do carrinho: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return produtos;
    }

    public double precoTotalCarrinho(ArrayList<Carrinho> listaCarrinho) {
        double soma = 0;

        try {
            
            if (listaCarrinho.size() > 0) {
                for (Carrinho item : listaCarrinho) {
                    String sql = "SELECT preco FROM produto WHERE id = ?";

                    try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1,item.getId());
                        ResultSet rs = stmt.executeQuery();
                        
                        while (rs.next()) {
                            soma += rs.getDouble("preco")*item.getQnt();
                        }

                    } catch (SQLException e) {
                        System.err.println("Erro ao somar proço total do carrinho: " + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return soma;
    }
}
