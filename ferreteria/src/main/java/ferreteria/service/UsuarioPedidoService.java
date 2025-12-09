package ferreteria.service;

import ferreteria.domain.Pedido;
import ferreteria.domain.DetallePedido;
import ferreteria.repository.UsuarioPedidoRepository;
import ferreteria.repository.DetallePedidoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPedidoService {

    @Autowired
    private UsuarioPedidoRepository usuarioPedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<Pedido> listarTodos() {
        return usuarioPedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Integer id) {
        return usuarioPedidoRepository.findById(id);
    }

    public List<DetallePedido> obtenerDetallesPorPedido(Integer idPedido) {
        return detallePedidoRepository.findByPedido_IdPedido(idPedido);
    }

    public BigDecimal calcularTotal(List<DetallePedido> detalles) {
        return detalles.stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
