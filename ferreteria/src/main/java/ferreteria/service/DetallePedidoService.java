package ferreteria.service;

import ferreteria.domain.DetallePedido;
import ferreteria.domain.Pedido;
import ferreteria.repository.DetallePedidoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Transactional
    public DetallePedido guardar(DetallePedido detalle) {
        return detallePedidoRepository.save(detalle);
    }

    @Transactional(readOnly = true)
    public List<DetallePedido> listarPorPedido(Pedido pedido) {
        return detallePedidoRepository.findByPedido(pedido);
    }
}
