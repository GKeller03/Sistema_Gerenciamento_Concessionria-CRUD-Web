package command;

import dao.CarroDAO;

/**
 * Comando responsável por excluir um veículo do sistema.
 * Segue o padrão Command para desacoplar a lógica do Servlet.
 */
public class ExcluirCarroCommand implements Command {
    
    private final int idCarro;
    private final CarroDAO carroDAO;

    /**
     * Construtor do Comando.
     * @param idCarro O ID do veículo que será removido no banco de dados.
     */
    public ExcluirCarroCommand(int idCarro) {
        this.idCarro = idCarro;
        this.carroDAO = new CarroDAO();
    }

    /**
     * Executa a lógica de exclusão através do DAO.
     * @throws Exception Caso ocorra algum erro de conexão ou SQL.
     */
    @Override
    public void executar() throws Exception { 
        // Chama o método que acabamos de validar na classe CarroDAO
        carroDAO.excluir(idCarro);
    }
}