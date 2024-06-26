/**
 *
 * @author Pietro Rosolia
 */
package Controller.Servlets;

import Model.Carrinho.Carrinho;
import Model.Cliente.Cliente;
import Model.Pedido.Pedido;
import dao.PedidoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PedirServlet", urlPatterns = {"/PedirServlet"})
public class PedirServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date data = new Date();

            Cliente auth = (Cliente) request.getSession().getAttribute("auth");
            if (auth != null) {

                String id_produto = request.getParameter("id");
                int qnt = Integer.parseInt(request.getParameter("qnt"));

                if (qnt <= 0) {
                    qnt = 1;
                }

                Pedido pedido = new Pedido();
                pedido.setId(Integer.parseInt(id_produto));
                pedido.setCliente(auth.getCpf());
                pedido.setQnt(qnt);
                pedido.setData(dtFormat.format(data));

                PedidoDAO pedidoDao = new PedidoDAO();
                boolean result = pedidoDao.inserirPedido(pedido);

                if (result) {
                    ArrayList<Carrinho> cart_list = (ArrayList<Carrinho>) request.getSession().getAttribute("cart-list");
                    if (cart_list != null) {
                        for (Carrinho cart : cart_list) {
                            if (cart.getId() == Integer.parseInt(id_produto)) {
                                cart_list.remove(cart_list.indexOf(cart));
                                break;
                            }
                        }
                    }
                    response.sendRedirect("pedidos.jsp");
                } else {
                    out.println("Falha ao Pedir");
                }

            } else {
                response.sendRedirect("index.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
