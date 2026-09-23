# Laboratorio N°05 - Navegación en Jetpack Compose

**Curso:** Programación Móviles (Tecsup)
**Docente:** Juan León Suiyon

## Descripción

Implementación de navegación entre pantallas usando **Navigation Compose**,
con una sealed class `Screen` como contrato central de rutas, paso de
argumentos tipados (`itemId: Int`) entre pantallas, y manejo del back stack
con `popBackStack()` y `popUpTo(...) { inclusive = true }`.

## Pantallas

- **HomeScreen**: pantalla de inicio, con botones hacia Lista y Perfil.
- **ListScreen**: lista de 8 elementos (LazyColumn) con TopAppBar y navegación al detalle.
- **DetailScreen**: recibe el `itemId` como argumento tipado desde el NavHost.
- **ProfileScreen**: pantalla de perfil, limpia el back stack al volver al inicio.

## Tecnologías

- Kotlin
- Jetpack Compose
- Navigation Compose (`navigation-compose:2.7.7`)
- Material 3

## Resultado final

![Lista](lista.png)
![Detalle 1](detalle1.png)
![Detalle 2](detalle2.png)
![Perfil](perfil.png)
