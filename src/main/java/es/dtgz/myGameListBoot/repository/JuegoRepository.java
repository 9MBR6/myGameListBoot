package es.dtgz.myGameListBoot.repository;

import es.dtgz.myGameListBoot.model.EstadoJuego;
import es.dtgz.myGameListBoot.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    List<Juego> findByEstado(EstadoJuego estado);
}
