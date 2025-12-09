package ferreteria.repository;

import ferreteria.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Productos activos (solo estado = true)
    List<Producto> findByEstadoTrue();

    // Buscar productos por nombre o parte del nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Productos con existencias <= límite y activos
    List<Producto> findByExistenciasLessThanEqualAndEstadoTrue(Integer existencias);
}
