/**
 *
 * @author Pietro Rosolia
 */
package dao;

import Controller.DatabaseConnection.DatabaseConnector;
import Model.Cliente.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public boolean inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (cpf, nome, email) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            return false;
        }
    }
    
    public boolean atualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, email = ? WHERE cpf = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCpf());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deletarCliente(String cpf) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }
    
    public Cliente buscarClientePorCpf(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
            }else{
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }
    
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }
}
