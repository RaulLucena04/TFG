package com.tfg.nbapredictor.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\f\u001a\u00020\u0005J\b\u0010\r\u001a\u0004\u0018\u00010\u0007J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0014"}, d2 = {"Lcom/tfg/nbapredictor/util/Session;", "", "()V", "_userUpdated", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "currentUser", "Lcom/tfg/nbapredictor/model/User;", "userUpdated", "Lkotlinx/coroutines/flow/SharedFlow;", "getUserUpdated", "()Lkotlinx/coroutines/flow/SharedFlow;", "clearSession", "getCurrentUser", "isAdmin", "", "isLoggedIn", "notifyUserUpdated", "setCurrentUser", "user", "app_debug"})
public final class Session {
    @org.jetbrains.annotations.Nullable()
    private static com.tfg.nbapredictor.model.User currentUser;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Unit> _userUpdated = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> userUpdated = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tfg.nbapredictor.util.Session INSTANCE = null;
    
    private Session() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> getUserUpdated() {
        return null;
    }
    
    public final void setCurrentUser(@org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.User user) {
    }
    
    public final void notifyUserUpdated() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.tfg.nbapredictor.model.User getCurrentUser() {
        return null;
    }
    
    public final void clearSession() {
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    public final boolean isAdmin() {
        return false;
    }
}