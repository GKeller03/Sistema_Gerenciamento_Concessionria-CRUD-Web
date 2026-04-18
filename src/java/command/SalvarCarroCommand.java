package command;

import dao.CarroDAO;
import model.Carro;

public class SalvarCarroCommand implements Command {
    
    private final Carro carro;
    private final CarroDAO carroDAO;

    public SalvarCarroCommand(Carro carro) {
        this.carro = carro;
        this.carroDAO = new CarroDAO();
    }

    @Override
    public void executar() throws Exception {
        carroDAO.salvar(carro);
    }
}