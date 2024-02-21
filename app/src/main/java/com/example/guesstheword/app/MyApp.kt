package com.example.guesstheword.app

import android.app.Application
import com.example.guesstheword.dependencies.Appcontainer
class MyApp : Application() {
    //contenedor de dependencias manuales.
    private lateinit var _appContainer : Appcontainer
    val appContainer get() = _appContainer

    //Inicialización del container para que pueda recibir correctamente el contexto.
    override fun onCreate() {
        super.onCreate()
        _appContainer = Appcontainer(this)
    }
}