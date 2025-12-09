package ferreteria.repository;

import ferreteria.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioPedidoRepository extends JpaRepository<Pedido, Integer> {
}
