package controller;

import command.Command;
import command.RegistrarManutencaoCommand;
import command.FinalizarManutencaoCommand;
import model.Manutencao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "ManutencaoController", urlPatterns = {"/api/manutencao"})
public class ManutencaoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        // Lógica para Finalizar vinda do botão na gerenciar_oficina.jsp
        if ("finalizar".equals(action)) {
            try {
                int idM = Integer.parseInt(request.getParameter("idManutencao"));
                int idC = Integer.parseInt(request.getParameter("idCarro"));
                
                Command cmd = new FinalizarManutencaoCommand(idM, idC);
                cmd.executar();
                
                // Redireciona de volta para a tabela de gerenciamento com mensagem de sucesso
                response.sendRedirect(request.getContextPath() + "/gerenciar_oficina.jsp?msg=Manutencao concluida e carro disponivel!");
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp?erro=" + e.getMessage());
            }
            return;
        }

        // Se a ação for listar, envia direto para o JSP que criamos
        if ("list".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/gerenciar_oficina.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("registrar".equals(action)) {
            try {
                int idCarro = Integer.parseInt(request.getParameter("idCarro"));
                int idAdm = Integer.parseInt(request.getParameter("idAdministrador"));
                String descricao = request.getParameter("descricao");
                Date data = Date.valueOf(request.getParameter("data"));
                boolean revisao = request.getParameter("revisaoObrigatoria") != null;

                // Construtor ajustado para os campos obrigatórios
                Manutencao m = new Manutencao(data, descricao, idCarro, idAdm);
                m.setRevisaoObrigatoria(revisao); 

                Command cmd = new RegistrarManutencaoCommand(m);
                cmd.executar();

                // Após registrar, o usuário volta para o dashboard ou para a lista da oficina
                response.sendRedirect(request.getContextPath() + "/gerenciar_oficina.jsp?msg=Veiculo registrado na oficina!");

            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp?erro=Erro ao registrar: " + e.getMessage());
            }
        }
    }
}