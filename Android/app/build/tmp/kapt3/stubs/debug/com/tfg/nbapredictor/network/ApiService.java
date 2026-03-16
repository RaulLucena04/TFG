package com.tfg.nbapredictor.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J(\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\u0005\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\b\b\u0001\u0010\u0010\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\u0014\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0015J2\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\u0017\u001a\u00020\u00182\b\b\u0001\u0010\u0019\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u001c0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ$\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u001c0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010 J\u001e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010 J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010 J\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u001c0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001a\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u001c0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001a\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u001c0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ$\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u001c0\u00032\b\b\u0001\u0010*\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010 J\u001a\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u001c0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ$\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u001c0\u00032\b\b\u0001\u0010*\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010 J\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010 J\u001e\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\b\b\u0001\u0010\u0005\u001a\u00020/H\u00a7@\u00a2\u0006\u0002\u00100J\u001e\u00101\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\b\b\u0001\u00102\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u00103J(\u00104\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u00102\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u00105\u00a8\u00066"}, d2 = {"Lcom/tfg/nbapredictor/network/ApiService;", "", "canjearPuntos", "Lretrofit2/Response;", "Lcom/tfg/nbapredictor/network/CanjearPuntosResponse;", "request", "Lcom/tfg/nbapredictor/network/CanjearPuntosRequest;", "(Lcom/tfg/nbapredictor/network/CanjearPuntosRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "changePassword", "Lokhttp3/ResponseBody;", "id", "", "Lcom/tfg/nbapredictor/network/PasswordRequest;", "(JLcom/tfg/nbapredictor/network/PasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createApuesta", "Lcom/tfg/nbapredictor/model/Apuesta;", "apuesta", "(Lcom/tfg/nbapredictor/model/Apuesta;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createPartido", "Lcom/tfg/nbapredictor/model/Partido;", "partido", "(Lcom/tfg/nbapredictor/model/Partido;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "finalizarPartido", "puntosLocal", "", "puntosVisitante", "(JIILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllUsers", "", "Lcom/tfg/nbapredictor/model/User;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getApuestasByUsuario", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEquipoById", "Lcom/tfg/nbapredictor/model/Equipo;", "getEquipoEstadisticas", "Lcom/tfg/nbapredictor/model/EquipoEstadisticas;", "getEquipos", "getEquiposConEstadisticas", "getJugadores", "Lcom/tfg/nbapredictor/model/Jugador;", "getJugadoresByEquipo", "equipoId", "getPartidos", "getPartidosByEquipo", "getUserById", "login", "Lcom/tfg/nbapredictor/network/LoginRequest;", "(Lcom/tfg/nbapredictor/network/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "user", "(Lcom/tfg/nbapredictor/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUser", "(JLcom/tfg/nbapredictor/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.POST(value = "usuarios/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.User>> $completion);
    
    @retrofit2.http.POST(value = "usuarios/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.network.LoginRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.User>> $completion);
    
    @retrofit2.http.GET(value = "usuarios")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.tfg.nbapredictor.model.User>>> $completion);
    
    @retrofit2.http.GET(value = "usuarios/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserById(@retrofit2.http.Path(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.User>> $completion);
    
    @retrofit2.http.PUT(value = "usuarios/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUser(@retrofit2.http.Path(value = "id")
    long id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.User>> $completion);
    
    @retrofit2.http.PUT(value = "usuarios/{id}/password")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object changePassword(@retrofit2.http.Path(value = "id")
    long id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.network.PasswordRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<okhttp3.ResponseBody>> $completion);
    
    @retrofit2.http.GET(value = "partidos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPartidos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.tfg.nbapredictor.model.Partido>>> $completion);
    
    @retrofit2.http.GET(value = "partidos/equipo/{equipoId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPartidosByEquipo(@retrofit2.http.Path(value = "equipoId")
    long equipoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.tfg.nbapredictor.model.Partido>>> $completion);
    
    @retrofit2.http.POST(value = "partidos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createPartido(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.Partido partido, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.Partido>> $completion);
    
    @retrofit2.http.PUT(value = "partidos/{id}/finalizar")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object finalizarPartido(@retrofit2.http.Path(value = "id")
    long id, @retrofit2.http.Query(value = "puntosLocal")
    int puntosLocal, @retrofit2.http.Query(value = "puntosVisitante")
    int puntosVisitante, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.Partido>> $completion);
    
    @retrofit2.http.POST(value = "apuestas")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createApuesta(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.Apuesta apuesta, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.Apuesta>> $completion);
    
    @retrofit2.http.GET(value = "apuestas/usuario/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getApuestasByUsuario(@retrofit2.http.Path(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.tfg.nbapredictor.model.Apuesta>>> $completion);
    
    @retrofit2.http.GET(value = "equipos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEquipos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.tfg.nbapredictor.model.Equipo>>> $completion);
    
    @retrofit2.http.GET(value = "equipos-con-estadisticas")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEquiposConEstadisticas(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.tfg.nbapredictor.model.Equipo>>> $completion);
    
    @retrofit2.http.GET(value = "equipos/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEquipoById(@retrofit2.http.Path(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.Equipo>> $completion);
    
    @retrofit2.http.GET(value = "equipos/{id}/estadisticas")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEquipoEstadisticas(@retrofit2.http.Path(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.model.EquipoEstadisticas>> $completion);
    
    @retrofit2.http.GET(value = "jugadores")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJugadores(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.tfg.nbapredictor.model.Jugador>>> $completion);
    
    @retrofit2.http.GET(value = "jugadores/equipo/{equipoId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJugadoresByEquipo(@retrofit2.http.Path(value = "equipoId")
    long equipoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.tfg.nbapredictor.model.Jugador>>> $completion);
    
    @retrofit2.http.POST(value = "tienda/canjear")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object canjearPuntos(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.network.CanjearPuntosRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tfg.nbapredictor.network.CanjearPuntosResponse>> $completion);
}