package com.tfg.nbapredictor.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001BY\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u0010\u0010\"\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u000b\u0010#\u001a\u0004\u0018\u00010\rH\u00c6\u0003Jb\u0010$\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u00c6\u0001\u00a2\u0006\u0002\u0010%J\u0013\u0010&\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000e\u0010)\u001a\u00020\r2\u0006\u0010*\u001a\u00020\u0003J\u000e\u0010+\u001a\u00020\r2\u0006\u0010*\u001a\u00020\u0003J\t\u0010,\u001a\u00020\nH\u00d6\u0001J\u0006\u0010-\u001a\u00020\'J\u0006\u0010.\u001a\u00020\'J\t\u0010/\u001a\u00020\rH\u00d6\u0001R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\t\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u000b\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u001c\u0010\u001a\u00a8\u00060"}, d2 = {"Lcom/tfg/nbapredictor/model/Partido;", "", "id", "", "fecha", "Ljava/time/LocalDateTime;", "equipoLocal", "Lcom/tfg/nbapredictor/model/Equipo;", "equipoVisitante", "puntosLocal", "", "puntosVisitante", "estado", "", "(Ljava/lang/Long;Ljava/time/LocalDateTime;Lcom/tfg/nbapredictor/model/Equipo;Lcom/tfg/nbapredictor/model/Equipo;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;)V", "getEquipoLocal", "()Lcom/tfg/nbapredictor/model/Equipo;", "getEquipoVisitante", "getEstado", "()Ljava/lang/String;", "getFecha", "()Ljava/time/LocalDateTime;", "getId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getPuntosLocal", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getPuntosVisitante", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(Ljava/lang/Long;Ljava/time/LocalDateTime;Lcom/tfg/nbapredictor/model/Equipo;Lcom/tfg/nbapredictor/model/Equipo;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;)Lcom/tfg/nbapredictor/model/Partido;", "equals", "", "other", "getResultado", "equipoId", "getRival", "hashCode", "isFinalizado", "isProgramado", "toString", "app_release"})
public final class Partido {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long id = null;
    @org.jetbrains.annotations.Nullable()
    private final java.time.LocalDateTime fecha = null;
    @com.google.gson.annotations.SerializedName(value = "equipoLocal")
    @org.jetbrains.annotations.Nullable()
    private final com.tfg.nbapredictor.model.Equipo equipoLocal = null;
    @com.google.gson.annotations.SerializedName(value = "equipoVisitante")
    @org.jetbrains.annotations.Nullable()
    private final com.tfg.nbapredictor.model.Equipo equipoVisitante = null;
    @com.google.gson.annotations.SerializedName(value = "puntosLocal")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer puntosLocal = null;
    @com.google.gson.annotations.SerializedName(value = "puntosVisitante")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer puntosVisitante = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String estado = null;
    
    public Partido(@org.jetbrains.annotations.Nullable()
    java.lang.Long id, @org.jetbrains.annotations.Nullable()
    java.time.LocalDateTime fecha, @org.jetbrains.annotations.Nullable()
    com.tfg.nbapredictor.model.Equipo equipoLocal, @org.jetbrains.annotations.Nullable()
    com.tfg.nbapredictor.model.Equipo equipoVisitante, @org.jetbrains.annotations.Nullable()
    java.lang.Integer puntosLocal, @org.jetbrains.annotations.Nullable()
    java.lang.Integer puntosVisitante, @org.jetbrains.annotations.Nullable()
    java.lang.String estado) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDateTime getFecha() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.tfg.nbapredictor.model.Equipo getEquipoLocal() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.tfg.nbapredictor.model.Equipo getEquipoVisitante() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPuntosLocal() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPuntosVisitante() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEstado() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRival(long equipoId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getResultado(long equipoId) {
        return null;
    }
    
    public final boolean isProgramado() {
        return false;
    }
    
    public final boolean isFinalizado() {
        return false;
    }
    
    public Partido() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDateTime component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.tfg.nbapredictor.model.Equipo component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.tfg.nbapredictor.model.Equipo component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.tfg.nbapredictor.model.Partido copy(@org.jetbrains.annotations.Nullable()
    java.lang.Long id, @org.jetbrains.annotations.Nullable()
    java.time.LocalDateTime fecha, @org.jetbrains.annotations.Nullable()
    com.tfg.nbapredictor.model.Equipo equipoLocal, @org.jetbrains.annotations.Nullable()
    com.tfg.nbapredictor.model.Equipo equipoVisitante, @org.jetbrains.annotations.Nullable()
    java.lang.Integer puntosLocal, @org.jetbrains.annotations.Nullable()
    java.lang.Integer puntosVisitante, @org.jetbrains.annotations.Nullable()
    java.lang.String estado) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}