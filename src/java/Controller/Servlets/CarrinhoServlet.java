/**
 *
 * @author Pietro Rosolia
 */
package Controller.Servlets;

import Model.Carrinho.Carrinho;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CarrinhoServlet", urlPatterns = {"/CarrinhoServlet"})
public class CarrinhoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ArrayList<Carrinho> listaCarrinho = new ArrayList();

            int id = Integer.parseInt(request.getParameter("id"));

            Carrinho carrinho = new Carrinho();
            carrinho.setId(id);
            carrinho.setQnt(1);

            HttpSession sessao = request.getSession();
            ArrayList<Carrinho> cart_list = (ArrayList<Carrinho>) sessao.getAttribute("cart-list");

            if (cart_list == null) {
                listaCarrinho.add(carrinho);
                sessao.setAttribute("cart-list", listaCarrinho);
                response.sendRedirect("produtos.jsp");
            } else {
                listaCarrinho = cart_list;
                boolean existe = false;

                for (Carrinho cart : listaCarrinho) {
                    if (cart.getId() == id) {
                        existe = true;
                        out.println("<h3 style='color:crimson; text-align:center;'>Produto já existente<a href='carrinho.jsp'>Ir para carrinho</a></h3>");
                    }
                }
                if (!existe) {
                    listaCarrinho.add(carrinho);
                    response.sendRedirect("produtos.jsp");
                }
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
        return "Short description";
    }

}
