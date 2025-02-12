package es.dtgz.myGameListBoot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtgz.myGameListBoot.model.Juego;
import es.dtgz.myGameListBoot.service.JuegoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/juegos")
public class JuegoController {

    private static final Logger logger = LoggerFactory.getLogger(JuegoController.class);

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    public String listJuegos(Model model) {
        logger.info("Listando todos los juegos");
        model.addAttribute("juegos", juegoService.getAllJuegos());
        return "juegos/juegos";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        logger.info("Mostrando formulario para crear un nuevo juego");
        model.addAttribute("juego", new Juego());
        return "juegos/addJuego";
    }

    @PostMapping("/add")
    public String saveJuego(@ModelAttribute Juego juego) {
        logger.info("Guardando nuevo juego: {} (Nombre: {})", juego, juego.getTitulo());
        juegoService.saveJuego(juego);
        return "redirect:/juegos";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        logger.info("Mostrando formulario de edición para el juego con ID: {}", id);
        Juego juego = juegoService.getJuegoById(id)
                .orElseThrow(() -> {
                    logger.error("Juego con ID {} no encontrado", id);
                    return new IllegalArgumentException("Juego no encontrado");
                });
        model.addAttribute("juego", juego);
        return "juegos/updateJuego";
    }

    @PostMapping("/update/{id}")
    public String updateJuego(@PathVariable Long id, @ModelAttribute Juego juego) {
        logger.info("Actualizando juego con ID: {}", id);
        juego.setId(id);
        juegoService.saveJuego(juego);
        return "redirect:/juegos";
    }

    @GetMapping("/delete/{id}")
    public String deleteJuego(@PathVariable Long id) {
        juegoService.getJuegoById(id).ifPresentOrElse(
                juego -> {
                    logger.info("Eliminando juego: {} (ID: {})", juego.getTitulo(), id);
                    juegoService.deleteJuego(id);
                },
                () -> logger.warn("Intento de eliminar juego con ID {} pero no fue encontrado", id)
        );
        return "redirect:/juegos";
    }

    @GetMapping("/completados")
    public String getJuegosCompletados(Model model) {
        List<Juego> juegosCompletados = juegoService.getJuegosCompletados();
        model.addAttribute("juegos", juegosCompletados);
        return "juegos/completados"; // Nombre de la vista en Thymeleaf
    }

    @GetMapping("/estadisticas")
    public String estadisticas(Model model) throws JsonProcessingException {
        Map<String, Long> estadisticasEstados = juegoService.obtenerEstadisticasEstados();
        Map<String, Long> estadisticasPlataformas = juegoService.obtenerEstadisticasPlataformas();
        Map<String, Long> estadisticasGenero = juegoService.obtenerEstadisticasGenero();
        long juegosPasadosEsteAnio = juegoService.obtenerJuegosPasadosEsteAnio(); // ✅ Nueva estadística

        // Convertir mapas a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String estadisticasEstadosJson = objectMapper.writeValueAsString(estadisticasEstados);
        String estadisticasPlataformasJson = objectMapper.writeValueAsString(estadisticasPlataformas);
        String estadisticasGeneroJson = objectMapper.writeValueAsString(estadisticasGenero);

        // Debug: Imprimir las estadísticas para verificar
        System.out.println("Estadísticas de Estados: " + estadisticasEstados);
        System.out.println("Estadísticas de Plataformas: " + estadisticasPlataformas);
        System.out.println("Estadísticas de Género: " + estadisticasGenero);
        System.out.println("Juegos Pasados este Año: " + juegosPasadosEsteAnio);

        // Pasar los datos JSON a Thymeleaf
        model.addAttribute("estadisticasEstados", estadisticasEstadosJson);
        model.addAttribute("estadisticasPlataformas", estadisticasPlataformasJson);
        model.addAttribute("estadisticasGenero", estadisticasGeneroJson);
        model.addAttribute("juegosPasadosEsteAnio", juegosPasadosEsteAnio); // ✅ Enviar a la vista

        return "juegos/estadisticas";
    }




}
