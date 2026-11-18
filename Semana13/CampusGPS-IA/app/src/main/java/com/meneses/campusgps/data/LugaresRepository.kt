package com.meneses.campusgps.data

import com.meneses.campusgps.model.Lugar

interface LugaresRepository {
    fun obtenerLugares(): List<Lugar>
}
