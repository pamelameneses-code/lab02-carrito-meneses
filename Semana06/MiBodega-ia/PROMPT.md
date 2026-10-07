# PROMPT MAESTRO DE CONTINUIDAD Y REFACTORIZACIÓN - "MiBodega-ia"

## 1. CONTEXTO GENERAL DEL PROYECTO
- **Nombre del Proyecto:** `MiBodega-ia`
- **Ubicación en el Repositorio:** `Lab02CarritoKotlin/Semana06/MiBodega-ia`
- **Rama de Git:** `mejora-ia` (completamente sincronizada con GitHub y libre de carpetas obsoletas/duplicadas).
- **Entorno Técnico:** Kotlin, Jetpack Compose, Material Design 3, Android Studio.
- **Tipo de Aplicación:** MVP de e-commerce móvil enfocado en bodegas de barrio.

---

## 2. REFACTORIZACIÓN Y MEJORAS REALIZADAS (SEMANA 06)
1. **Limpieza del Repositorio Git:**
   - Se eliminó definitivamente la carpeta no rastreada/duplicada `Semana06/MiBodega` para evitar conflictos de compilación e inconsistencias en la estructura.
   - El proyecto operativo y funcional reside únicamente en `Semana06/MiBodega-ia`.

2. **Refactorización del Modelo de Datos (`Producto.kt`):**
   - Se añadió la anotación `@DrawableRes` a la propiedad `imagenRes` en la data class `Producto`.
   - Se migró el manejo de imágenes de URLs/placeholders a referencias directas de recursos locales en Android (`R.drawable.*`).

3. **Actualización de la Fuente de Datos (`DatosFake.kt`):**
   - Se vincularon todos los productos de prueba a imágenes reales alojadas en `app/src/main/res/drawable/` (por ejemplo: `R.drawable.coca_cola`, `R.drawable.galletas`, etc.).

4. **Refactorización de la Interfaz de Usuario (`ProductoCard.kt`):**
   - Se eliminó el icono genérico de marcador de posición.
   - Se implementó la renderización de recursos visuales locales utilizando `painterResource(id = producto.imagenRes)`.
   - Se configuró el escalado de imagen con `ContentScale.Crop` dentro de un contenedor `Box` / `Card` con bordes redondeados y sombras de Material 3.

5. **Estructura y Navegación (`ClienteApp.kt`):**
   - Mantenimiento del flujo de navegación tipo App de Cliente: Catálogo de Productos -> Detalle del Producto -> Carrito de Compras -> Confirmación de Pedido (`Pedido.kt`, `ItemCarrito.kt`).

---

## 3. REGLAS ARQUITECTÓNICAS E INVARIANTES (ESTRICTO)
- **Manejo de Imágenes:** Todas las imágenes DEBEN cargarse mediante `painterResource` apuntando a `@DrawableRes Int`. No agregar librerías de carga asíncrona remota (Coil, Glide) salvo solicitud explícita.
- **Inmutabilidad y Estado:** Respetar los patrones de Jetpack Compose (`remember`, `mutableStateOf`, hoisting de estados) para el carrito y los totales.
- **Consistencia de Nombres:** Mantener los paquetes dentro de `com.tecsup.mibodega.*`. No alterar la estructura de imports.

---

## 4. INSTRUCCIÓN PARA LA NUEVA TAREA
[INSERTA AQUÍ TU NUEVA SOLICITUD O LO QUE QUIERES DESARROLLAR AHORA]
Ejemplo: "Añade un campo de búsqueda en la parte superior del catálogo para filtrar los productos por nombre en tiempo real."
