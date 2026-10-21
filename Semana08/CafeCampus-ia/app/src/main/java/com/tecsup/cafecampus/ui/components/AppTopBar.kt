package com.tecsup.cafecampus.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.tecsup.cafecampus.model.*
import com.tecsup.cafecampus.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun AppTopBar(titulo: String, volver: (() -> Unit)? = null, acciones: @Composable RowScope.() -> Unit = {}) {
    TopAppBar(title={ Text(titulo) }, navigationIcon={ volver?.let { IconButton(onClick=it) { Icon(Icons.Default.ArrowBack,"Volver") } } }, actions=acciones)
}
