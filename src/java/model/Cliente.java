package model;

import java.util.Date;

public class Cliente extends Usuario {
    private String cpf;
    private String telefone;

    // Construtor utilizado pelo UsuarioDAO
    public Cliente(int idUsuario, String nome, String cpf) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpf = cpf;
        this.tipoUsuario = "Cliente";
    }

    // RN07: Clientes só possuem permissão para disparar o PedidoCommand [cite: 14]
    public void fazerPedido() {
        // Logica para iniciar um pedido conforme o Diagrama de Frequência
        System.out.println("Cliente iniciando um pedido...");
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}