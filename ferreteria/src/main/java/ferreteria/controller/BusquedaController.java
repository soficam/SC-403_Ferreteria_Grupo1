package ferreteria.controller;

import ferreteria.domain.Producto;
import ferreteria.service.ProductoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class BusquedaController {

    @Autowired
    private ProductoService productoService;

    // Página de búsqueda
    @GetMapping("/buscar")
    public String buscar(@RequestParam(value = "nombre", required = false) String nombre, Model model) {
        List<Producto> productos;

        if (nombre != null && !nombre.isEmpty()) {
            productos = productoService.buscarPorNombre(nombre);
        } else {
            productos = productoService.getProductos();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("nombre", nombre);
        return "inicio/buscar";
    }
    
    /*
    @GetMapping("/buscar")
    public String buscar(
        @RequestParam(value = "nombre", required = false) String nombre,
        Model model) {

        boolean searchPerformed = (nombre != null && !nombre.isBlank());

        List<Producto> productos = searchPerformed
                ? productoService.buscarPorNombre(nombre)
                : productoService.getProductos();

        model.addAttribute("productos", productos);
        model.addAttribute("nombre", (nombre == null) ? "" : nombre);
        model.addAttribute("searchPerformed", searchPerformed);
        return "inicio/buscar";
    }
    */

    // Ver descripción de producto
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable("id") Long id, Model model) {
        Optional<Producto> productoOpt = productoService.getProducto(id);
        if (productoOpt.isEmpty()) {
            return "redirect:/productos/buscar";
        }
        model.addAttribute("producto", productoOpt.get());
        return "inicio/detalleProducto";
    } 
}
