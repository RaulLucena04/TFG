package com.tfg.nbapredictor.ui.compose.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a8\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a&\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000bH\u0083@\u00a2\u0006\u0002\u0010\u0012\u001ah\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0014\u0010\u0017\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0004\u0012\u00020\u00010\u00182\u0014\u0010\u0019\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0004\u0012\u00020\u00010\u00182\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0018H\u0003\u001a\u0012\u0010\u001b\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u0002\u00a8\u0006\u001c"}, d2 = {"CreateBetDialog", "", "open", "", "preselectedPartido", "Lcom/tfg/nbapredictor/model/Partido;", "onDismiss", "Lkotlin/Function0;", "onBetCreated", "PredictionChip", "label", "", "selected", "onClick", "calcularCuota", "", "partido", "prediccion", "(Lcom/tfg/nbapredictor/model/Partido;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recalculateCuotaAndGanancia", "scope", "Lkotlinx/coroutines/CoroutineScope;", "puntosText", "setCuota", "Lkotlin/Function1;", "setGanancia", "setLoading", "selectedPartidoLabel", "app_debug"})
public final class CreateBetDialogKt {
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.compose.runtime.Composable()
    public static final void CreateBetDialog(boolean open, @org.jetbrains.annotations.Nullable()
    com.tfg.nbapredictor.model.Partido preselectedPartido, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBetCreated) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void PredictionChip(java.lang.String label, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    private static final java.lang.String selectedPartidoLabel(com.tfg.nbapredictor.model.Partido partido) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    private static final java.lang.Object calcularCuota(com.tfg.nbapredictor.model.Partido partido, java.lang.String prediccion, kotlin.coroutines.Continuation<? super java.lang.Double> $completion) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    private static final void recalculateCuotaAndGanancia(kotlinx.coroutines.CoroutineScope scope, com.tfg.nbapredictor.model.Partido partido, java.lang.String prediccion, java.lang.String puntosText, kotlin.jvm.functions.Function1<? super java.lang.Double, kotlin.Unit> setCuota, kotlin.jvm.functions.Function1<? super java.lang.Double, kotlin.Unit> setGanancia, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> setLoading) {
    }
}