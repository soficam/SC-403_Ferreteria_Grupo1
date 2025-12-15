package ferreteria.controller;

import ferreteria.domain.Usuario;
import ferreteria.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registrarAdmins")
public class AdminController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    // Mostrar formulario para registrar un nuevo administrador
    @GetMapping("/registrarAdmin")
    public String mostrarFormularioRegistroAdmin() {
        return "registrarAdmins/registrarAdmin"; 
    }
    
    // Guardar el nuevo administrador
    @PostMapping("/registrar")
    public String guardarUsuario(@RequestParam("nombre") String nombre,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 Model model) {
        
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombre);
        usuario.setCorreo(email);
        usuario.setContrasena(password);
        usuario.setRol("admin");  // Rol establecido por defecto

        usuarioService.guardarUsuario(usuario);

        // Mensaje de éxito
        model.addAttribute("mensaje", "Usuario registrado exitosamente");
        
        // Redirección al panel admin
        return "redirect:/admin";
    }
}

