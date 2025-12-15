package ferreteria.service;

import ferreteria.domain.DetallePedido;
import ferreteria.domain.Pedido;
import ferreteria.repository.DetallePedidoRepository;
import ferreteria.repository.PedidoRepository;
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
    
    public List<DetallePedido> getProductosSubtotalDesc(){
        return detallePedidoRepository.findAllByOrderBySubtotalDesc();
    }
    
     public List<DetallePedido> getDetallePedido(){
        return detallePedidoRepository.findAll();
    }
     
     public List<DetallePedido> getProductosFechaDesc() {
        return detallePedidoRepository.findAllOrderByFechaPedidoDesc();
    }
}
