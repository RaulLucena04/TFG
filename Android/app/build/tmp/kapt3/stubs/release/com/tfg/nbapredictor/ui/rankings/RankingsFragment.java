package com.tfg.nbapredictor.ui.rankings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\tH\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0016\u0010\u0012\u001a\u00020\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0016\u0010\u0014\u001a\u00020\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J$\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0011H\u0016J\u001a\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u001e\u0010 \u001a\u00020\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\"H\u0002J\b\u0010&\u001a\u00020\u0011H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/tfg/nbapredictor/ui/rankings/RankingsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/tfg/nbapredictor/databinding/FragmentRankingsBinding;", "binding", "getBinding", "()Lcom/tfg/nbapredictor/databinding/FragmentRankingsBinding;", "rankingRows", "", "Lcom/tfg/nbapredictor/ui/rankings/RankingRow;", "calcularEstadisticasUsuario", "usuario", "Lcom/tfg/nbapredictor/model/User;", "apuestas", "Lcom/tfg/nbapredictor/model/Apuesta;", "loadRankings", "", "mostrarPosicionUsuario", "rows", "mostrarTop3", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "ordenarYMostrar", "tipoRanking", "", "parseTasaAciertos", "", "tasa", "setupRankingTypeSpinner", "app_release"})
public final class RankingsFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.tfg.nbapredictor.databinding.FragmentRankingsBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.tfg.nbapredictor.ui.rankings.RankingRow> rankingRows;
    
    public RankingsFragment() {
        super();
    }
    
    private final com.tfg.nbapredictor.databinding.FragmentRankingsBinding getBinding() {
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
    
    private final void setupRankingTypeSpinner() {
    }
    
    private final void loadRankings() {
    }
    
    private final com.tfg.nbapredictor.ui.rankings.RankingRow calcularEstadisticasUsuario(com.tfg.nbapredictor.model.User usuario, java.util.List<com.tfg.nbapredictor.model.Apuesta> apuestas) {
        return null;
    }
    
    private final void ordenarYMostrar(java.util.List<com.tfg.nbapredictor.ui.rankings.RankingRow> rows, java.lang.String tipoRanking) {
    }
    
    private final double parseTasaAciertos(java.lang.String tasa) {
        return 0.0;
    }
    
    private final void mostrarTop3(java.util.List<com.tfg.nbapredictor.ui.rankings.RankingRow> rows) {
    }
    
    private final void mostrarPosicionUsuario(java.util.List<com.tfg.nbapredictor.ui.rankings.RankingRow> rows) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}