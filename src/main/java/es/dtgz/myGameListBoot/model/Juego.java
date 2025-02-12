package es.dtgz.myGameListBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * Representa un juego en el sistema.
 *
 * Esta clase se mapea a la tabla "juegos" en la base de datos y contiene todos los atributos
 * necesarios para almacenar información sobre un juego, como su título, plataforma, género, etc.
 * Además, se utilizan anotaciones para la validación de campos y la configuración de la persistencia.
 */
@Entity
@Table(name = "juegos")
@Getter
@Setter
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único del juego

    @NotBlank
    @Column(nullable = false, length = 255)
    private String titulo; // Título del juego

    @Column(length = 50)
    private String plataforma; // Plataforma en la que se juega (ej. PC, PS5, etc.)

    @Column(length = 50)
    private String genero; // Género del juego (ej. Aventura, Acción, etc.)

    @Column(length = 255)
    private String desarrollador; // Desarrollador del juego

    private String portadaUrl; // URL de la portada del juego

    @Lob
    private String resumen; // Resumen del juego

    @Lob
    private String reseña; // Reseña del juego

    @Enumerated(EnumType.STRING)
    private EstadoJuego estado = EstadoJuego.PENDIENTE; // Estado del juego (ej. PENDIENTE, COMPLETADO)

    @Enumerated(EnumType.STRING)
    private Dificultad dificultad = Dificultad.NORMAL; // Dificultad del juego (ej. NORMAL, DIFÍCIL)

    @Enumerated(EnumType.STRING)
    private Replayability replayability = Replayability.TAL_VEZ; // Rejugabilidad del juego

    @Min(0)
    private Integer horasJugadas = 0; // Horas jugadas en el juego

    @Min(0)
    @Max(10)
    private Integer calificacion; // Calificación del juego (de 0 a 10)

    private String recomendacion; // Recomendación sobre el juego

    private Integer saga; // Número de saga del juego (si es parte de una saga)

    private java.sql.Date fechaLanzamiento; // Fecha de lanzamiento del juego

    private java.sql.Date fechaInicio; // Fecha en la que se comenzó a jugar

    private java.sql.Date fechaFin; // Fecha en la que se terminó de jugar

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public EstadoJuego getEstado() {
        return estado;
    }

    public void setEstado(EstadoJuego estado) {
        this.estado = estado;
    }

    public Replayability getReplayability() {
        return replayability;
    }

    public void setReplayability(Replayability replayability) {
        this.replayability = replayability;
    }

    public Integer getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(Integer horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public Integer getSaga() {
        return saga;
    }

    public void setSaga(Integer saga) {
        this.saga = saga;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
