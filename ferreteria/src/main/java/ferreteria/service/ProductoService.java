package ferreteria.service;

import ferreteria.domain.Producto;
import ferreteria.repository.CategoriaRepository;
import ferreteria.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    // Límite para considerar productos por agotarse
    private static final int LIMITE_STOCK_BAJO = 10;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // ---------------------------------------------------------
    // Obtener productos activos
    // ---------------------------------------------------------
    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return productoRepository.findByEstadoTrue();
    }

    // ---------------------------------------------------------
    // Obtener un producto por ID
    // ---------------------------------------------------------
    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Long id) {
        return productoRepository.findById(id);
    }

    // ---------------------------------------------------------
    // Crear o actualizar producto
    // ---------------------------------------------------------
    @Transactional
    public Producto saveOrUpdate(Producto src) {

        // Validación: categoría seleccionada
        if (src.getCategoria() == null || src.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("Debe seleccionar una categoría válida.");
        }

        var categoria = categoriaRepository.findById(src.getCategoria().getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe."));

        src.setCategoria(categoria);

        // --------- ACTUALIZAR ---------
        if (src.getIdProducto() != null) {

            var productoExistente = productoRepository.findById(src.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("El producto no existe."));

            productoExistente.setNombre(src.getNombre());
            productoExistente.setDescripcion(src.getDescripcion());
            productoExistente.setPrecio(src.getPrecio());
            productoExistente.setExistencias(src.getExistencias());
            productoExistente.setRutaImagen(src.getRutaImagen());
            productoExistente.setEstado(src.isEstado());
            productoExistente.setCategoria(categoria);

            return productoRepository.save(productoExistente);

        } else {

            // --------- CREAR ---------
            src.setEstado(true);
            return productoRepository.save(src);
        }
    }

    // ---------------------------------------------------------
    // Eliminar producto
    // ---------------------------------------------------------
    @Transactional
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
        }

        try {
            productoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar el producto. Tiene datos asociados.", e);
        }
    }

    // ---------------------------------------------------------
    // Buscar productos por nombre
    // ---------------------------------------------------------
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // ---------------------------------------------------------
    // LISTA DE PRODUCTOS POR AGOTARSE (existencias ≤ 10)
    // ---------------------------------------------------------
    @Transactional(readOnly = true)
    public List<Producto> getProductosPorAgotarse() {
        return productoRepository.findByExistenciasLessThanEqualAndEstadoTrue(LIMITE_STOCK_BAJO);
    }
}

