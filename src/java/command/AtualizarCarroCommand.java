package command;

import dao.CarroDAO;
import dao.PedidoDAO;
import dao.ManutencaoDAO;
import model.Carro;
import model.Manutencao;
import java.sql.Date;

public class AtualizarCarroCommand implements Command {
    private final Carro carro;
    private final int idUsuarioLogado; // Precisamos do ID do ADM para o registro
    private final CarroDAO carroDAO;
    private final PedidoDAO pedidoDAO;
    private final ManutencaoDAO manutencaoDAO;

    public AtualizarCarroCommand(Carro carro, int idUsuarioLogado) {
        this.carro = carro;
        this.idUsuarioLogado = idUsuarioLogado;
        this.carroDAO = new CarroDAO();
        this.pedidoDAO = new PedidoDAO();
        this.manutencaoDAO = new ManutencaoDAO();
    }

    @Override
    public void executar() throws Exception {
        Carro carroAntigo = carroDAO.buscarPorId(carro.getIdCarro());

        // 1. RN: VENDIDO -> DISPONÍVEL (Limpa pedido)
        if ("Vendido".equals(carroAntigo.getStatus()) && "Disponível".equals(carro.getStatus())) {
            pedidoDAO.excluirPorCarro(carro.getIdCarro());
        }

        // 2. RN: NOVO STATUS = MANUTENÇÃO (Cria registro de oficina)
        // Só cria se o status mudou para Manutenção agora
        if (!"Manutenção".equals(carroAntigo.getStatus()) && "Manutenção".equals(carro.getStatus())) {
            Manutencao m = new Manutencao(
                new Date(System.currentTimeMillis()), 
                "", // Descrição vazia conforme solicitado
                carro.getIdCarro(), 
                idUsuarioLogado
            );
            manutencaoDAO.registrarEntrada(m);
        }

        // 3. Atualiza o carro normalmente
        carroDAO.atualizar(carro);
    }
}