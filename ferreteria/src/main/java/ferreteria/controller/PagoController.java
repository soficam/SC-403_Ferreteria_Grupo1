/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

/**
 *
 * @author sofic
 */
import ferreteria.domain.CarritoItem;
import ferreteria.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import ferreteria.domain.Usuario;
import ferreteria.domain.CarritoItem;
import ferreteria.domain.DetallePedido;
import ferreteria.domain.Pedido;
import ferreteria.domain.Producto;
import ferreteria.service.DetallePedidoService;
import ferreteria.service.PedidoService;
import ferreteria.service.ProductoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.security.Principal;

@Controller
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private CarritoService carritoService;
    
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String mostrarPago(HttpSession session, Model model) {
        List<CarritoItem> items = carritoService.getItems(session);

        if (items == null || items.isEmpty()) {
            return "redirect:/carrito/listado";
        }

        model.addAttribute("items", items);
        model.addAttribute("total", carritoService.getTotal(session));

        return "pago/pago"; // NEW: templates/pago/pago.html
    }
    
    @PostMapping("/checkout")
    public String procesarCompra(HttpSession session,
            Principal principal,
            Model model,
            RedirectAttributes ra) {

        List<CarritoItem> items = carritoService.getItems(session);

        if (items == null || items.isEmpty()) {
            ra.addFlashAttribute("error", "El carrito está vacío.");
            return "redirect:/carrito/listado";
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioActual");

        String nombreCliente;
        if (usuario != null) {
            // Ajusta el getter según tu entidad (nombreUsuario / nombre / etc.)
            nombreCliente = usuario.getNombreUsuario();
        } else {
            nombreCliente = "Cliente invitado";
        }

        Pedido pedido = new Pedido();
        pedido.setNombreCliente(nombreCliente);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("Pendiente");

        pedido = pedidoService.guardar(pedido); // ya viene con idPedido

        List<DetallePedido> detallesGuardados = new ArrayList<>();

        for (CarritoItem item : items) {

            Producto producto = productoService.getProducto(item.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                    "Producto no encontrado: " + item.getProductoId()));

            DetallePedido det = new DetallePedido();
            det.setPedido(pedido);
            det.setProducto(producto);
            det.setCantidad(item.getCantidad());
            det.setPrecioUnitario(item.getPrecioUnitario());
            det.setSubtotal(item.getSubtotal());

            det = detallePedidoService.guardar(det);
            detallesGuardados.add(det);
        }

        BigDecimal total = carritoService.getTotal(session);

        carritoService.clear(session);

        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", detallesGuardados);
        model.addAttribute("total", total);

        return "carrito/factura";
    }
}
