package es.dtgz.myGameListBoot.controller;

import es.dtgz.myGameListBoot.model.Juego;
import es.dtgz.myGameListBoot.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/juegos")
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    // Mostrar lista de juegos
    @GetMapping
    public String listJuegos(Model model) {
        model.addAttribute("juegos", juegoService.getAllJuegos());
        return "juegos/juegos";  // Ahora usa juegos.html
    }

    // Mostrar formulario de creación
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("juego", new Juego());
        return "juegos/addJuego"; // Ahora usa addJuego.html
    }

    // Guardar un nuevo juego
    @PostMapping("/add")
    public String saveJuego(@ModelAttribute Juego juego) {
        juegoService.saveJuego(juego);
        return "redirect:/juegos";
    }

    // Mostrar formulario de edición
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Juego juego = juegoService.getJuegoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado"));
        model.addAttribute("juego", juego);
        return "juegos/updateJuego"; // Ahora usa updateJuego.html
    }

    // Actualizar un juego existente
    @PostMapping("/update/{id}")
    public String updateJuego(@PathVariable Long id, @ModelAttribute Juego juego) {
        juego.setId(id);
        juegoService.saveJuego(juego);
        return "redirect:/juegos";
    }

    // Eliminar un juego
    @GetMapping("/delete/{id}")
    public String deleteJuego(@PathVariable Long id) {
        juegoService.deleteJuego(id);
        return "redirect:/juegos";
    }
}
