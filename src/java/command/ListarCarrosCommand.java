package command;

import dao.CarroDAO;
import model.Carro;
import java.util.Collections;
import java.util.List;

public class ListarCarrosCommand implements Command {
    private final CarroDAO carroDAO;
    private List<Carro> resultado;

    public ListarCarrosCommand() {
        this.carroDAO = new CarroDAO();
    }

    @Override
    public void executar() throws Exception {
        // RN06: O método deve retornar uma lista tratada para leitura
        // Aqui buscamos do DAO e encapsulamos em uma lista não modificável
        List<Carro> listaBruta = carroDAO.listarTodos();
        this.resultado = Collections.unmodifiableList(listaBruta);
    }

    public List<Carro> getResultado() {
        return resultado;
    }
}