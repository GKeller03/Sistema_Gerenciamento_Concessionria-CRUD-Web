package command;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginCommand implements Command {
    private String email;
    private String senha;
    private Usuario usuarioAutenticado;

    public LoginCommand(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    @Override
    public void executar() throws Exception {
        UsuarioDAO dao = new UsuarioDAO();
        this.usuarioAutenticado = dao.autenticar(email, senha);
        
        if (this.usuarioAutenticado == null) {
            throw new Exception("Credenciais inválidas!"); // RN08 [cite: 16]
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}