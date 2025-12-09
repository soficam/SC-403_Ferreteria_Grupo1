package ferreteria.controller;

import ferreteria.domain.Producto;
import ferreteria.service.CategoriaService;
import ferreteria.service.ProductoService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gestionar")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoriaService categoriaService;

    // ==================================================
    // CARGA NOTIFICACIONES (productos con existencias ≤ 10)
    // ==================================================
    private void cargarNotificaciones(Model model) {

        // Usamos el método del service para obtenerlos YA filtrados
        var bajas = productoService.getProductosPorAgotarse();

        model.addAttribute("notificacionesBajas", bajas);
        model.addAttribute("totalNotificaciones", bajas.size());
    }


    // ==================================================
    // LISTAR PRODUCTOS
    // ==================================================
    /*
    @GetMapping("/listadoProductos") public String listado(Model model) { 
        var productos = productoService.getProductos(); 
        model.addAttribute("productos", productos); 
        model.addAttribute("totalProductos", productos.size()); 
        
        cargarNotificaciones(model); 
        
        return "gestionar/listadoProductos"; 
    }*/
    
    @GetMapping("/listadoProductos")
    public String listado(Model model) {
        var productos = productoService.getProductos();
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        // Ya NO llamas a cargarNotificaciones aquí
        return "gestionar/listadoProductos";
    }



    // ==================================================
    // DETALLE DE PRODUCTO
    // ==================================================
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        Optional<Producto> productoOpt = productoService.getProducto(id);

        if (productoOpt.isEmpty()) {
            ra.addFlashAttribute("error",
                    messageSource.getMessage("producto.error01", null,
                            "El producto no existe.", Locale.getDefault()));
            return "redirect:/gestionar/listadoProductos";
        }

        model.addAttribute("producto", productoOpt.get());
        cargarNotificaciones(model);

        return "gestionar/detalle";
    }

    // ==================================================
    // AGREGAR PRODUCTO
    // ==================================================
    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarActivas());

        cargarNotificaciones(model);

        return "gestionar/modifica";
    }

    // ==================================================
    // GUARDAR O ACTUALIZAR
    // ==================================================
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
                          BindingResult br,
                          RedirectAttributes ra,
                          Model model) {

        if (br.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarActivas());
            cargarNotificaciones(model);
            return "gestionar/modifica";
        }

        productoService.saveOrUpdate(producto);

        ra.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null,
                        "Se realizó la actualización.", Locale.getDefault()));

        return "redirect:/gestionar/listadoProductos";
    }

    // ==================================================
    // MODIFICAR PRODUCTO
    // ==================================================
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {

        Optional<Producto> productoOpt = productoService.getProducto(id);

        if (productoOpt.isEmpty()) {
            ra.addFlashAttribute("error",
                    messageSource.getMessage("producto.error01", null,
                            "El producto no existe.", Locale.getDefault()));
            return "redirect:/gestionar/listadoProductos";
        }

        model.addAttribute("producto", productoOpt.get());
        model.addAttribute("categorias", categoriaService.listarActivas());

        cargarNotificaciones(model);

        return "gestionar/modifica";
    }

    // ==================================================
    // ELIMINAR (LÓGICO)
    // ==================================================
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, RedirectAttributes ra) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";

        try {
            Optional<Producto> productoOpt = productoService.getProducto(id);

            if (productoOpt.isEmpty()) {
                throw new IllegalArgumentException("Producto no existe");
            }

            Producto producto = productoOpt.get();
            producto.setEstado(false); // Eliminación lógica

            productoService.saveOrUpdate(producto);

        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "producto.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "producto.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "producto.error03";
        }

        ra.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null,
                        "Operación finalizada.", Locale.getDefault()));

        return "redirect:/gestionar/listadoProductos";
    }
}

