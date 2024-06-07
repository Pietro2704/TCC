<%@page import="java.text.DecimalFormat"%>
<%@page import="Model.Produto.ProdutoDAO"%>
<%@page import="Model.Carrinho.Carrinho"%>
<%@page import="java.util.*"%>
<%@page import="Model.Cliente.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DecimalFormat formatacao = new DecimalFormat("#.##");
    request.setAttribute("formatacao", formatacao);

    Cliente auth = (Cliente) request.getSession().getAttribute("auth");

    if (auth != null) {
        request.setAttribute("auth", auth);
    }

    ArrayList<Carrinho> listaCarrinho = (ArrayList<Carrinho>) request.getSession().getAttribute("cart-list");

    List<Carrinho> produtoCarrinho = null;

    if (listaCarrinho != null) {
        ProdutoDAO produtoDao = new ProdutoDAO();
        produtoCarrinho = produtoDao.selecionarProdutosCarrinho(listaCarrinho);
        double total = produtoDao.precoTotalCarrinho(listaCarrinho);
        request.setAttribute("cart_list", listaCarrinho);
        request.setAttribute("total", total);
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
        <title>Carrinho</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            .table, tbody, td{
                vertical-align: middle;
            }
            .btn-incre, .btn-decre{
                box-shadow: none;
                font-size: 25px;
            }
        </style>
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
            <div class="d-flex py-3">
                <h3>Preço Total: R$ ${ (total > 0) ? formatacao.format(total) : 0 }</h3>
                <a class="mx-3 btn btn-primary" href="EncerrarServlet">Encerrar</a>
            </div> 
            <table class="table table-light table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">Descrição</th>
                        <th scope="col">Preço</th>
                        <th scope="col">Quantidade</th>
                        <th scope="col">Cancelar</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (listaCarrinho != null) {
                            for (Carrinho cart : produtoCarrinho) {%>
                    <tr>
                        <td><%= cart.getNome()%></td>
                        <td><%= cart.getDesc()%></td>
                        <td>R$ <%= formatacao.format(cart.getPreco())%></td>
                        <td>
                            <form class="form-inline" method="post" action="PedirServlet">
                                <input type="hidden" class="form-input" name="id" value="<%= cart.getId()%>">
                                <div class="form-group d-flex justify-content-between w-50">
                                    <a class="btn btn-sm btn-decre" href="IncreDecreServlet?action=dec&id=<%= cart.getId()%>">
                                        <i class="fas fa-minus-square"></i>
                                    </a>
                                    <input type="text" class="form-control text-center mx-2" name="qnt" value="<%= cart.getQnt()%>" readonly style="width: 50px;">
                                    <a class="btn btn-sm btn-incre" href="IncreDecreServlet?action=inc&id=<%= cart.getId()%>">
                                        <i class="fas fa-plus-square"></i>
                                    </a>
                                </div>
                                <button type="submit" class="btn btn-sm btn-primary">Pagar</button>
                            </form>
                        </td>
                        <td>
                            <a class="btn btn-sm btn-danger" href="RemoveServlet?id=<%= cart.getId()%>">Remover</a>
                        </td>
                    </tr>
                    <%
                            }
                        }

                    %>

                </tbody>
            </table>
        </div>            

    </body>
</html>
