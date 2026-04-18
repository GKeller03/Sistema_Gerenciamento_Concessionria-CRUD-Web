package controller;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "UsuarioController", urlPatterns = {"/usuario"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if ("cadastrar".equals(action)) {
            try {
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                String tipoUsuario = request.getParameter("tipoUsuario");
                String cpf = request.getParameter("cpf");
                String telefone = request.getParameter("telefone");
                String cargo = request.getParameter("cargo");

                // Validação de segurança para Clientes
                if ("Cliente".equals(tipoUsuario)) {
                    // O JS envia apenas os números, então verificamos exatamente 11
                    if (cpf == null || cpf.length() != 11) {
                        String msgErro = URLEncoder.encode("Erro: O CPF deve conter exatamente 11 números.", "UTF-8");
                        response.sendRedirect(request.getContextPath() + "/cadastro_usuario.html?erro=" + msgErro);
                        return;
                    }
                    if (telefone == null || telefone.length() < 10) {
                        String msgErro = URLEncoder.encode("Erro: Verifique o telefone informado.", "UTF-8");
                        response.sendRedirect(request.getContextPath() + "/cadastro_usuario.html?erro=" + msgErro);
                        return;
                    }
                }

                UsuarioDAO dao = new UsuarioDAO();
                dao.salvar(nome, email, senha, tipoUsuario, cpf, telefone, cargo);
                
                response.sendRedirect("index.html?cadastro=sucesso");
                
            } catch (Exception e) {
                String msgErro = URLEncoder.encode("Erro no servidor: " + e.getMessage(), "UTF-8");
                response.sendRedirect(request.getContextPath() + "/cadastro_usuario.html?erro=" + msgErro);
            }
        }
    }
}