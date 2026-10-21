# PROMPTS.md — Documentación de Prompts para la Fase 2: Mejora UI asistida por IA

**Proyecto:** tecsupstore-ia
**Rama de Git:** `mejora-ia`
**Estudiante:** Pamela Meneses
**Curso/Laboratorio:** Semana 06 - Implementación de UI asistida por IA

---

## 1. Prompt de Solicitud de Código (Prompt enviado a la IA)

Copia y pega el siguiente prompt en la herramienta de IA para obtener la implementación exacta de la Fase 2:

```text
Actúa como un desarrollador experto en Kotlin y Jetpack Compose con Material 3. Necesito que generes el código fuente para la Fase 2 de mi proyecto "tecsupstore-ia", dentro del paquete "com.tecsup.tecsupstore".

Debes implementar exactamente las siguientes cualidades y especificaciones de la guía de laboratorio:

1. TarjetaProducto.kt:
   - Crear el composable TarjetaProducto que reciba un objeto Producto(id, nombre, precio) y un callback onAgregarFavorito: () -> Unit.
   - Añadir a la izquierda de cada tarjeta un contenedor Surface con ícono de bolsa de compras (Icons.Default.ShoppingBag) en fondo primaryContainer.
   - Mostrar el nombre del producto y el precio en formato de moneda en soles con exactamente dos decimales (ejemplo: "S/ 89.00", "S/ 199.00", "S/ 25.00") utilizando String.format(Locale.US, "S/ %.2f", producto.precio).
   - Incluir a la derecha un IconButton con el ícono de tres puntos verticales (Icons.Default.MoreVert) que controle un DropdownMenu.
   - El DropdownMenu desplegable debe incluir 3 opciones con sus respectivos leadingIcon:
     * "Favoritos" con icono Icons.Default.Favorite (al presionar debe invocar onAgregarFavorito() y cerrar el menú).
     * "Compartir" con icono Icons.Default.Share (al presionar debe cerrar el menú).
     * "Reportar" con icono Icons.Default.Warning (al presionar debe cerrar el menú).

2. AppDrawer.kt:
   - Crear el composable AppDrawer que reciba destinoSeleccionado: String, contadorFavoritos: Int y onNavegar: (String) -> Unit.
   - Diseñar el encabezado dentro de ModalDrawerSheet con los datos del usuario: un avatar circular con iniciales "PM", el nombre "Pamela Meneses" y el correo "pamela.meneses@tecsup.edu.pe".
   - Colocar un separador HorizontalDivider debajo del encabezado.
   - Crear exactamente 5 ítems de navegación usando NavigationDrawerItem: "Inicio", "Mis pedidos", "Favoritos", "Perfil" y "Cerrar sesión".
   - Usar Icons.Default.RadioButtonUnchecked como ícono para las opciones del drawer según la plantilla.
   - En el ítem "Favoritos", agregar la propiedad badge que renderice un Badge con el texto de contadorFavoritos únicamente cuando contadorFavoritos > 0.
   - Usar NavigationDrawerItemDefaults.ItemPadding en el padding de cada NavigationDrawerItem para el correcto alineamiento.

3. MainActivity.kt / AppNavegacion:
   - Crear el modelo de datos data class Producto(val id: Int, val nombre: String, val precio: Double) en Producto.kt.
   - Configurar la TopAppBar con un color de fondo morado (#5E2180) y texto/íconos en color blanco.
   - En la barra superior, incluir el título "TECSUP Store" y el subtítulo "Mas vendidos".
   - Implementar el estado elevado (State Hoisting) usando variables de estado remember { mutableStateOf("Inicio") } para destinoSeleccionado y remember { mutableIntStateOf(0) } para contadorFavoritos.
   - Conectar el callback de "Favoritos" de la tarjeta para que incremente contadorFavoritos de manera reactiva, actualizando en tiempo real el Badge del AppDrawer.
   - Implementar un bloque when(destinoSeleccionado) para permitir la navegación dinámica entre las pantallas: Inicio (con la LazyColumn de productos), Mis pedidos, Favoritos, Perfil y Cerrar sesión.

Por favor entrega el código dividido modularmente por archivos en el paquete com.tecsup.tecsupstore, libre de errores de importación o desajustes de tipos de datos.
