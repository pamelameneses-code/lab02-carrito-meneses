
# Lab 04: Mi Carrito TECSUP - Jetpack Compose

**Estudiante:** [Pamela Meneses Mayhua]

## Descripción del Proyecto
Aplicación interactiva desarrollada en Android Studio utilizando Jetpack Compose. Implementa un carrito de compras dinámico que permite agregar productos, visualizarlos en una lista optimizada (`LazyColumn`), eliminarlos previa confirmación mediante un `AlertDialog`, y calcular en tiempo real el Subtotal, Descuentos aplicables, IGV y Total final.

---

## Capturas de Pantalla

### 1. Estado Vacío
*(Agrega aquí tu captura del carrito vacío)*

### 2. Carrito con Productos y Totales
*(Agrega aquí tu captura con productos y descuentos)*

---

## Respuestas al Cuestionario Conceptual

### (a) ¿Por qué `mutableStateListOf` y no una `MutableList` normal?
Se utiliza `mutableStateListOf` porque es un tipo de estado observable creado específicamente para Jetpack Compose. Cuando agregamos, modificamos o eliminamos elementos de esta lista, Compose detecta automáticamente la mutación y desencadena la recomposición (actualización) de la interfaz de usuario en tiempo real. Una `MutableList` tradicional de Kotlin sí cambiaría en memoria, pero no notificaría a la UI para que se redibuje.

### (b) ¿Por qué la lista es `val`?
Se declara como `val` porque la **referencia al objeto lista en sí no cambia** a lo largo del ciclo de vida del composable (sigue siendo la misma instancia de lista observable creada por `remember`). Lo que cambia es el **contenido interno** (los elementos agregados o eliminados) de la lista. Usar `val` protege la referencia para que no sea reasignada accidentalmente por otro objeto.

### (c) ¿Qué hace `weight(1f)` en la `LazyColumn`?
El modificador `Modifier.weight(1f)` dentro de una `Column` le indica a la `LazyColumn` que debe tomar **todo el espacio vertical disponible** que quede libre entre el formulario de arriba y el panel de totales de abajo. Además, permite que la lista se vuelva desplazable (scrollable) internamente dentro de ese espacio reservado sin empujar ni ocultar el panel de totales de la pantalla.