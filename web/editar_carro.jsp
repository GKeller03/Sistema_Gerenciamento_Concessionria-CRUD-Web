<%@page import="model.Usuario"%>
<%@page import="model.Carro"%>
<%@page import="dao.CarroDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Verificação de Segurança
    Usuario user = (Usuario) session.getAttribute("usuarioLogado");
    if (user == null || !"Administrador".equals(user.getTipoUsuario())) {
        response.sendRedirect(request.getContextPath() + "/index.html");
        return;
    }

    // Recupera o ID da URL e busca o carro
    String idParam = request.getParameter("id");
    Carro carro = null;
    if (idParam != null) {
        try {
            CarroDAO dao = new CarroDAO();
            carro = dao.buscarPorId(Integer.parseInt(idParam));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/api/carro?erro=IdInvalido");
            return;
        }
    }

    if (carro == null) {
        response.sendRedirect(request.getContextPath() + "/api/carro?erro=CarroNaoEncontrado");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AutoHub - Editar Veículo</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
    
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            background-color: #0a0a0a;
        }

        .edit-container {
            background-color: #181818;
            padding: 40px;
            border-radius: 15px;
            width: 100%;
            max-width: 450px;
            text-align: center;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            position: relative;
        }

        .logo-top {
            position: absolute;
            top: -100px;
            right: -150px; /* Ajuste dependendo do tamanho da tela */
        }

        h3 {
            text-transform: uppercase;
            letter-spacing: 2px;
            margin-bottom: 30px;
            font-size: 22px;
        }

        .form-group {
            text-align: left;
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            font-size: 12px;
            color: white;
            text-transform: uppercase;
            margin-bottom: 8px;
            letter-spacing: 1px;
        }

        input, select {
            width: 100%;
            padding: 15px;
            background-color: #3d3939; /* Cor fiel à sua imagem */
            border: 1px solid #444;
            border-radius: 10px;
            color: white;
            font-size: 14px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }

        input:focus, select:focus {
            outline: none;
            border-color: var(--primary-red);
        }

        .btn-save {
            width: 100%;
            padding: 18px;
            background-color: var(--primary-red);
            color: white;
            border: none;
            border-radius: 10px;
            font-weight: 900;
            text-transform: uppercase;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
            transition: transform 0.2s, background 0.3s;
        }

        .btn-save:hover {
            background-color: #a00000;
            transform: scale(1.02);
        }

        .back-link {
            display: inline-block;
            margin-top: 25px;
            color: white;
            text-decoration: none;
            font-size: 13px;
            font-weight: bold;
        }

        .back-link:hover {
            color: var(--primary-red);
        }
        
        .logo-fixed {
            position: fixed;
            top: 20px;
            right: 20px;
            max-width: 120px;
        }
    </style>
</head>
<body>

    <img src="${pageContext.request.contextPath}/resources/img/Logo-AutoHub.png" alt="AutoHub" class="logo-fixed">

    <div class="edit-container">
        <h3>Editar Veículo #<%= carro.getIdCarro() %></h3>
        
        <form action="${pageContext.request.contextPath}/api/carro" method="POST">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="idCarro" value="<%= carro.getIdCarro() %>">
            
            <%-- Mantendo dados que não são editados para não perdê-los no objeto --%>
            <input type="hidden" name="placa" value="<%= carro.getPlaca() %>">
            <input type="hidden" name="cor" value="<%= carro.getCor() %>">
            <input type="hidden" name="ano" value="<%= carro.getAno() %>">

            <div class="form-group">
                <label>Modelo:</label>
                <input type="text" name="modelo" value="<%= carro.getModelo() %>" required>
            </div>

            <div class="form-group">
                <label>Preço (R$):</label>
                <input type="number" step="0.01" name="preco" value="<%= carro.getPreco() %>" required>
            </div>

            <div class="form-group">
                <label>Status do Veículo:</label>
                <select name="status">
                    <option value="Disponível" <%= "Disponível".equals(carro.getStatus()) ? "selected" : "" %>>Disponível</option>
                    <option value="Vendido" <%= "Vendido".equals(carro.getStatus()) ? "selected" : "" %>>Vendido</option>
                    <option value="Manutenção" <%= "Manutenção".equals(carro.getStatus()) ? "selected" : "" %>>Manutenção</option>
                </select>
            </div>

            <button type="submit" class="btn-save">Salvar Alterações</button>
        </form>
        
        <a href="${pageContext.request.contextPath}/api/carro" class="back-link">+ Cancelar e Voltar</a>
    </div>

</body>
</html>