<%@page import="Model.Carrinho.Carrinho"%>
<%@page import="Model.Produto.Produto"%>
<%@page import="java.util.*"%>
<%@page import="Model.Produto.ProdutoDAO"%>
<%@page import="Model.Cliente.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%   Cliente auth = (Cliente) request.getSession().getAttribute("auth");   %>
<%
    if (auth != null) {
        request.setAttribute("auth", auth);
    }
%>
<%
    ProdutoDAO produtoDao = new ProdutoDAO();
    List<Produto> produtos = produtoDao.selecionarTodosProdutos();

    ArrayList<Carrinho> listaCarrinho = (ArrayList<Carrinho>) request.getSession().getAttribute("cart-list");
    if (listaCarrinho != null) {
        request.setAttribute("cart_list", listaCarrinho);
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <title>Produtos</title>
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
            <div class="card-header my-3">Todos os Produtos</div>
            <div class="row">
                <%
                    if (!produtos.isEmpty()) {
                        for (Produto prod : produtos) {%>
                <div class="col-md-3 my-3">
                    <div class="card w-100" style="width: 18rem; height: 100%;">
                        <img src="img/<%= prod.getImagem()%>" class="card-img-top" alt="<%= prod.getNome()%>">
                        <div class="card-body">
                            <h5 class="card-title"><%= prod.getNome()%></h5>
                            <h6 class="price">Preço: R$ <%= prod.getPreco()%></h6>
                            <h6 class="category">Descrição: <%= prod.getDesc()%></h6>
                            <div class="mt-3 d-flex justify-content-between align-items-end">
                                <a href="PedirServlet?qnt=1&id=<%= prod.getId() %>" class="btn btn-danger">Comprar</a>
                                <a href="CarrinhoServlet?id=<%= prod.getId()%>" class="btn btn-primary">Add carrinho</a>
                            </div>
                        </div>
                    </div>
                </div>
                <%}
                    }
                %>

            </div>
        </div>
    </body>
</html>