<%@page import="dao.ManutencaoDAO"%>
<%@page import="model.Manutencao"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AutoHub - Histórico de Manutenções</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
    
    <style>
        body {
            background-color: #0a0a0a;
            color: white;
            padding: 40px;
        }

        .history-container {
            max-width: 1200px;
            margin: 0 auto;
        }

        h2 {
            text-transform: uppercase;
            letter-spacing: 2px;
            font-size: 20px;
            border-bottom: 1px solid #333;
            padding-bottom: 15px;
            margin-bottom: 30px;
        }

        /* Tabela Estilo Dark */
        .history-table {
            width: 100%;
            border-collapse: collapse;
            background-color: #111;
            border-radius: 10px;
            overflow: hidden;
        }

        .history-table th {
            background-color: #333; /* Cor sólida cinza escuro da sua imagem */
            color: white;
            text-transform: uppercase;
            font-size: 13px;
            letter-spacing: 1px;
            padding: 18px;
            text-align: center;
        }

        .history-table td {
            padding: 18px;
            border-bottom: 1px solid #222;
            text-align: center;
            font-size: 14px;
            color: #ccc;
        }

        /* Destaque para as datas e IDs */
        .date-highlight {
            color: #82f011; /* Verde neon para as datas */
            font-family: monospace;
            font-weight: bold;
        }

        .car-id {
            color: white;
            font-weight: bold;
        }

        /* Badge Revisado - Verde Neon */
        .badge-revisado {
            background-color: rgba(130, 240, 17, 0.15);
            color: #82f011;
            border: 2px solid #82f011;
            padding: 6px 15px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 900;
            text-transform: uppercase;
        }

        .link-back {
            display: inline-block;
            margin-top: 30px;
            color: white;
            text-decoration: none;
            text-transform: uppercase;
            font-weight: bold;
            font-size: 13px;
            transition: color 0.3s;
        }

        .link-back:hover {
            color: var(--primary-red);
        }

        .empty-msg {
            padding: 60px;
            color: #555;
            font-style: italic;
        }
    </style>
</head>
<body>

    <div class="history-container">
        <h2>HISTÓRICO DE MANUTENÇÕES CONCLUÍDAS</h2>
        
        <table class="history-table">
            <thead>
                <tr>
                    <th>Entrada</th>
                    <th>Saída</th>
                    <th>Carro (ID)</th>
                    <th>Descrição</th>
                    <th>Revisão</th>
                    <th>Status Final</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    ManutencaoDAO dao = new ManutencaoDAO();
                    List<Manutencao> listaHistorico = dao.listarFinalizadas();
                    
                    if (listaHistorico != null && !listaHistorico.isEmpty()) {
                        for (Manutencao m : listaHistorico) { 
                %>
                <tr>
                    <td class="date-highlight"><%= m.getData() %></td>
                    <td class="date-highlight"><%= (m.getDataSaida() != null) ? m.getDataSaida() : "---" %></td>
                    <td class="car-id">#<%= m.getIdCarro() %></td>
                    <td style="text-transform: uppercase;">MANUTENÇÃO CONCLUÍDA</td>
                    <td style="color: white; font-weight: bold;">CONCLUÍDA</td>
                    <td>
                        <span class="badge-revisado">REVISADO</span>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="empty-msg">
                        🔎 Nenhum registro de manutenção finalizada foi encontrado.
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <a href="gerenciar_oficina.jsp" class="link-back">⬅ VOLTAR PARA PAINEL DE OFICINA</a>
    </div>

</body>
</html>