package model;

import java.util.Date;

public class Pedido {
    private int idPedido;
    private Date data;
    private double valor;
    private int idCarro;
    private int idUsuario;

    public Pedido(double valor, int idCarro, int idUsuario) {
        this.data = new Date(); // Data atual
        this.valor = valor;
        this.idCarro = idCarro;
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public Date getData() { return data; }
    public double getValor() { return valor; }
    public int getIdCarro() { return idCarro; }
    public int getIdUsuario() { return idUsuario; }
}