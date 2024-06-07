/**
 *
 * @author Pietro Rosolia
 */
package Controller.Servlets;

import dao.ClienteDAO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Cliente.*;


@WebServlet(name = "ClienteServlet", urlPatterns = {"/ClienteServlet"})
public class ClienteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            ClienteDAO clienteDao = new ClienteDAO();
            String acao = request.getParameter("acao");
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            
            if(acao.equals("cadastrar")){
                // Criar um objeto Cliente e definir seus atributos
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setCpf(cpf);
                cliente.setEmail(email);
                
                // Usar o ClienteDao para persistir o objeto
                boolean cadastro = clienteDao.inserirCliente(cliente);
                if(cadastro){
                    // response.getWriter().append("Cliente cadastrado com sucesso");
                    HttpSession sessao = request.getSession();
                    sessao.setAttribute("auth", cliente);
                    response.sendRedirect("produtos.jsp");
                   
                }else{
                    response.getWriter().append("Erro ao cadastrar cliente");
                }
                
            }else if(acao.equals("procurar")){
                Cliente cliente = clienteDao.buscarClientePorCpf(cpf);
                
                if(cliente != null){
                    // response.getWriter().append("Cliente encontrado");
                    HttpSession sessao = request.getSession();
                    sessao.setAttribute("auth", cliente);
                    response.sendRedirect("produtos.jsp");
                    
                }else{
                    response.getWriter().append("Cliente não encontrado");
                }
                
            }else{
                response.getWriter().append("Ação Inválida");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Lidar com as ações de cadastro e procura de clientes.";
    }

}
