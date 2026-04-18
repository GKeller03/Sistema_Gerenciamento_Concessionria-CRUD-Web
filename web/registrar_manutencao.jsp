<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Validação de sessão e nível de acesso (Somente ADM)
    Usuario user = (Usuario) session.getAttribute("usuarioLogado");
    if (user == null || !"Administrador".equals(user.getTipoUsuario())) {
        response.sendRedirect("dashboard.jsp?erro=Acesso negado");
        return;
    }

    // Pega os dados passados pelo botão da lista_carros.jsp
    String idCarro = request.getParameter("id");
    String modelo = request.getParameter("modelo");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Entrada na Oficina</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f7f6; display: flex; justify-content: center; padding: 50px; }
        .card { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 450px; }
        h3 { color: #fd7e14; margin-top: 0; }
        label { font-weight: bold; display: block; margin-top: 15px; font-size: 0.9em; }
        input[type="text"], input[type="date"], textarea { 
            width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; 
        }
        .checkbox-group { margin-top: 15px; display: flex; align-items: center; gap: 10px; }
        button { 
            width: 100%; padding: 12px; background-color: #fd7e14; color: white; border: none; 
            border-radius: 5px; cursor: pointer; font-weight: bold; margin-top: 20px; transition: 0.3s;
        }
        button:hover { background-color: #e66a00; }
        .info-carro { background: #fff3e6; padding: 10px; border-radius: 5px; border-left: 5px solid #fd7e14; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="card">
        <h3>🛠️ Registro de Manutenção</h3>
        
        <div class="info-carro">
            <strong>Veículo:</strong> <%= (modelo != null) ? modelo : "Não identificado" %> <br>
            <strong>ID do Sistema:</strong> #<%= idCarro %>
        </div>

        <form action="api/manutencao" method="POST">
            <input type="hidden" name="action" value="registrar">
            <input type="hidden" name="idCarro" value="<%= idCarro %>">
            <input type="hidden" name="idAdministrador" value="<%= user.getIdUsuario() %>">

            <label>Data de Entrada:</label>
            <input type="date" name="data" required value="<%= new java.sql.Date(System.currentTimeMillis()) %>">

            <label>Descrição do Problema/Serviço:</label>
            <textarea name="descricao" rows="4" placeholder="Ex: Troca de óleo, revisão de freios..." required></textarea>

            <div class="checkbox-group">
                <input type="checkbox" name="revisaoObrigatoria" id="revisao">
                <label for="revisao" style="margin: 0;">É uma revisão obrigatória?</label>
            </div>

            <button type="submit">Confirmar Entrada na Oficina</button>
        </form>
        
        <p style="text-align: center;">
            <a href="api/carro" style="font-size: 0.9em; color: #666; text-decoration: none;">← Cancelar e Voltar</a>
        </p>
    </div>
</body>
</html>