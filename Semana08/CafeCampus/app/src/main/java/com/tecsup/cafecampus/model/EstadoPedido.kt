package com.tecsup.cafecampus.model

enum class EstadoPedido {
    RECIBIDO,
    PREPARANDO,
    LISTO,
    ENTREGADO,
    CANCELADO;

    fun siguiente(): EstadoPedido? = when (this) {
        RECIBIDO -> PREPARANDO
        PREPARANDO -> LISTO
        LISTO -> ENTREGADO
        ENTREGADO, CANCELADO -> null
    }
}
