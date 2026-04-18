<%@page import="model.Usuario"%>
<%@page import="model.Carro"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario user = (Usuario) session.getAttribute("usuarioLogado");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/index.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AutoHub - Estoque</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
    
    <style>
        .header-stock {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 30px 40px; /* Aumentado o padding lateral */
            max-width: 1400px; /* Aumentado para dar mais espaço */
            margin: 0 auto;
            gap: 20px; /* CRUCIAL: Adiciona espaço entre os elementos para não colar */
        }

        .btn-return {
            color: white;
            text-decoration: none;
            text-transform: uppercase;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 10px;
            white-space: nowrap; /* Impede que o texto quebre linha */
            transition: color 0.3s;
        }
        
        .btn-return:hover {
            color: var(--primary-red);
        }

        .stock-title {
            text-transform: uppercase; 
            letter-spacing: 3px; 
            color: var(--primary-red); 
            margin: 0; 
            text-align: center;
            flex-grow: 1; /* Faz o título ocupar o centro */
        }

        .user-badge {
            text-align: right;
            min-width: 150px;
        }

        .stock-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            padding: 20px;
            max-width: 1300px;
            margin: 0 auto;
        }

        /* Mantendo os estilos dos cards anteriores... */
        .car-card {
            background-color: #181818;
            border-radius: 15px;
            padding: 25px;
            border: 1px solid #282828;
            text-align: center;
            position: relative;
        }
        .badge-status { display: inline-block; padding: 5px 15px; border-radius: 20px; font-size: 10px; font-weight: bold; text-transform: uppercase; margin-bottom: 15px; border: 1px solid; }
        .status-disponivel { color: #82f011; border-color: #82f011; }
        .status-manutencao { color: #00d2ff; border-color: #00d2ff; }
        .status-vendido { color: #ff4444; border-color: #ff4444; }
        .car-price { color: var(--primary-red); font-size: 20px; font-weight: bold; margin-bottom: 15px; }
        .car-details { color: #bbb; font-size: 14px; text-transform: uppercase; margin-bottom: 20px; border-bottom: 1px solid #333; padding-bottom: 15px;}
        .card-actions { display: flex; gap: 10px; }
        .btn-card { flex: 1; padding: 10px; border-radius: 8px; font-weight: bold; text-decoration: none; text-transform: uppercase; font-size: 12px; }
        .btn-edit-card { background-color: var(--primary-red); color: white; }
        .btn-delete-card { background-color: #333; color: white; }
    </style>
</head>
<body>

    <div class="header-stock">
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn-return">
            <span style="font-size: 24px;">⬅</span> RETORNAR
        </a>

        <h1 class="stock-title">Estoque de Veículos</h1>

        <div class="user-badge">
            <img src="${pageContext.request.contextPath}/resources/img/Logo-AutoHub.png" alt="AutoHub" style="max-width: 100px;"><br>
            <span style="font-size: 12px; color: #888;">Logado como: <strong style="color: var(--primary-red);"><%= user.getNome().toUpperCase() %></strong></span>
        </div>
    </div>

    <div class="stock-grid">
        <%
            List<Carro> lista = (List<Carro>) request.getAttribute("listaCarros");
            if (lista != null && !lista.isEmpty()) {
                for (Carro c : lista) {
                    String status = (c.getStatus() != null) ? c.getStatus() : "Disponível";
                    String classeBadge = "status-disponivel";
                    if ("Vendido".equals(status)) classeBadge = "status-vendido";
                    if ("Manutenção".equals(status)) classeBadge = "status-manutencao";
        %>
            <div class="car-card">
                <span class="badge-status <%= classeBadge %>"><%= status %></span>
                
                <div style="font-size: 22px; font-weight: bold; margin-bottom: 5px; text-transform: uppercase;"><%= c.getModelo() %></div>
                <div class="car-price">R$ <%= String.format("%,.2f", c.getPreco()) %></div>
                
                <div class="car-details">
                    PLACA: <%= c.getPlaca() %><br>
                    COR: <%= c.getCor() %><br>
                    ANO: <%= c.getAno() %>
                </div>

                <% if ("Administrador".equals(user.getTipoUsuario())) { %>
                    <div class="card-actions">
                        <a href="${pageContext.request.contextPath}/editar_carro.jsp?id=<%= c.getIdCarro() %>" class="btn-card btn-edit-card">Editar</a>
                        <a href="${pageContext.request.contextPath}/api/carro?action=excluir&id=<%= c.getIdCarro() %>" 
                           class="btn-card btn-delete-card"
                           onclick="return confirm('Deseja excluir este veículo?')">Excluir</a>
                    </div>
                <% } %>
            </div>
        <% 
                }
            } else { 
        %>
            <div style="grid-column: 1/-1; text-align: center; padding: 50px; color: #666;">
                <h2>Nenhum veículo encontrado.</h2>
            </div>
        <% } %>
    </div>

</body>
</html>