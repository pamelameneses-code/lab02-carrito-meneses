# Pruebas de Fase 2

Estado: pendientes en Android Studio. No se ejecutó Gradle ni el emulador desde el asistente.

| Mejora | Procedimiento | Resultado esperado | Estado |
| --- | --- | --- | --- |
| Búsqueda | Escribir cafe y luego americano cafe | Café americano aparece sin depender de tildes/orden | Pendiente |
| Filtros | Elegir categoría, Solo disponibles y limpiar | Combinación coherente; limpiar restaura Todo y texto vacío | Pendiente |
| Sin resultados | Escribir un nombre inexistente | Mensaje y botón Limpiar búsqueda y filtros | Pendiente |
| Repetir | Historial → Repetir pedido con bolsa vacía | Carga cantidades válidas y precios actuales; no crea pedido aún | Pendiente |
| Reemplazo | Repetir con una bolsa ya cargada, cancelar y luego aceptar | Cancelar conserva bolsa; aceptar reemplaza, sin duplicar líneas | Pendiente |
| Agotados | Agotar/eliminar un producto del pedido antes de repetir | Lo omite con aviso; si no queda ninguno, conserva la bolsa anterior | Pendiente |
| Precio histórico | Cambiar precio y repetir | Pedido antiguo conserva su total; nueva bolsa usa precio actual | Pendiente |
| Gráfico | Crear un pedido hoy y consultar su hora | Aumenta la barra correspondiente; horas vacías con cero | Pendiente |
| Cancelación | Cancelar un pedido y volver al panel | Disminuyen barra/ventas/cantidad del día | Pendiente |
| Medianoche | Probar un cambio de día o registros de días distintos | Gráfico solo del intervalo local actual; cambia el día sin reiniciar VM | Pendiente |
| Foto | Editar producto y elegir una foto de galería; guardar | Vista previa y foto en menús de ambos perfiles, detalle y bolsa | Pendiente |
| Reinicio de foto | Cerrar y abrir, volver a ingresar | Foto conservada aunque no exista un permiso temporal activo de galería | Pendiente |
| Cancelar selector | Abrir galería y volver sin elegir | Conserva foto anterior; no guarda ni crea producto | Pendiente |
| Quitar foto | Quitar foto, guardar y revisar cliente | Aparece la foto incluida del producto; Usar ilustración permite volver al dibujo | Pendiente |
| Formato/tamaño | Seleccionar archivo no decodificable o mayor a 20 MB | Error controlado sin cerrar la app ni guardar producto incompleto | Pendiente |
| Migración | Instalar actualización sobre datos de Fase 1 sin desinstalar | Conserva usuarios, productos y pedidos; fotoUri inicia vacío y los seis productos iniciales muestran sus fotos incluidas | Pendiente |

## Comprobaciones realizadas fuera de Android

SQLite con consulta extraída del código: 14 consultas válidas con EXPLAIN; agrupación horaria, límites de día, cancelación, día vacío y día siguiente. Migración SQL 1→2 conserva registros e incorpora fotoUri nullable. Revisión estática de 59 Kotlin: imports internos, delimitadores, contratos de Repository y capas. No equivale a Room/KSP ni a una compilación Kotlin.

## Capturas nuevas sugeridas

- Búsqueda con resultados y Solo disponibles.
- Repetir pedido desde Historial y aviso al omitir un agotado.
- Panel con gráfico de datos reales.
- Formulario con foto real y menú de estudiante con esa misma foto.

Las imágenes actuales del README son de la Fase 1 y no prueban estas mejoras.
