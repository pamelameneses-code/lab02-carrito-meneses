package com.tecsup.cafecampus.ui.cliente
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.grid.*
import com.tecsup.cafecampus.viewmodel.MenuViewModel
import com.tecsup.cafecampus.ui.components.ProductoCard
@Composable fun MenuScreen(vm: MenuViewModel, onProducto: (Int) -> Unit) {
    val productos by vm.productos.collectAsState()
    LazyVerticalGrid(columns=GridCells.Fixed(2)) {
        items(productos,key={it.id}) {p -> ProductoCard(p,onClick={onProducto(p.id)})}
    }
}
