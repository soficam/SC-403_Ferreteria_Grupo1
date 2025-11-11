/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/inicio/buscar")
    public String vistaCliente() {
        return "inicio/buscar";
    }

    @GetMapping("/general/fragmentosAdmin")
    public String vistaAdmin() {
        return "general/fragmentosAdmin";
    }
}
