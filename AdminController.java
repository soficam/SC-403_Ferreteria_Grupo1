/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

import ferreteria.domain.Usuario;
import ferreteria.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author enano
 */

@Controller
@RequestMapping("/registrarAdmins")
public class AdminController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/registrarAdmin")
    public String mostrarFormularioRegistroAdmin() {
        return "registrarAdmins/registrarAdmin"; 
    }
    
    @PostMapping("/registrar")
    public String guardarUsuario(@RequestParam("nombre") String nombre,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             Model model){
    Usuario usuario = new Usuario();
    usuario.setNombreUsuario(nombre);
    usuario.setCorreo(email);
    usuario.setContrasena(password);
    usuario.setRol("admin"); // Rol por defecto

    usuarioService.guardarUsuario(usuario);
    model.addAttribute("mensaje", "Usuario registrado exitosamente");
    
    return "redirect:/general/fragmentosAdmin";
}
}
