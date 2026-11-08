package com.tecsup.rickymorty.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tecsup.rickymorty.R
import com.tecsup.rickymorty.domain.model.Item

@Composable
fun EncabezadoCatalogo(
    titulo: String,
    subtitulo: String,
    icono: Int,
    accion: @Composable () -> Unit = {}
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.primaryContainer) {
            Box(Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                Icon(painterResource(icono), null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
        Column(Modifier.weight(1f)) {
            Text(titulo, style = MaterialTheme.typography.headlineSmall)
            Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        accion()
    }
}

@Composable
fun CuadriculaPersonajes(
    personajes: List<Item>,
    abrir: (Int) -> Unit,
    alternarFavorito: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(144.dp),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(personajes, key = { it.id }) { item ->
            OutlinedCard(
                onClick = { abrir(item.id) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Box(Modifier.fillMaxWidth().aspectRatio(1.2f)) {
                    FotoPersonaje(item, Modifier.matchParentSize())
                    Box(Modifier.align(Alignment.TopEnd).padding(4.dp)) {
                        IconToggleButton(
                            checked = item.esFavorito,
                            onCheckedChange = { alternarFavorito(item.id) }
                        ) {
                            Surface(shape = CircleShape, color = MaterialTheme.colorScheme.surface) {
                                Icon(
                                    painterResource(if (item.esFavorito) R.drawable.ic_favorito else R.drawable.ic_favorito_borde),
                                    contentDescription = if (item.esFavorito) "Quitar a ${item.titulo} de favoritos"
                                        else "Guardar a ${item.titulo} en favoritos",
                                    modifier = Modifier.padding(8.dp).size(20.dp),
                                    tint = if (item.esFavorito) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(item.titulo, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(
                        item.subtitulo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun FotoPersonaje(item: Item, modifier: Modifier = Modifier) {
    Box(modifier.background(MaterialTheme.colorScheme.secondaryContainer), contentAlignment = Alignment.Center) {
        Icon(
            painterResource(R.drawable.ic_portal), null, Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
        AsyncImage(
            model = item.imagenUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun EstadoVacioCatalogo(
    icono: Int,
    titulo: String,
    mensaje: String,
    accion: String? = null,
    onAccion: () -> Unit = {}
) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer) {
            Icon(painterResource(icono), null, Modifier.padding(16.dp).size(24.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
        }
        Text(titulo, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
        Text(mensaje, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
        accion?.let { TextButton(onClick = onAccion) { Text(it) } }
    }
}
