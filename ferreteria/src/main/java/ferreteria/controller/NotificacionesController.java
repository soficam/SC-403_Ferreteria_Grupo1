/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

import ferreteria.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import java.util.List;

/**
 *
 * @author sofic
 */
@ControllerAdvice
public class NotificacionesController {
    @Autowired
    private ProductoService productoService;

    @ModelAttribute
    public void cargarNotificacionesGlobales(Model model) {

        var bajas = productoService.getProductosPorAgotarse();

        model.addAttribute("notificacionesBajas", bajas);
        model.addAttribute("totalNotificaciones",
                (bajas != null) ? bajas.size() : 0);
    }
}