<%@page import="Model.Pedido.Pedido"%>
<%@page import="Model.Pedido.PedidoDAO"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="Model.Carrinho.Carrinho"%>
<%@page import="java.util.*"%>
<%@page import="Model.Cliente.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%   Cliente auth = (Cliente) request.getSession().getAttribute("auth");   %>
<%
    DecimalFormat formatacao = new DecimalFormat("#.##");
    request.setAttribute("formatacao", formatacao);

    List<Pedido> pedidos = null;

    if (auth != null) {
        request.setAttribute("auth", auth);
        pedidos = new PedidoDAO().listarPedidos(auth.getCpf());

    } else {
        response.sendRedirect("index.jsp");
    }

    ArrayList<Carrinho> listaCarrinho = (ArrayList<Carrinho>) request.getSession().getAttribute("cart-list");
    if (listaCarrinho != null) {
        request.setAttribute("cart_list", listaCarrinho);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <title>Pedidos</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container">
                <div class="container-fluid">
                    <a class="navbar-brand" href="index.jsp">E-commerce</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto ml-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="produtos.jsp">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="carrinho.jsp"
                                   >Carrinho<span class="badge badge-danger ml-2">${cart_list.size()}</span>
                                </a>
                            </li>
                            <%    if (auth != null) {   %>
                            <li class="nav-item">
                                <a class="nav-link" href="pedidos.jsp">Pedidos</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="LogoutServlet">LogOut</a>
                            </li>
                            <%  } else {  %>
                            <li class="nav-item">
                                <a class="nav-link" href="index.jsp">LogIn</a>
                            </li>
                            <%  }%>

                        </ul>

                    </div>
                </div>
            </div>
        </nav>

        <div class="container"> 
            <div class="card-header my-3">Pedidos</div>
            <table class="table table-light table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">Data</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Descrição</th>
                        <th scope="col">Quantidade</th>
                        <th scope="col">Preço</th>
                        <th scope="col">Cancelar</th>
                    </tr>
                </thead>
                <tbody>
                    <%

                        if (pedidos != null) {
                            for (Pedido order : pedidos) {%>
                    <tr>
                        <td><%= order.getData()%></td>
                        <td><%= order.getNome()%></td>
                        <td><%= order.getDesc()%></td>
                        <td><%= order.getQnt()%></td>
                        <td><%= order.getPreco()%></td>
                        <td><a class="btn btn-sm btn-danger" href="CancelarServlet?id=<%= order.getId()%>">Cancelar</a></td>
                    </tr>
                    <%      }
                        }
                    %>
                </tbody>
            </table>
        </div>

    </body>
</html>
