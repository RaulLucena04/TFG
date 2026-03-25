package com.tfg.nbapredictor.ui.matches;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0003J\b\u0010\r\u001a\u00020\fH\u0003J$\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\fH\u0016J\u001a\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0017J\b\u0010\u0019\u001a\u00020\fH\u0002J\u0016\u0010\u001a\u001a\u00020\f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/tfg/nbapredictor/ui/matches/MatchesFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/tfg/nbapredictor/databinding/FragmentMatchesBinding;", "binding", "getBinding", "()Lcom/tfg/nbapredictor/databinding/FragmentMatchesBinding;", "todosLosPartidos", "", "Lcom/tfg/nbapredictor/model/Partido;", "aplicarFiltro", "", "loadMatches", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupFilterSpinner", "setupRecyclerView", "partidos", "app_debug"})
public final class MatchesFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.tfg.nbapredictor.databinding.FragmentMatchesBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.tfg.nbapredictor.model.Partido> todosLosPartidos;
    
    public MatchesFragment() {
        super();
    }
    
    private final com.tfg.nbapredictor.databinding.FragmentMatchesBinding getBinding() {
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
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupFilterSpinner() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    private final void loadMatches() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    private final void aplicarFiltro() {
    }
    
    private final void setupRecyclerView(java.util.List<com.tfg.nbapredictor.model.Partido> partidos) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}