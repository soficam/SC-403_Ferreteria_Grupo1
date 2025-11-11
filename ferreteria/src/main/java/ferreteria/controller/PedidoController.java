package ferreteria.controller;

import ferreteria.domain.Pedido;
import ferreteria.service.PedidoService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "pedido/listadoP";
    }

    @GetMapping("/editar/{id}")
    public String editarPedido(@PathVariable Integer id, Model model) {
        model.addAttribute("pedido", pedidoService.buscarPorId(id).orElse(new Pedido()));
        return "pedido/formulario";
    }

    @PostMapping("/guardar")
public String guardarPedido(@ModelAttribute Pedido pedido) {
    if (pedido.getFecha() == null) {
        pedido.setFecha(LocalDateTime.now());
    }
    pedidoService.guardar(pedido);
    return "pedido/confirmacion";
}

}
