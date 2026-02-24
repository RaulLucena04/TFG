package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Partido {

    private final LongProperty id = new SimpleLongProperty();

    @JsonProperty("fecha")
    private final ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();

    @JsonProperty("equipoLocal")
    private Equipo equipoLocal;

    @JsonProperty("equipoVisitante")
    private Equipo equipoVisitante;

    @JsonProperty("puntosLocal")
    private final IntegerProperty puntosLocal = new SimpleIntegerProperty();

    @JsonProperty("puntosVisitante")
    private final IntegerProperty puntosVisitante = new SimpleIntegerProperty();

    // Constructor vacío
    public Partido() {}

    // Getters y setters JavaFX
    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    public LocalDate getFecha() { return fecha.get(); }
    public void setFecha(LocalDate fecha) { this.fecha.set(fecha); }
    public ObjectProperty<LocalDate> fechaProperty() { return fecha; }

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