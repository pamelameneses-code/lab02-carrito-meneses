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

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
@Composable fun CafeTextField(valor: String, onChange: (String) -> Unit, etiqueta: String,
    icono: ImageVector? = null, password: Boolean = false, modifier: Modifier = Modifier, enabled: Boolean = true) {
    OutlinedTextField(value=valor,onValueChange=onChange,label={ Text(etiqueta) },modifier=modifier.fillMaxWidth(),
        leadingIcon=icono?.let { i -> { Icon(i,contentDescription=null) } },
        visualTransformation=if(password) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine=true,shape=RoundedCornerShape(10.dp),enabled=enabled)
}
