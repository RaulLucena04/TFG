package com.tfg.nbapredictor.ui.bets;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u001f\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0004H\u0002J\b\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J$\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\b\u0010$\u001a\u00020\u0004H\u0016J\u001a\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u001d2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\b\u0010\'\u001a\u00020\u0004H\u0002J\b\u0010(\u001a\u00020\u0004H\u0002J\u0010\u0010)\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0011H\u0002J\b\u0010+\u001a\u00020\u0004H\u0002J\b\u0010,\u001a\u00020\u0004H\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/tfg/nbapredictor/ui/bets/CreateBetDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "onBetCreated", "Lkotlin/Function0;", "", "preselectedPartido", "Lcom/tfg/nbapredictor/model/Partido;", "(Lkotlin/jvm/functions/Function0;Lcom/tfg/nbapredictor/model/Partido;)V", "_binding", "Lcom/tfg/nbapredictor/databinding/DialogCreateBetBinding;", "binding", "getBinding", "()Lcom/tfg/nbapredictor/databinding/DialogCreateBetBinding;", "partidosDisponibles", "", "selectedPartido", "selectedPrediccion", "", "calcularCuota", "", "partido", "prediccion", "(Lcom/tfg/nbapredictor/model/Partido;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createBet", "hideError", "loadPartidos", "user", "Lcom/tfg/nbapredictor/model/User;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupListeners", "setupPrediccionSpinner", "showError", "msg", "updateCuotaAndGanancia", "updateGananciaPotencial", "app_debug"})
public final class CreateBetDialogFragment extends androidx.fragment.app.DialogFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onBetCreated = null;
    @org.jetbrains.annotations.Nullable()
    private final com.tfg.nbapredictor.model.Partido preselectedPartido = null;
    @org.jetbrains.annotations.Nullable()
    private com.tfg.nbapredictor.databinding.DialogCreateBetBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.tfg.nbapredictor.model.Partido> partidosDisponibles;
    @org.jetbrains.annotations.Nullable()
    private com.tfg.nbapredictor.model.Partido selectedPartido;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String selectedPrediccion;
    
    public CreateBetDialogFragment(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBetCreated, @org.jetbrains.annotations.Nullable()
    com.tfg.nbapredictor.model.Partido preselectedPartido) {
        super();
    }
    
    private final com.tfg.nbapredictor.databinding.DialogCreateBetBinding getBinding() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupPrediccionSpinner() {
    }
    
    private final void loadPartidos(@kotlin.Suppress(names = {"UNUSED_PARAMETER"})
    com.tfg.nbapredictor.model.User user) {
    }
    
    private final void setupListeners() {
    }
    
    private final void updateCuotaAndGanancia() {
    }
    
    private final void updateGananciaPotencial() {
    }
    
    private final java.lang.Object calcularCuota(com.tfg.nbapredictor.model.Partido partido, java.lang.String prediccion, kotlin.coroutines.Continuation<? super java.lang.Double> $completion) {
        return null;
    }
    
    private final void createBet() {
    }
    
    private final void showError(java.lang.String msg) {
    }
    
    private final void hideError() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}