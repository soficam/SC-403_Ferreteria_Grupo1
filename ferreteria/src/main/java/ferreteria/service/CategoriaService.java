package ferreteria.service;

import ferreteria.domain.Categoria;
import ferreteria.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias() {
        return categoriaRepository.findByEstadoTrue(); 
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> getCategoria(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Categoria saveOrUpdate(Categoria src) {

        if (src.getIdCategoria() != null) {
            // UPDATE
            var categoriaExistente = categoriaRepository.findById(src.getIdCategoria())
                    .orElseThrow(() -> new IllegalArgumentException("La categoría no existe."));

            categoriaExistente.setNombre(src.getNombre());
            categoriaExistente.setEstado(src.isEstado());

            return categoriaRepository.save(categoriaExistente);

        } else {
            // CREATE
            src.setEstado(true);
            return categoriaRepository.save(src);
        }
    }

    @Transactional
    public void delete(Long id) {

        var categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + id + " no existe."));

        try {
            // Eliminación lógica (igual que con productos)
            categoria.setEstado(false);
            categoriaRepository.save(categoria);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar la categoría. Tiene productos asociados.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarActivas() {
        return categoriaRepository.findByEstadoTrue();
    }
}
