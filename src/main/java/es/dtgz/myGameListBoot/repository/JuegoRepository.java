package es.dtgz.myGameListBoot.repository;

import es.dtgz.myGameListBoot.model.EstadoJuego;
import es.dtgz.myGameListBoot.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repositorio que maneja las operaciones de acceso a la base de datos para la entidad Juego.
 * Extiende JpaRepository, lo que proporciona funcionalidades CRUD básicas.
 */
@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    /**
     * Busca todos los juegos que tienen un estado específico.
     *
     * @param estado El estado del juego (por ejemplo, COMPLETADO, EN_PROGRESO, etc.)
     * @return Lista de juegos con el estado proporcionado
     */
    List<Juego> findByEstado(EstadoJuego estado);

    /**
     * Cuenta la cantidad de juegos cuya fecha de finalización está dentro del rango del año proporcionado.
     *
     * @param inicioAnio La fecha de inicio del rango (inicio del año)
     * @param finAnio La fecha de fin del rango (final del año)
     * @return La cantidad de juegos completados en el rango de fechas
     */
    @Query("SELECT COUNT(j) FROM Juego j WHERE j.fechaFin BETWEEN :inicioAnio AND :finAnio")
    long countByFechaFinBetween(@Param("inicioAnio") Date inicioAnio, @Param("finAnio") Date finAnio);
}
