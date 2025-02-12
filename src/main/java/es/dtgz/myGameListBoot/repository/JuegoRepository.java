package es.dtgz.myGameListBoot.repository;

import es.dtgz.myGameListBoot.model.EstadoJuego;
import es.dtgz.myGameListBoot.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;


import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    List<Juego> findByEstado(EstadoJuego estado);


    @Query("SELECT COUNT(j) FROM Juego j WHERE j.fechaFin BETWEEN :inicioAnio AND :finAnio")
    long countByFechaFinBetween(@Param("inicioAnio") Date inicioAnio, @Param("finAnio") Date finAnio);


}
