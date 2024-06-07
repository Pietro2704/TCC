<%@page import="java.util.*"%>
<%@page import="Model.Carrinho.Carrinho"%>
<%@page import="Model.Carrinho.Carrinho"%>
<%@page import="Model.Cliente.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%   Cliente auth = (Cliente) request.getSession().getAttribute("auth");   %>
<%
    if (auth != null) {
        response.sendRedirect("produtos.jsp");
    }

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
        <title>Login</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    </head>
    <body>
        

        <div class="container">
            <div class="row justify-content-center mt-5">
                <div class="col-md-6">
                    <h1 class="text-center mb-4">Login</h1>
                    <form id="clienteForm" action="ClienteServlet" method="post">
                        <div class="form-group">
                            <label for="cpf">CPF</label>
                            <input type="text" class="form-control" id="cpf" name="cpf" required> 
                        </div>
                        <div class="form-group">
                            <label for="nome">Nome</label>
                            <input type="text" class="form-control" id="nome" name="nome"> 
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" name="email"> 
                        </div>
                        <input type="hidden" id="acao" name="acao">
                        <div class="row">
                            <div class="col">
                                <button class="btn btn-outline-success btn-lg w-100" type="submit" onclick="setAction('cadastrar')">Cadastrar</button>
                            </div>
                            <div class="col">
                                <button class="btn btn-outline-primary btn-lg w-100" type="submit" onclick="setAction('procurar')">Procurar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script>
            function setAction(action) {
                document.querySelector("#acao").value = action;
            }
        </script>

    </body>
</html>
