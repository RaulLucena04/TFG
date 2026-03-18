package com.tfg.nbapredictor

import android.app.Application
import com.tfg.nbapredictor.util.AppContext

class NBAPredictorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
    }
}
