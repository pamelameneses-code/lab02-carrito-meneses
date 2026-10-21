# Prompts utilizados — Fase 2

## Herramienta realmente utilizada

ChatGPT/Codex, sobre el código local de CafeCampus. El PDF propone Gemini en Android Studio; no se utilizó Gemini ni se inventan conversaciones con esa herramienta. La implementación fue solicitada expresamente al asistente en esta conversación. El usuario debe revisar esta adaptación si necesita cumplir literalmente la herramienta indicada en clase.

## Solicitud original de esta fase

> ya ahora los 3 comits q dice para modificar de acuerdo a mi ppt tenemos q hacer te vuelvo adjuntar ahora lee la fase dos que es mejora con ia para q lo puedas hacer en mi android tmbien quisiera implementarlo fotos

Contexto conservado: trabajar solo en Semana08/CafeCampus-ia, rama mejora-ia; no tocar main, no cambiar dependencias ni ejecutar Gradle. MVVM con CafeRepository como interface. El buscador ya existía por un ajuste anterior de la Fase 1.

Los apartados siguientes describen el desglose técnico de esa solicitud y lo generado. No son mensajes ficticios enviados a Gemini.

## Mejora 1 — Buscar en el menú

**Instrucción derivada de la solicitud:** completar la mejora «Buscar en el menú» respetando el buscador ya existente y situando el filtrado en MenuViewModel.

**Qué generó la IA:** combinación reactiva de catálogo, categoría, texto y disponibilidad; normalización de tildes/mayúsculas; búsqueda por varias palabras en cualquier orden; filtro Solo disponibles, contador y limpieza de filtros en estados vacíos.

**Qué se revisó/corrigió:** no se presentó la búsqueda como totalmente nueva, porque main ya contenía el campo funcional. El commit registra mejoras reales sobre esa base. No se cambió ninguna dependencia.

**¿Respeta MVVM?:** sí; la View captura eventos y observa StateFlow. La selección por categoría/palabras/disponibilidad vive en MenuViewModel y los productos provienen de CafeRepository.

## Mejora 2 — Repetir pedido

**Instrucción derivada de la solicitud:** añadir Repetir pedido al historial y cargar sus productos en la bolsa sin modificar el pedido antiguo ni confirmar un pedido nuevo automáticamente.

**Qué generó la IA:** botón en Historial, confirmación antes de reemplazar una bolsa existente, recarga del catálogo actual, cantidades del pedido original, precios actuales y omisión informada de productos agotados/eliminados. Si no hay productos válidos, conserva la bolsa. El éxito navega a Confirmar pedido.

**Qué se revisó/corrigió:** no reutilizar los precios históricos para cobrar de nuevo; evitar doble envío mientras carga; no copiar notas antiguas sin revisión. Se detectó y corrigió un aviso duplicado en ConfirmarPedidoScreen, registrado en un commit de corrección aparte.

**¿Respeta MVVM?:** sí; la pantalla no consulta DAO ni Repository. MenuViewModel realiza la reconstrucción mediante CafeRepository; el Composable solo presenta la confirmación de reemplazo y navega tras el resultado.

## Mejora 3 — Gráfico real por hora

**Instrucción derivada de la solicitud:** sustituir la serie fija del panel por una consulta Room observada con Flow, recorriendo DAO → Repository → ViewModel → View.

**Qué generó la IA:** proyección PedidosPorHora, consulta con COUNT y strftime en la hora local del dispositivo, límites [inicio del día, siguiente día), exclusión de CANCELADO, contrato/implementación del Repository y StateFlow de 24 horas completadas con cero. El gráfico se desplaza horizontalmente y escala con el máximo actual. La ventana diaria cambia a medianoche.

**Qué se revisó/corrigió:** declarar primero la función en la interface; mantener Flow sin suspend para observar; usar límite superior exclusivo y evitar contar cancelados. No se muestra una serie fija como si fueran datos reales.

**¿Respeta MVVM?:** sí; la consulta SQL está en PedidoDao, se delega en CafeRepositoryRoom y el ViewModel solo depende de CafeRepository. La View dibuja los puntos recibidos.

## Mejora adicional — Fotos

**Instrucción del usuario:** «tmbien quisiera implementarlo fotos». Se interpretó como permitir que el administrador elija fotos reales de su galería para los productos; no se descargaron fotos de terceros ni se generaron fotografías artificiales.

**Qué generó la IA:** selector del sistema OpenDocument, importación mediante Repository a almacenamiento privado, validación y reducción de tamaño fuera del hilo principal, corrección EXIF, campo fotoUri opcional y migración aditiva Room 1→2. ImagenProducto carga fuera del hilo principal con caché y usa la ilustración si no hay foto. El formulario permite cambiar/quitar la foto y volver a la ilustración.

**Qué se revisó/corrigió:** no persistir únicamente un permiso temporal de galería; copiar la imagen a archivos privados. No solicitar permisos amplios de almacenamiento ni agregar una librería externa. Mantener las versiones AGP/Kotlin/KSP/Room. Conservar productos y pedidos al migrar. Las imágenes elegidas después de instalar son locales al dispositivo y no se incluyen automáticamente en GitHub.

**¿Respeta MVVM?:** sí; la View invoca el selector, envía el URI al ViewModel y renderiza la imagen. La importación de datos se delega en la interface CafeRepository; AlmacenFotos utiliza applicationContext en la capa de datos. El acceso visual al bitmap en ImagenProducto es una responsabilidad de presentación, sin consultas a Room.

## Verificación real y límites

Revisión estática de 59 archivos Kotlin, imports internos, delimitadores, nombres/firmas de métodos y capas. Se validaron 14 consultas SQL con EXPLAIN en un esquema equivalente. Se ejecutó la migración en SQLite comprobando que conserva usuarios/productos/pedidos/detalles; el campo nuevo inicia en NULL. La consulta horaria se probó con medianoche, horas múltiples, día siguiente, cancelación y día vacío.

No se ejecutó Gradle, Kotlin, KSP, Room ni el selector de fotos en Android. Las pruebas de SQLite no certifican la migración Room en el dispositivo. La búsqueda, la repetición, los permisos del proveedor de fotos, la orientación y la persistencia deben probarse en Android Studio. Las capturas que acompañan el README siguen siendo de la Fase 1.

## Reflexión técnica (5 líneas; revisar con tus propias palabras)

La IA ayudó a conectar las mejoras entre datos, ViewModels y pantallas manteniendo el contrato del Repository.
La búsqueda ya existía y hubo que ampliarla sin atribuirla como una implementación nueva desde cero.
Se detectó un aviso duplicado y se corrigió durante la revisión de la interfaz.
Flow permite que consultas y pantallas reaccionen a pedidos, cancelaciones y cambios de productos.
La revisión estática y SQLite ayudan, pero hacen falta pruebas reales en Android antes de afirmar que todo funciona.


## Ampliación — fotografías aportadas por la usuaria

**Solicitud literal:** «ahi estas imagenes implentalo tambien a donde le pertenece».

**Archivos aportados:** empanada.png, chicha-morada.webp, sandwich-de-pollo.webp, zumo-naranja.jpg, CAFE.jpg y mnu.jpg.

**Resultado:** seis recursos drawable-nodpi asociados por nombre a Empanada, Chicha morada, Sándwich de pollo, Jugo de naranja, Café americano y Menú del día. Se conservaron los originales sin retoques. La foto de galería tiene prioridad; Usar ilustración guarda esa elección explícita. La visualización funciona también en registros ya existentes, sin resembra ni migración adicional.

**MVVM:** la asociación es de presentación en ImagenProducto. No se modificaron entidades, DAOs, Repository ni lógica de negocio. Main y versiones conservados.

**Revisión:** integridad SHA-256 origen/destino, seis recursos válidos para Android, enlaces R.drawable y sitios de llamada revisados. No se compiló ni se verificó el resultado en dispositivo.
