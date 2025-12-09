package ferreteria.controller;

import ferreteria.domain.Categoria;
import ferreteria.service.CategoriaService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gestionar")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/listadoCategorias")
    public String listado(Model model) {
        var categorias = categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        return "gestionar/listadoCategorias";
    }

    @GetMapping("/agregarCategoria")
    public String agregar(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "gestionar/modificaCategoria";
    }

    @PostMapping("/guardarCategoria")
    public String guardar(@Valid @ModelAttribute("categoria") Categoria categoria,
                          BindingResult br,
                          RedirectAttributes ra,
                          Model model) {

        if (br.hasErrors()) {
            return "gestionar/modificaCategoria";
        }

        categoriaService.saveOrUpdate(categoria);
        ra.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null,
                        "Se realizó la actualización.", Locale.getDefault()));
        return "redirect:/gestionar/listadoCategorias";
    }

    @GetMapping("/modificarCategoria/{id}")
    public String modificar(@PathVariable("id") Long id,
                            Model model,
                            RedirectAttributes ra) {

        Optional<Categoria> categoriaOpt = categoriaService.getCategoria(id);
        if (categoriaOpt.isEmpty()) {
            ra.addFlashAttribute("error",
                    messageSource.getMessage("categoria.error01", null,
                            "La categoría no existe.", Locale.getDefault()));
            return "redirect:/gestionar/listadoCategorias";
        }

        model.addAttribute("categoria", categoriaOpt.get());
        return "gestionar/modificaCategoria";
    }

    @PostMapping("/eliminarCategoria")
    public String eliminar(@RequestParam Long id,
                           RedirectAttributes ra) {

        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";

        try {
            Optional<Categoria> categoriaOpt = categoriaService.getCategoria(id);
            if (categoriaOpt.isEmpty()) {
                throw new IllegalArgumentException("Categoría no existe");
            }

            // Soft delete = poner estado en false
            Categoria categoria = categoriaOpt.get();
            categoria.setEstado(false);
            categoriaService.saveOrUpdate(categoria);

        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "categoria.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "categoria.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "categoria.error03";
        }

        ra.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null,
                        "Operación finalizada.", Locale.getDefault()));
        return "redirect:/gestionar/listadoCategorias";
    }
}
