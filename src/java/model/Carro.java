package model;

import java.io.Serializable;

public class Carro implements Serializable {
    private int idCarro;
    private String modelo;
    private double preco;
    private String placa;
    private String cor;
    private int ano;
    private String status;

    // Construtor padrão necessário para algumas bibliotecas e boas práticas
    public Carro() {}

    public Carro(String modelo, double preco, String placa, String cor, int ano) {
        this.modelo = modelo;
        this.preco = preco;
        this.placa = placa;
        this.cor = cor;
        this.ano = ano;
        this.status = "Disponível";
    }

    public void validar() throws Exception {
        if (this.ano < 1000 || this.ano > 9999) {
            throw new Exception("O ano do veículo deve conter exatamente 4 dígitos.");
        }
        if (this.modelo == null || this.modelo.trim().isEmpty()) {
            throw new Exception("O modelo não pode estar vazio.");
        }
        if (this.placa == null || this.placa.trim().isEmpty()) {
            throw new Exception("A placa é obrigatória.");
        }
    }

    // Getters e Setters Completos
    public int getIdCarro() { return idCarro; }
    public void setIdCarro(int idCarro) { this.idCarro = idCarro; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}