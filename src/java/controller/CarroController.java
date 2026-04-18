package controller;

import command.*;
import dao.CarroDAO;
import model.Carro;
import model.Usuario;
import java.io.IOException;
import java.net.URLEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "CarroController", urlPatterns = {"/api/carro"})
public class CarroController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            validarSessao(request);
            String action = request.getParameter("action");
            String idParam = request.getParameter("id");

            // Debug para o console do NetBeans
            System.out.println("Ação: " + action + " | ID: " + idParam);

            if (("excluir".equals(action) || "delete".equals(action)) && idParam != null) {
                int id = Integer.parseInt(idParam.trim());
                new ExcluirCarroCommand(id).executar();
                
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp?msg=" + URLEncoder.encode("Veículo excluído com sucesso!", "UTF-8"));
                return;
            }

            // Listagem padrão
            ListarCarrosCommand listar = new ListarCarrosCommand();
            listar.executar();
            request.setAttribute("listaCarros", listar.getResultado());
            request.getRequestDispatcher("/lista_carros.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace(); // Importante para ver o erro real no console
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp?erro=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Carro carro = null;
        
        try {
            validarSessao(request);
            
            carro = new Carro(
                request.getParameter("modelo"),
                Double.parseDouble(request.getParameter("preco")),
                request.getParameter("placa"),
                request.getParameter("cor"),
                Integer.parseInt(request.getParameter("ano"))
            );
            
            carro.validar(); 
            
            if (!"update".equals(action)) {
                new CarroDAO().validarPlacaUnica(carro.getPlaca());
            }

            Command comando = new SalvarCarroCommand(carro);
            
            if ("update".equals(action)) {
                carro.setIdCarro(Integer.parseInt(request.getParameter("idCarro")));
                carro.setStatus(request.getParameter("status"));
                Usuario user = (Usuario) request.getSession().getAttribute("usuarioLogado");
                comando = new AtualizarCarroCommand(carro, user.getIdUsuario());
            }

            comando.executar();

            String destino = "Manutenção".equals(carro.getStatus()) ? "/gerenciar_oficina.jsp" : "/dashboard.jsp";
            response.sendRedirect(request.getContextPath() + destino + "?msg=Sucesso");

        } catch (Exception e) {
            e.printStackTrace();
            enviarErro(request, response, e, carro, action);
        }
    }

    private void enviarErro(HttpServletRequest req, HttpServletResponse res, Exception e, Carro c, String action) throws ServletException, IOException {
        req.setAttribute("erro", e.getMessage());
        if (c != null) {
            req.setAttribute("modelo", c.getModelo());
            req.setAttribute("placa", c.getPlaca());
        }
        req.setAttribute("ano", req.getParameter("ano"));
        req.setAttribute("preco", req.getParameter("preco"));
        req.setAttribute("cor", req.getParameter("cor"));
        
        String destino = "update".equals(action) ? "/editar_carro.jsp" : "/dashboard.jsp";
        req.getRequestDispatcher(destino).forward(req, res);
    }

    private void validarSessao(HttpServletRequest request) {
        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            throw new IllegalStateException("Sessão expirada. Faça login novamente.");
        }
    }
}