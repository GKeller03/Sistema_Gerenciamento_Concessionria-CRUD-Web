package controller;

import command.PedidoCommand;
import model.Pedido;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "PedidoController", urlPatterns = {"/pedido"})
public class PedidoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Usuario user = (Usuario) sessao.getAttribute("usuarioLogado");

        // RN08: Proteção básica
        if (user == null) {
            response.sendRedirect("index.html?erro=sessao");
            return;
        }

        try {
            double valor = Double.parseDouble(request.getParameter("valor"));
            int idCarro = Integer.parseInt(request.getParameter("idCarro"));
            
            // Criamos o objeto Pedido vinculando ao usuário logado
            Pedido novoPedido = new Pedido(valor, idCarro, user.getIdUsuario());
            
            // Disparamos o Command
            PedidoCommand comando = new PedidoCommand(novoPedido);
            comando.executar();

            // Sucesso: Redireciona com mensagem
            response.sendRedirect("dashboard.jsp?sucesso=venda");
            
        } catch (Exception e) {
            // Se falhar na RN01 (Disponibilidade) ou RN03 (Valor), mostra o erro
            response.sendRedirect("dashboard.jsp?erro=" + e.getMessage());
        }
    } @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        int idCarro = Integer.parseInt(request.getParameter("idCarro"));
        double valor = Double.parseDouble(request.getParameter("valor"));
        Usuario user = (Usuario) request.getSession().getAttribute("usuarioLogado");

        Pedido p = new Pedido(valor, idCarro, user.getIdUsuario());
        PedidoCommand cmd = new PedidoCommand(p);
        cmd.executar();

        // Passa a mensagem engraçada pela URL
        String msgEncodada = java.net.URLEncoder.encode(cmd.getMensagemSucesso(), "UTF-8");
        response.sendRedirect("dashboard.jsp?msg=" + msgEncodada);

    } catch (Exception e) {
        response.sendRedirect("dashboard.jsp?erro=" + e.getMessage());
    }
}
}