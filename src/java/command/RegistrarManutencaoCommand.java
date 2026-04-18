package command;

import dao.ManutencaoDAO;
import model.Manutencao;

public class RegistrarManutencaoCommand implements Command {
    private final Manutencao manutencao;
    private final ManutencaoDAO dao;

    public RegistrarManutencaoCommand(Manutencao m) {
        this.manutencao = m;
        this.dao = new ManutencaoDAO();
    }

    @Override
    public void executar() throws Exception {
        dao.registrarEntrada(manutencao);
    }
}