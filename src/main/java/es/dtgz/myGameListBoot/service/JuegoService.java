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

@Service
public class JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> getAllJuegos() {
        return juegoRepository.findAll();
    }

    public List<Juego> getJuegosCompletados() {
        return juegoRepository.findByEstado(EstadoJuego.COMPLETADO);
    }
    public Optional<Juego> getJuegoById(Long id) {
        return juegoRepository.findById(id);
    }

    public Juego saveJuego(Juego moto) {
        return juegoRepository.save(moto);
    }

    public void deleteJuego(Long id) {
        juegoRepository.deleteById(id);
    }

    public Map<String, Long> obtenerEstadisticasEstados() {
        List<Juego> juegos = juegoRepository.findAll();
        return juegos.stream()
                .collect(Collectors.groupingBy(j -> j.getEstado().name(), Collectors.counting()));
    }

    public Map<String, Long> obtenerEstadisticasPlataformas() {
        List<Juego> juegos = juegoRepository.findAll();
        return juegos.stream()
                .collect(Collectors.groupingBy(Juego::getPlataforma, Collectors.counting()));
    }

    public Map<String, Long> obtenerEstadisticasGenero() {
        List<Juego> juegos = juegoRepository.findAll();
        return juegos.stream()
                .collect(Collectors.groupingBy(Juego::getGenero, Collectors.counting()));
    }



    public long obtenerJuegosPasadosEsteAnio() {
        LocalDate inicioAnio = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate finAnio = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        Date inicio = Date.from(inicioAnio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(finAnio.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return juegoRepository.countByFechaFinBetween(inicio, fin);
    }


}
