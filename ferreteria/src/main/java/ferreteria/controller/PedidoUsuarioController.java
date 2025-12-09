package ferreteria.controller;

import ferreteria.domain.Pedido;
import ferreteria.service.UsuarioPedidoService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarioPedidos")
public class PedidoUsuarioController {

    @Autowired
    private UsuarioPedidoService usuarioPedidoService;


    @GetMapping
    public String listarPedidosUsuario(Model model) {
        List<Pedido> pedidos = usuarioPedidoService.listarTodos();
        model.addAttribute("pedidos", pedidos);
        return "usuarioPedido/listado";
    }

    /*
    @GetMapping("/detalle/{id}")
    public String detallePedido(@PathVariable Integer id, Model model) {
        Pedido pedido = usuarioPedidoService.buscarPorId(id).orElse(new Pedido());
        model.addAttribute("pedido", pedido);
        return "usuarioPedido/detalle";
    }
    */
    @GetMapping("/detalle/{id}")
    public String detallePedido(@PathVariable Integer id, Model model) {

        Pedido pedido = usuarioPedidoService.buscarPorId(id).orElse(new Pedido());

        var detalles = usuarioPedidoService.obtenerDetallesPorPedido(id);

        var totalPedido = usuarioPedidoService.calcularTotal(detalles);

        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", detalles);
        model.addAttribute("totalPedido", totalPedido);

        return "usuarioPedido/detalle";
    }

}
