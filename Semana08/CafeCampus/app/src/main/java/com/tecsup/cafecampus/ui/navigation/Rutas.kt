package com.tecsup.cafecampus.ui.navigation

object Rutas {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val CLIENTE = "cliente"
    const val ADMIN = "admin"
    const val MENU = "cliente/menu"
    const val MIS_PEDIDOS = "cliente/pedidos"
    const val PERFIL = "cliente/perfil"
    const val PRODUCTO = "cliente/producto/{id}"
    const val CONFIRMAR = "cliente/confirmar"
    const val SEGUIMIENTO = "cliente/seguimiento/{id}"
    const val PANEL = "admin/panel"
    const val PEDIDOS_ADMIN = "admin/pedidos"
    const val DETALLE_ADMIN = "admin/pedido/{id}"
    const val MENU_ADMIN = "admin/menu"
    const val FORMULARIO = "admin/producto?id={id}"
    const val AJUSTES = "admin/ajustes"
    fun producto(id: Int) = "cliente/producto/$id"
    fun seguimiento(id: Int) = "cliente/seguimiento/$id"
    fun detalleAdmin(id: Int) = "admin/pedido/$id"
    fun formulario(id: Int?) = "admin/producto?id=${id ?: 0}"
}
