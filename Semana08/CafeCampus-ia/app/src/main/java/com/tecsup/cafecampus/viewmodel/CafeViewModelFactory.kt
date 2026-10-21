package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.cafecampus.CafeApp
class CafeViewModelFactory(private val app: CafeApp, private val clienteId: Int = 0) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val vm: ViewModel = when(modelClass) {
            SesionViewModel::class.java -> SesionViewModel(app.repository,app.preferencias,app.inicializacion)
            MenuViewModel::class.java -> MenuViewModel(app.repository)
            PedidosClienteViewModel::class.java -> PedidosClienteViewModel(app.repository,clienteId,app.preferencias)
            PanelAdminViewModel::class.java -> PanelAdminViewModel(app.repository)
            PedidosAdminViewModel::class.java -> PedidosAdminViewModel(app.repository)
            MenuAdminViewModel::class.java -> MenuAdminViewModel(app.repository)
            else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
        @Suppress("UNCHECKED_CAST") return vm as T
    }
}
