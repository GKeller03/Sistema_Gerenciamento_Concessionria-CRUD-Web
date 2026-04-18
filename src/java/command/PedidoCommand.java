package command;

import dao.PedidoDAO;
import dao.CarroDAO;
import model.Pedido;
import model.Carro;

public class PedidoCommand implements Command {
    private final Pedido pedido;
    private final PedidoDAO pedidoDAO;
    private final CarroDAO carroDAO;
    private String mensagemSucesso; // Variável para guardar a frase engraçada

    public PedidoCommand(Pedido pedido) {
        this.pedido = pedido;
        this.pedidoDAO = new PedidoDAO();
        this.carroDAO = new CarroDAO();
    }

    @Override
    public void executar() throws Exception {
        // RN01: Verificar se o carro ainda está disponível no banco
        Carro carro = carroDAO.buscarPorId(pedido.getIdCarro());
        
        if (carro == null || !"Disponível".equals(carro.getStatus())) {
            throw new Exception("Puxa! Alguém foi mais rápido e levou esse possante segundos antes de você clicar!");
        }

        // RN03: Validar se o valor condiz (mesmo que seja direto, é bom manter a segurança)
        if (pedido.getValor() < carro.getPreco()) {
            throw new Exception("Opa! O sistema detectou um valor abaixo da tabela. Esse desconto aí não foi autorizado pelo chefe!");
        }

        // Se passar nas regras, finaliza no banco (muda status para 'Vendido')
        pedidoDAO.finalizarPedido(pedido);

        // Prepara a mensagem personalizada conforme solicitado
        this.mensagemSucesso = "Compra realizada com sucesso! Jajá um de nossos motoristas irá até a sua casa entregar seu(ua) " 
                                + carro.getModelo() + "!";
    }

    // Getter para o Controller conseguir pegar a mensagem e mandar para o JSP
    public String getMensagemSucesso() {
        return mensagemSucesso;
    }
}