package command;

import dao.ManutencaoDAO;

public class FinalizarManutencaoCommand implements Command {
    private final int idManutencao;
    private final int idCarro;
    private final ManutencaoDAO manutencaoDAO;

    public FinalizarManutencaoCommand(int idManutencao, int idCarro) {
        this.idManutencao = idManutencao;
        this.idCarro = idCarro;
        this.manutencaoDAO = new ManutencaoDAO();
    }

    @Override
    public void executar() throws Exception {
        // Chama o DAO que faz o update na Manutencao (finalizada=true, revisao=true)
        // E no Carro (status='Disponível')
        manutencaoDAO.finalizarManutencao(idManutencao, idCarro);
    }
}