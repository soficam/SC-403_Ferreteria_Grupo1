package ferreteria.repository;

import ferreteria.domain.DetallePedido;
import ferreteria.domain.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    List<DetallePedido> findByPedido(Pedido pedido);
    
    List<DetallePedido> findByPedido_IdPedido(Integer idPedido);
}
