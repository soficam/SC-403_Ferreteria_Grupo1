package ferreteria.repository;

import ferreteria.domain.DetallePedido;
import ferreteria.domain.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    List<DetallePedido> findByPedido(Pedido pedido);
    
    List<DetallePedido> findByPedido_IdPedido(Integer idPedido);
    
    List<DetallePedido> findAllByOrderBySubtotalDesc();
    
    @Query("SELECT d FROM DetallePedido d ORDER BY d.pedido.fecha DESC")
    List<DetallePedido> findAllOrderByFechaPedidoDesc();
}
