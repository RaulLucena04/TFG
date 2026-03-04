package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Apuesta {

    private Long id;
    private int puntosApostados;
    private String prediccion;
    private String resultado;
    private Double cuota; // Cuota de la apuesta

    private Partido partido; // equivalente a Partido
    private User usuario; // Usuario que realiza la apuesta

    public Long getId() { return id; }

    public int getPuntosApostados() { return puntosApostados; }

    public void setPuntosApostados(int puntosApostados) {
        this.puntosApostados = puntosApostados;
    }

    public String getPrediccion() { return prediccion; }

    public void setPrediccion(String prediccion) {
        this.prediccion = prediccion;
    }

    public String getResultado() { return resultado; }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Partido getPartido() { return partido; }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public User getUsuario() { return usuario; }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Double getCuota() {
        return cuota;
    }

    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }

    // Método para calcular ganancia potencial
    public double getGananciaPotencial() {
        if (cuota != null && cuota > 0) {
            return puntosApostados * cuota;
        }
        return 0;
    }

    // Métodos útiles
    public boolean isActiva() {
        return "PENDIENTE".equalsIgnoreCase(resultado);
    }

    public boolean isGanada() {
        return "GANADA".equalsIgnoreCase(resultado);
    }

    public boolean isPerdida() {
        return "PERDIDA".equalsIgnoreCase(resultado);
    }
}