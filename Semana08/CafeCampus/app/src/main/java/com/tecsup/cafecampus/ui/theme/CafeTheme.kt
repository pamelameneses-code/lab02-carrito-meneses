package com.tecsup.cafecampus.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.tecsup.cafecampus.model.*
data class ColoresEstado(val recibido: Color, val preparando: Color, val listo: Color, val entregado: Color, val cancelado: Color)
val LocalColoresEstado = staticCompositionLocalOf { ColoresEstado(Color.Unspecified,Color.Unspecified,Color.Unspecified,Color.Unspecified,Color.Unspecified) }
@Composable fun CafeTheme(rol: Rol = Rol.CLIENTE, darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val admin = rol == Rol.ADMIN
    val scheme = if(darkTheme) darkColorScheme(
        primary=Color(if(admin) 0xFFA5B4FC else 0xFFFFB787),
        primaryContainer=Color(if(admin) 0xFF312E81 else 0xFF7C2D12),
        onPrimaryContainer=Color(if(admin) 0xFFE0E7FF else 0xFFFFEDD5),
        background=Color(0xFF17151B), surface=Color(0xFF211F26),
        tertiaryContainer=Color(0xFF594400), onTertiaryContainer=Color(0xFFFFE08A)
    ) else lightColorScheme(
        primary=Color(if(admin) 0xFF4F46E5 else 0xFFEA580C), onPrimary=Color.White,
        primaryContainer=Color(if(admin) 0xFFE0E7FF else 0xFFFFEDD5),
        onPrimaryContainer=Color(if(admin) 0xFF312E81 else 0xFF7C2D12),
        background=Color(if(admin) 0xFFF7F7FD else 0xFFFFFBF7), surface=Color.White,
        tertiaryContainer=Color(0xFFFFEDB5), onTertiaryContainer=Color(0xFF594400)
    )
    val estados = if(darkTheme) ColoresEstado(Color(0xFF93C5FD),Color(0xFFFCD34D),Color(0xFF86EFAC),Color(0xFFD1D5DB),Color(0xFFFCA5A5))
        else ColoresEstado(Color(0xFF2563EB),Color(0xFFB45309),Color(0xFF15803D),Color(0xFF6B7280),Color(0xFFDC2626))
    CompositionLocalProvider(LocalColoresEstado provides estados) { MaterialTheme(colorScheme=scheme, typography=Typography, content=content) }
}
@Composable fun colorEstado(estado: EstadoPedido): Color {
    val c = LocalColoresEstado.current
    return when(estado) { EstadoPedido.RECIBIDO -> c.recibido; EstadoPedido.PREPARANDO -> c.preparando;
        EstadoPedido.LISTO -> c.listo; EstadoPedido.ENTREGADO -> c.entregado; EstadoPedido.CANCELADO -> c.cancelado }
}
