package com.tfg.nbapredictor.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B+\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0016\b\u0002\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000bH\u0017J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000bH\u0016R\u001c\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/tfg/nbapredictor/ui/dashboard/MatchesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/tfg/nbapredictor/ui/dashboard/MatchesAdapter$MatchViewHolder;", "partidos", "", "Lcom/tfg/nbapredictor/model/Partido;", "onItemClick", "Lkotlin/Function1;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "MatchViewHolder", "app_release"})
public final class MatchesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.tfg.nbapredictor.ui.dashboard.MatchesAdapter.MatchViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.tfg.nbapredictor.model.Partido> partidos = null;
    @org.jetbrains.annotations.Nullable()
    private final kotlin.jvm.functions.Function1<com.tfg.nbapredictor.model.Partido, kotlin.Unit> onItemClick = null;
    
    public MatchesAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.tfg.nbapredictor.model.Partido> partidos, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super com.tfg.nbapredictor.model.Partido, kotlin.Unit> onItemClick) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.tfg.nbapredictor.ui.dashboard.MatchesAdapter.MatchViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.ui.dashboard.MatchesAdapter.MatchViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0016\b\u0002\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u000fH\u0007R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/tfg/nbapredictor/ui/dashboard/MatchesAdapter$MatchViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "tvDate", "Landroid/widget/TextView;", "tvEstado", "tvMarcador", "tvTeams", "bind", "", "partido", "Lcom/tfg/nbapredictor/model/Partido;", "onItemClick", "Lkotlin/Function1;", "app_release"})
    public static final class MatchViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvTeams = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvDate = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvEstado = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvMarcador = null;
        
        public MatchViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.tfg.nbapredictor.model.Partido partido, @org.jetbrains.annotations.Nullable()
        kotlin.jvm.functions.Function1<? super com.tfg.nbapredictor.model.Partido, kotlin.Unit> onItemClick) {
        }
    }
}