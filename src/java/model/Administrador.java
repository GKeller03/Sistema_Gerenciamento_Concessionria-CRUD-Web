package model;

public class Administrador extends Usuario {
    private String cargo;

    public Administrador(int id, String nome, String cargo) {
        this.idUsuario = id;
        this.nome = nome;
        this.cargo = cargo;
        this.tipoUsuario = "Administrador";
    }
}