package model;

public abstract class Usuario {
    protected int idUsuario;
    protected String nome;
    protected String email;
    protected String senha;
    protected String tipoUsuario;

    // Getters e validarAcesso (RN08)
    public String getTipoUsuario() { return tipoUsuario; }
    public String getNome() { return nome; }
    public int getIdUsuario() { return idUsuario; }
    
    public boolean validarAcesso() {
        return this.idUsuario > 0; // RN08: Lógica simples de sessão ativa 
    }
}