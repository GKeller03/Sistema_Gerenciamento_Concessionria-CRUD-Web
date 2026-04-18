package controller;

import command.LoginCommand;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    // Adicione este método GET para processar o "Sair do sistema"
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("logout".equals(action)) {
            // RN08: Invalida a sessão atual para garantir que o acesso seja bloqueado após sair
            HttpSession sessao = request.getSession(false);
            if (sessao != null) {
                sessao.invalidate();
            }
            // Redireciona para o index limpo
            response.sendRedirect(request.getContextPath() + "/index.html");
        } else {
            // Se alguém tentar acessar /login via GET sem ser logout, manda de volta
            response.sendRedirect(request.getContextPath() + "/index.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginCommand login = new LoginCommand(
                request.getParameter("email"),
                request.getParameter("senha")
            );
            
            login.executar();
            Usuario user = login.getUsuarioAutenticado();
            
            // RN08: Início da sessão válida
            HttpSession sessao = request.getSession();
            sessao.setAttribute("usuarioLogado", user);
            
            response.sendRedirect("dashboard.jsp");
        } catch (Exception e) {
            // Redireciona para o index com erro se a senha/usuário falhar
            response.sendRedirect("index.html?erro=1");
        }
    }
}