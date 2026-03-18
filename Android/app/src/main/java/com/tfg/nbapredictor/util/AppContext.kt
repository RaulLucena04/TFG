package com.tfg.nbapredictor.util

import android.app.Application
import android.content.Context

object AppContext {
    private var application: Application? = null
    
    fun init(application: Application) {
        this.application = application
    }
    
    fun get(): Context? {
        return application?.applicationContext
    }
}
