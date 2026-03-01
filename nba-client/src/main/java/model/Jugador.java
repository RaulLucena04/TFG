package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora cualquier campo extra en JSON
public class Jugador {

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty posicion = new SimpleStringProperty();
    private final IntegerProperty numero = new SimpleIntegerProperty();

    @JsonProperty("promedioPuntos") // JSON "promedioPuntos" -> ppg
    private final DoubleProperty ppg = new SimpleDoubleProperty();

    @JsonProperty("promedioRebotes") // JSON "promedioRebotes" -> rpg
    private final DoubleProperty rpg = new SimpleDoubleProperty();

    @JsonProperty("promedioAsistencias") // JSON "promedioAsistencias" -> apg
    private final DoubleProperty apg = new SimpleDoubleProperty();

    @JsonProperty("equipo") // JSON "equipo" -> objeto Equipo
    private Equipo equipo;

    // Constructor vacío (necesario para Jackson)
    public Jugador() {
    }

    // Getters y setters JavaFX
    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getPosicion() {
        return posicion.get();
    }

    public void setPosicion(String posicion) {
        this.posicion.set(posicion);
    }

    public StringProperty posicionProperty() {
        return posicion;
    }

    public int getNumero() {
        return numero.get();
    }

    public void setNumero(int numero) {
        this.numero.set(numero);
    }

    public IntegerProperty numeroProperty() {
        return numero;
    }

    public double getPpg() {
        return ppg.get();
    }

    public void setPpg(double ppg) {
        this.ppg.set(ppg);
    }

    public DoubleProperty ppgProperty() {
        return ppg;
    }

    public double getRpg() {
        return rpg.get();
    }

    public void setRpg(double rpg) {
        this.rpg.set(rpg);
    }

    public DoubleProperty rpgProperty() {
        return rpg;
    }

    public double getApg() {
        return apg.get();
    }

    public void setApg(double apg) {
        this.apg.set(apg);
    }

    public DoubleProperty apgProperty() {
        return apg;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getEquipoNombre() {
        return equipo != null ? equipo.getNombre() : "";
    }

}