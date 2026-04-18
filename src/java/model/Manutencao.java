package model;
import java.sql.Date;

public class Manutencao {
    private int idManutencao;
    private Date data;
    private Date dataSaida; // Novo campo para o histórico
    private String descricao;
    private boolean revisaoObrigatoria;
    private int idCarro;
    private int idAdministrador;
    private boolean finalizada;

    public Manutencao() {}

    // Construtor para automação (Entrada na Oficina)
    public Manutencao(Date data, String descricao, int idCarro, int idAdm) {
        this.data = data;
        this.descricao = (descricao == null || descricao.isEmpty()) ? "Manutenção iniciada via sistema" : descricao;
        this.revisaoObrigatoria = false; 
        this.idCarro = idCarro;
        this.idAdministrador = idAdm;
        this.finalizada = false;
    }

    // Getters e Setters
    public int getIdManutencao() { return idManutencao; }
    public void setIdManutencao(int idManutencao) { this.idManutencao = idManutencao; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Date getDataSaida() { return dataSaida; }
    public void setDataSaida(Date dataSaida) { this.dataSaida = dataSaida; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public boolean isRevisaoObrigatoria() { return revisaoObrigatoria; }
    public void setRevisaoObrigatoria(boolean revisaoObrigatoria) { this.revisaoObrigatoria = revisaoObrigatoria; }

    public int getIdCarro() { return idCarro; }
    public void setIdCarro(int idCarro) { this.idCarro = idCarro; }

    public int getIdAdministrador() { return idAdministrador; }
    public void setIdAdministrador(int idAdministrador) { this.idAdministrador = idAdministrador; }

    public boolean isFinalizada() { return finalizada; }
    public void setFinalizada(boolean finalizada) { this.finalizada = finalizada; }
}