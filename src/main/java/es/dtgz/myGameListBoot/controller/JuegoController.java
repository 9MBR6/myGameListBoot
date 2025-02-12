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

/**
 * Controlador de la aplicación encargado de gestionar las acciones relacionadas con los juegos.
 * Permite listar, crear, editar, eliminar juegos, así como mostrar estadísticas.
 */
@Controller
@RequestMapping("/juegos")
public class JuegoController {

    private static final Logger logger = LoggerFactory.getLogger(JuegoController.class);

    @Autowired
    private JuegoService juegoService;

    /**
     * Muestra la lista de todos los juegos.
     *
     * @param model Modelo utilizado para pasar datos a la vista
     * @return La vista con la lista de juegos
     */
    @GetMapping
    public String listJuegos(Model model) {
        logger.info("Listando todos los juegos");
        model.addAttribute("juegos", juegoService.getAllJuegos());
        return "juegos/juegos";
    }

    /**
     * Muestra el formulario para crear un nuevo juego.
     *
     * @param model Modelo utilizado para pasar datos a la vista
     * @return La vista con el formulario para agregar un juego
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        logger.info("Mostrando formulario para crear un nuevo juego");
        model.addAttribute("juego", new Juego());
        return "juegos/addJuego";
    }

    /**
     * Guarda un nuevo juego en la base de datos.
     *
     * @param juego El juego a guardar
     * @return Redirige a la lista de juegos
     */
    @PostMapping("/add")
    public String saveJuego(@ModelAttribute Juego juego) {
        logger.info("Guardando nuevo juego: {} (Nombre: {})", juego, juego.getTitulo());
        juegoService.saveJuego(juego);
        return "redirect:/juegos";
    }

    /**
     * Muestra el formulario de edición de un juego existente.
     *
     * @param id El ID del juego a editar
     * @param model Modelo utilizado para pasar datos a la vista
     * @return La vista con el formulario de edición del juego
     */
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

    /**
     * Actualiza un juego en la base de datos.
     *
     * @param id El ID del juego a actualizar
     * @param juego El objeto juego con los datos actualizados
     * @return Redirige a la lista de juegos
     */
    @PostMapping("/update/{id}")
    public String updateJuego(@PathVariable Long id, @ModelAttribute Juego juego) {
        logger.info("Actualizando juego con ID: {}", id);
        juego.setId(id);
        juegoService.saveJuego(juego);
        return "redirect:/juegos";
    }

    /**
     * Elimina un juego de la base de datos.
     *
     * @param id El ID del juego a eliminar
     * @return Redirige a la lista de juegos
     */
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

    /**
     * Muestra los juegos que han sido completados.
     *
     * @param model Modelo utilizado para pasar datos a la vista
     * @return La vista con la lista de juegos completados
     */
    @GetMapping("/completados")
    public String getJuegosCompletados(Model model) {
        List<Juego> juegosCompletados = juegoService.getJuegosCompletados();
        model.addAttribute("juegos", juegosCompletados);
        return "juegos/completados"; // Nombre de la vista en Thymeleaf
    }

    /**
     * Muestra las estadísticas relacionadas con los juegos.
     * Incluye estadísticas de estado, plataforma, género, juegos pasados este año,
     * juegos por año de lanzamiento y total de horas jugadas.
     *
     * @param model Modelo utilizado para pasar datos a la vista
     * @return La vista con las estadísticas de los juegos
     * @throws JsonProcessingException Si ocurre un error al procesar los objetos a JSON
     */
    @GetMapping("/estadisticas")
    public String estadisticas(Model model) throws JsonProcessingException {
        Map<String, Long> estadisticasEstados = juegoService.obtenerEstadisticasEstados();
        Map<String, Long> estadisticasPlataformas = juegoService.obtenerEstadisticasPlataformas();
        Map<String, Long> estadisticasGenero = juegoService.obtenerEstadisticasGenero();
        long juegosPasadosEsteAnio = juegoService.obtenerJuegosPasadosEsteAnio();
        Map<Integer, Long> juegosPorAnoLanzamiento = juegoService.obtenerJuegosPorAnioLanzamiento();
        long horasMaximasJugadas = juegoService.obtenerTotalHorasJugadas();

        ObjectMapper objectMapper = new ObjectMapper();
        String estadisticasEstadosJson = objectMapper.writeValueAsString(estadisticasEstados);
        String estadisticasPlataformasJson = objectMapper.writeValueAsString(estadisticasPlataformas);
        String estadisticasGeneroJson = objectMapper.writeValueAsString(estadisticasGenero);
        String juegosPorAnoLanzamientoJson = objectMapper.writeValueAsString(juegosPorAnoLanzamiento);

        model.addAttribute("estadisticasEstados", estadisticasEstadosJson);
        model.addAttribute("estadisticasPlataformas", estadisticasPlataformasJson);
        model.addAttribute("estadisticasGenero", estadisticasGeneroJson);
        model.addAttribute("juegosPasadosEsteAnio", juegosPasadosEsteAnio);
        model.addAttribute("juegosPorAnoLanzamiento", juegosPorAnoLanzamientoJson);
        model.addAttribute("totalHorasJugadas", horasMaximasJugadas);

        return "juegos/estadisticas";
    }
}
