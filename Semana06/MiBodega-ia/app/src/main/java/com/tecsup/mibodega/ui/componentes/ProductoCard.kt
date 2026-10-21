package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.R
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit,
    onAgregar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.3f)
                    .background(
                        color = GrisClaro,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = obtenerImagenProducto(producto.nombre)
                    ),
                    contentDescription = producto.nombre,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "S/ %.2f".format(producto.precio),
                    style = MaterialTheme.typography.labelMedium,
                    color = VerdeBodega
                )

                IconButton(
                    onClick = onAgregar,
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = VerdeBodega,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar ${producto.nombre}",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

private fun obtenerImagenProducto(nombre: String): Int {
    val nombreProducto = nombre.lowercase()

    return when {
        nombreProducto.contains("arroz") -> {
            R.drawable.arroz
        }

        nombreProducto.contains("leche") -> {
            R.drawable.leche
        }

        nombreProducto.contains("aceite") -> {
            R.drawable.aceite
        }

        nombreProducto.contains("gaseosa") ||
                nombreProducto.contains("coca") ||
                nombreProducto.contains("cola") -> {
            R.drawable.gaseosa
        }

        nombreProducto.contains("oreo") ||
                nombreProducto.contains("galleta") -> {
            R.drawable.galleta
        }

        else -> {
            R.drawable.galleta
        }
    }
}