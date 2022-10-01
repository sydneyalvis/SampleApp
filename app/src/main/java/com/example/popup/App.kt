package com.example.popup

import android.app.Application
import com.example.popup.utils.Prefs

val prefs: Prefs by lazy {
    App.prefs!!
}

class App: Application() {

    companion object {

        var prefs: Prefs? = null
        lateinit var instance: App
               private set

    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Prefs(applicationContext)

    }


}