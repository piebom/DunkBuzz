package com.pieterbommele.dunkbuzz

import android.app.Application
import com.pieterbommele.dunkbuzz.data.AppContainer
import com.pieterbommele.dunkbuzz.data.DefaultAppContainer

class DunkBuzzApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}