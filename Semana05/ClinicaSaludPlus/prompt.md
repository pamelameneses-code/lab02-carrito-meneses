
# Prompt de Ingeniería de IA - Semana 05

**Estudiante:** Pamela Meneses  
**Proyecto:** Clínica Salud+ (`lab02-carrito-meneses`)  
**Rama:** `mejora-ia`  

---

### Prompt Estructurado de la Mejora (Fase 2)

**Rol:** 
Actúa como un Desarrollador Senior UI/UX en Android con Kotlin y Jetpack Compose.

**Contexto del Sistema:**
Nos encontramos optimizando la interfaz móvil de la aplicación **"Clínica Salud+"**. El módulo requiere presentar el portal del paciente, la lista de citas médicas programadas y un flujo interactivo para cancelar/eliminar citas.

**Objetivo del Requerimiento:**
Diseñar una interfaz accesible e intuitiva en Jetpack Compose con identidad visual turquesa (`#00838F`), adaptada para la usuaria **Pamela Meneses**, e implementar la gestión dinámica de citas médicas permitiendo su cancelación mediante un cuadro de diálogo de confirmación (`AlertDialog`).

---

### Requisitos Técnicos y Visuales

1. **Identidad Visual y Sistema de Diseño:**
   * **Color Primario:** Aplicar la paleta de salud con el color turquesa `#00838F` en la barra superior (`TopAppBar`), encabezados e indicadores visuales.
   * **Independencia de Recursos:** Utilizar componentes gráficos nativos (`Canvas` y cajas estilizadas) para representar los avatares e indicadores sin depender de librerías externas de íconos.
   * **Encabezado Personalizado:** Incluir una tarjeta destacada con el saludo directo: *"¡Hola, Pamela Meneses! Bienvenida a tu portal de salud"* y un avatar circular con sus iniciales ("PM").

2. **Gestión Dinámica de Citas (Mejora de IA):**
   * **Modelo de Datos:** Implementación del modelo `data class CitaMedica(val id: Int, val doctor: String, val especialidad: String, val fecha: String, val hora: String, val estado: String)`.
   * **Componente AlertDialog:** Al hacer clic en el botón de la tarjeta *"Cancelar Cita"*, desplegar un diálogo modal de confirmación.
   * **Gestión de Estado (`mutableStateOf`):** Al confirmar en el `AlertDialog`, remover de forma dinámica la cita seleccionada del estado global de la lista (`listaCitas`), actualizando la pantalla en tiempo real.

---

### Entregables Esperados:
* Código completo y compilable en `MainActivity.kt` sin errores de librerías externas.
* Aplicación responsive lista para ejecutarse en el emulador Android (API 34).
* Documentación técnica del prompt utilizado dentro del archivo `Semana05/prompt.md`.
