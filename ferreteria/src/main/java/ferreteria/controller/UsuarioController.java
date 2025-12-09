/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

import ferreteria.domain.Usuario;
import ferreteria.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/landing")
    public String mostrarLanding() {
        return "login/landing"; 
    }

    @PostMapping("/entrar")
    public String procesarLogin(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 Model model,
                                 HttpSession session) {
        Usuario usuario = usuarioService.validarCredenciales(username, password);
        if (usuario != null) {
            session.setAttribute("usuarioActual", usuario);
            model.addAttribute("usuario", usuario);
            if ("admin".equalsIgnoreCase(usuario.getRol())) {
                return "redirect:/admin";
            } else {
                return "redirect:/inicio";
            }
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "login/landing";
        }
    }

    
    @PostMapping("/registrar")
    public String guardarUsuario(@RequestParam("nombre") String nombre,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             Model model) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombre);
        usuario.setCorreo(email);
        usuario.setContrasena(password);
        usuario.setRol("user"); // Rol por defecto

        usuarioService.guardarUsuario(usuario);
        model.addAttribute("mensaje", "Usuario registrado exitosamente");
        return "redirect:/login/landing";
    }

}



