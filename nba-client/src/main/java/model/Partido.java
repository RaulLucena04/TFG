package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modelo de partido usado por el cliente JavaFX.
 *
 * <p>El backend representa la fecha como {@link LocalDateTime}; el cliente expone además
 * {@link #getFechaSoloDia()} para casos de filtrado por día.</p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Partido {

    private final LongProperty id = new SimpleLongProperty();

    @JsonProperty("fecha")
    private final ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<>();

    @JsonProperty("equipoLocal")
    private Equipo equipoLocal;

    @JsonProperty("equipoVisitante")
    private Equipo equipoVisitante;

    @JsonProperty("puntosLocal")
    private final IntegerProperty puntosLocal = new SimpleIntegerProperty();

    @JsonProperty("puntosVisitante")
    private final IntegerProperty puntosVisitante = new SimpleIntegerProperty();

    // NUEVO: campo estado para filtrar partidos PROGRAMADO
    @JsonProperty("estado")
    private String estado;

    // Constructor vacío
    public Partido() {}

    // Getters y setters JavaFX
    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    public LocalDateTime getFecha() { return fecha.get(); }
    public void setFecha(LocalDateTime fecha) { this.fecha.set(fecha); }
    public ObjectProperty<LocalDateTime> fechaProperty() { return fecha; }

    @JsonIgnore
    public LocalDate getFechaSoloDia() {
        return getFecha() != null ? getFecha().toLocalDate() : null;
    }

    public Equipo getEquipoLocal() { return equipoLocal; }
    public void setEquipoLocal(Equipo equipoLocal) { this.equipoLocal = equipoLocal; }

    public Equipo getEquipoVisitante() { return equipoVisitante; }
    public void setEquipoVisitante(Equipo equipoVisitante) { this.equipoVisitante = equipoVisitante; }

    public int getPuntosLocal() { return puntosLocal.get(); }
    public void setPuntosLocal(int puntosLocal) { this.puntosLocal.set(puntosLocal); }
    public IntegerProperty puntosLocalProperty() { return puntosLocal; }

    public int getPuntosVisitante() { return puntosVisitante.get(); }
    public void setPuntosVisitante(int puntosVisitante) { this.puntosVisitante.set(puntosVisitante); }
    public IntegerProperty puntosVisitanteProperty() { return puntosVisitante; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Método auxiliar para mostrar el rival según el equipo actual
    public String getRival(Long equipoId) {
        if (equipoLocal != null && equipoVisitante != null) {
            return equipoLocal.getId().equals(equipoId)
                    ? equipoVisitante.getNombre()
                    : equipoLocal.getNombre();
        }
        return "Desconocido";
    }

    // Método auxiliar para mostrar el resultado
    public String getResultado(Long equipoId) {
        if (equipoLocal != null && equipoVisitante != null) {
            int puntosEquipo = equipoLocal.getId().equals(equipoId) ? getPuntosLocal() : getPuntosVisitante();
            int puntosRival = equipoLocal.getId().equals(equipoId) ? getPuntosVisitante() : getPuntosLocal();
            return puntosEquipo + " - " + puntosRival;
        }
        return "N/A";
    }
}