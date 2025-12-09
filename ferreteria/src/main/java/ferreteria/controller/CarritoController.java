/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

import ferreteria.domain.CarritoItem;
import ferreteria.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@RequestMapping("/carrito")
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;

    //Mostrar carrito
    @GetMapping("/listado")
    public String verCarrito(HttpSession session, Model model) {
        List<CarritoItem> items = carritoService.getItems(session);
        model.addAttribute("items", items);
        model.addAttribute("total", carritoService.getTotal(session));
        return "carrito/listado";
    }

    //Agregar producto al carrito
    @PostMapping("/agregar")
    public String agregarProducto(@RequestParam("productoId") Long productoId,
                                  @RequestParam(name = "cantidad", defaultValue = "1") int cantidad,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        carritoService.add(session, productoId, cantidad);

        redirectAttributes.addFlashAttribute("agregado", true);

        return "redirect:/productos/detalle/" + productoId;
    }


    //Actualizar cantidad
    @PostMapping("/actualizar")
    public String actualizarCantidad(@RequestParam("productoId") Long productoId,
                                     @RequestParam("cantidad") int cantidad,
                                     HttpSession session) {
        carritoService.update(session, productoId, cantidad);
        return "redirect:/carrito/listado";
    }

    //Eliminar un producto
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("productoId") Long productoId,
                           HttpSession session) {
        carritoService.remove(session, productoId);
        return "redirect:/carrito/listado";
    }

    //Vaciar carrito
    @PostMapping("/vaciar")
    public String vaciar(HttpSession session) {
        carritoService.clear(session);
        return "redirect:/carrito/listado";
    }
    
}