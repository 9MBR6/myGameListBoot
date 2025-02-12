package es.dtgz.myGameListBoot.service;

import es.dtgz.myGameListBoot.model.EstadoJuego;
import es.dtgz.myGameListBoot.model.Juego;
import es.dtgz.myGameListBoot.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los juegos.
 * Interactúa con el repositorio de juegos para obtener y manipular los datos.
 */
@Service
public class JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

    /**
     * Obtiene todos los juegos almacenados en la base de datos.
     *
     * @return Lista de juegos
     */
    public List<Juego> getAllJuegos() {
        return juegoRepository.findAll();
    }

    /**
     * Obtiene todos los juegos que están marcados como completados.
     *
     * @return Lista de juegos completados
     */
    public List<Juego> getJuegosCompletados() {
        return juegoRepository.findByEstado(EstadoJuego.COMPLETADO);
    }

    /**
     * Obtiene un juego a partir de su ID.
     *
     * @param id El ID del juego a obtener
     * @return El juego con el ID proporcionado, si existe
     */
    public Optional<Juego> getJuegoById(Long id) {
        return juegoRepository.findById(id);
    }

    /**
     * Guarda un nuevo juego o actualiza uno existente.
     *
     * @param juego El juego a guardar
     * @return El juego guardado
     */
    public Juego saveJuego(Juego juego) {
        return juegoRepository.save(juego);
    }

    /**
     * Elimina un juego a partir de su ID.
     *
     * @param id El ID del juego a eliminar
     */
    public void deleteJuego(Long id) {
        juegoRepository.deleteById(id);
    }

    /**
     * Obtiene estadísticas de los juegos agrupados por su estado (COMPLETADO, EN_PROGRESO, etc.).
     *
     * @return Un mapa con los estados como claves y el número de juegos en cada estado como valores
     */
    public Map<String, Long> obtenerEstadisticasEstados() {
        List<Juego> juegos = juegoRepository.findAll();
        return juegos.stream()
                .collect(Collectors.groupingBy(j -> j.getEstado().name(), Collectors.counting()));
    }

    /**
     * Obtiene estadísticas de los juegos agrupados por su plataforma.
     *
     * @return Un mapa con las plataformas como claves y el número de juegos en cada plataforma como valores
     */
    public Map<String, Long> obtenerEstadisticasPlataformas() {
        List<Juego> juegos = juegoRepository.findAll();
        return juegos.stream()
                .collect(Collectors.groupingBy(Juego::getPlataforma, Collectors.counting()));
    }

    /**
     * Obtiene estadísticas de los juegos agrupados por su género.
     *
     * @return Un mapa con los géneros como claves y el número de juegos en cada género como valores
     */
    public Map<String, Long> obtenerEstadisticasGenero() {
        List<Juego> juegos = juegoRepository.findAll();
        return juegos.stream()
                .collect(Collectors.groupingBy(Juego::getGenero, Collectors.counting()));
    }

    /**
     * Cuenta la cantidad de juegos completados durante el año actual.
     *
     * @return El número de juegos completados este año
     */
    public long obtenerJuegosPasadosEsteAnio() {
        LocalDate inicioAnio = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate finAnio = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        Date inicio = Date.from(inicioAnio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(finAnio.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return juegoRepository.countByFechaFinBetween(inicio, fin);
    }

    /**
     * Obtiene estadísticas de la cantidad de juegos lanzados por cada año.
     *
     * @return Un mapa con los años de lanzamiento como claves y el número de juegos lanzados en cada año como valores
     */
    public Map<Integer, Long> obtenerJuegosPorAnioLanzamiento() {
        List<Juego> juegos = juegoRepository.findAll();

        return juegos.stream()
                .filter(j -> j.getFechaLanzamiento() != null)
                .collect(Collectors.groupingBy(
                        j -> j.getFechaLanzamiento().toLocalDate().getYear(),
                        Collectors.counting()
                ));
    }

    /**
     * Calcula el total de horas jugadas de todos los juegos almacenados.
     *
     * @return El total de horas jugadas
     */
    public long obtenerTotalHorasJugadas() {
        List<Juego> juegos = juegoRepository.findAll();
        return juegos.stream()
                .mapToLong(Juego::getHorasJugadas)  // Suponiendo que 'getHorasJugadas' devuelve un long o int
                .sum();
    }
}
