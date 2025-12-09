package ferreteria.repository;

import ferreteria.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Solo categorías activas
    List<Categoria> findByEstadoTrue();
}
