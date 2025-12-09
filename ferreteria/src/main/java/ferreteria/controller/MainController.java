package ferreteria.controller;

import ferreteria.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private ProductoService productoService;

    // Vista principal del cliente (CATÁLOGO)
    
    @GetMapping("/inicio")
    public String vistaCliente(Model model) {

        model.addAttribute("productos", productoService.getProductos());
        model.addAttribute("productosPorAgotarse", productoService.getProductosPorAgotarse());

        return "inicio/buscar";  // catálogo
    }

    // Vista principal del Administrador
    @GetMapping("/admin")
    public String vistaAdmin(Model model) {

        model.addAttribute("productos", productoService.getProductos());
        model.addAttribute("productosPorAgotarse", productoService.getProductosPorAgotarse());

        return "inicio/inicioAdmin"; // pantalla admin
    }
}

