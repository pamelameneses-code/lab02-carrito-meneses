package com.tecsup.rickymorty

import android.app.Application

class CatalogoApplication : Application() {
    val container by lazy { AppContainer(this) }
}
