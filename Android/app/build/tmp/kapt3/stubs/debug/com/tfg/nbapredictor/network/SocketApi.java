package com.tfg.nbapredictor.network;

/**
 * Cliente TCP por sockets para hablar con el backend (JSON + frames length-prefixed).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u00013B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J4\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00060\u000bH\u0082@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0015J&\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0086@\u00a2\u0006\u0002\u0010!J\u001c\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00130\u001f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0002\u0010#J\u0014\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\u001fH\u0086@\u00a2\u0006\u0002\u0010!J\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00170\u001fH\u0086@\u00a2\u0006\u0002\u0010!J\u0016\u0010*\u001a\u00020 2\u0006\u0010\u0018\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0002\u0010#J\u001e\u0010+\u001a\u00020 2\u0006\u0010,\u001a\u00020\b2\u0006\u0010-\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010.J\u0016\u0010/\u001a\u00020 2\u0006\u00100\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u00101J\u0016\u00102\u001a\u00020 2\u0006\u00100\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u00101R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/tfg/nbapredictor/network/SocketApi;", "", "()V", "gson", "Lcom/google/gson/Gson;", "call", "T", "action", "", "payload", "clazz", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Class;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "canjearPuntos", "Lcom/tfg/nbapredictor/network/CanjearPuntosResponse;", "request", "Lcom/tfg/nbapredictor/network/CanjearPuntosRequest;", "(Lcom/tfg/nbapredictor/network/CanjearPuntosRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createApuesta", "Lcom/tfg/nbapredictor/model/Apuesta;", "apuesta", "(Lcom/tfg/nbapredictor/model/Apuesta;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "finalizarPartido", "Lcom/tfg/nbapredictor/model/Partido;", "id", "", "puntosLocal", "", "puntosVisitante", "(JIILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllUsers", "", "Lcom/tfg/nbapredictor/model/User;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getApuestasByUsuario", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEquipoEstadisticas", "Lcom/tfg/nbapredictor/model/EquipoEstadisticas;", "equipoId", "getEquipos", "Lcom/tfg/nbapredictor/model/Equipo;", "getPartidos", "getUserById", "login", "username", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "user", "(Lcom/tfg/nbapredictor/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUser", "SocketResponse", "app_debug"})
public final class SocketApi {
    @org.jetbrains.annotations.NotNull()
    private static final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tfg.nbapredictor.network.SocketApi INSTANCE = null;
    
    private SocketApi() {
        super();
    }
    
    private final <T extends java.lang.Object>java.lang.Object call(java.lang.String action, java.lang.Object payload, java.lang.Class<T> clazz, kotlin.coroutines.Continuation<? super T> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object register(@org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.User[]> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateUser(@org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPartidos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.Partido[]> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object finalizarPartido(long id, int puntosLocal, int puntosVisitante, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.Partido> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createApuesta(@org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.Apuesta apuesta, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.Apuesta> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getApuestasByUsuario(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.Apuesta[]> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getEquipos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.Equipo[]> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getEquipoEstadisticas(long equipoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.model.EquipoEstadisticas> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object canjearPuntos(@org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.network.CanjearPuntosRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tfg.nbapredictor.network.CanjearPuntosResponse> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B3\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\tJ\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J7\u0010\u0015\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00052\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r\u00a8\u0006\u001b"}, d2 = {"Lcom/tfg/nbapredictor/network/SocketApi$SocketResponse;", "", "requestId", "", "ok", "", "data", "Lcom/google/gson/JsonElement;", "error", "(Ljava/lang/String;ZLcom/google/gson/JsonElement;Ljava/lang/String;)V", "getData", "()Lcom/google/gson/JsonElement;", "getError", "()Ljava/lang/String;", "getOk", "()Z", "getRequestId", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    static final class SocketResponse {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String requestId = null;
        private final boolean ok = false;
        @org.jetbrains.annotations.Nullable()
        private final com.google.gson.JsonElement data = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String error = null;
        
        public SocketResponse(@org.jetbrains.annotations.Nullable()
        java.lang.String requestId, boolean ok, @org.jetbrains.annotations.Nullable()
        com.google.gson.JsonElement data, @org.jetbrains.annotations.Nullable()
        java.lang.String error) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getRequestId() {
            return null;
        }
        
        public final boolean getOk() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.google.gson.JsonElement getData() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getError() {
            return null;
        }
        
        public SocketResponse() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.google.gson.JsonElement component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.tfg.nbapredictor.network.SocketApi.SocketResponse copy(@org.jetbrains.annotations.Nullable()
        java.lang.String requestId, boolean ok, @org.jetbrains.annotations.Nullable()
        com.google.gson.JsonElement data, @org.jetbrains.annotations.Nullable()
        java.lang.String error) {
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
}