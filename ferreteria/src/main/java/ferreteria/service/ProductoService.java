/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return productoRepository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public Producto saveOrUpdate(Producto src) {
        // Validar categoría antes de guardar
        if (src.getCategoria() == null || src.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("Debe seleccionar una categoría válida.");
        }

        //  Buscar la categoría real en la base de datos
        var categoria = categoriaRepository.findById(src.getCategoria().getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe."));

        //  Asociar la categoría encontrada al producto
        src.setCategoria(categoria);

        if (src.getIdProducto() != null) {
            //  Modo UPDATE
            var productoExistente = productoRepository.findById(src.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("El producto no existe."));

            productoExistente.setNombre(src.getNombre());
            productoExistente.setDescripcion(src.getDescripcion());
            productoExistente.setPrecio(src.getPrecio());
            productoExistente.setExistencias(src.getExistencias());
            productoExistente.setRutaImagen(src.getRutaImagen());
            productoExistente.setEstado(src.isEstado());
            productoExistente.setCategoria(categoria); // ✅ mantener relación con categoría

            return productoRepository.save(productoExistente);
        } else {
            //  Modo CREATE
            src.setEstado(true); // activo por defecto
            return productoRepository.save(src);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
        }
        try {
            productoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el producto. Tiene datos asociados.", e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}

