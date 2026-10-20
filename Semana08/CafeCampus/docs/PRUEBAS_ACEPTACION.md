# Pruebas de aceptación — CafeCampus

Estado: **once capturas aportadas y revisión visual realizada; pruebas completas aún sin certificar**. No marcar OK basándose únicamente en una imagen o en la lectura del código.

## Preparación

- Ejecutar la versión actual desde Android Studio. No borrar almacenamiento, desinstalar ni limpiar la base: se necesitan los mismos datos para comparar pedidos.
- Usar las cuentas ficticias del README y seleccionar el perfil correspondiente.
- Para cada prueba anotar fecha, dispositivo/API, resultado observado y captura o referencia de pedido.
- Los precios de ejemplo son los de la siembra inicial. Si ya fueron editados, calcular con los valores actuales de la pantalla.

## 1. Clave incorrecta

1. En Login elegir Estudiante e ingresar `juan@campus.test` con una clave incorrecta.
2. Pulsar Ingresar.

Esperado: «Correo o contraseña incorrectos» y permanecer en Login. Probar también campos vacíos y una cuenta con rol distinto al seleccionado.

Resultado observado: pendiente.

## 2. Agregar dos productos y confirmar

1. Ingresar como estudiante. Abrir Menú del día, elegir cantidad 2, escribir una nota y agregar.
2. Agregar una unidad de Jugo de naranja. Comprobar contador de la bolsa: 3 unidades.
3. Abrir la bolsa, seleccionar hora y pago; comprobar subtotal, servicio 0 y total.
4. Confirmar. Anotar el identificador del pedido y abrir Mis pedidos.

Esperado: con los precios iniciales, `2 × 12.00 + 1 × 5.00 = S/ 29.00`; pedido Recibido, carrito vacío y pedido visible en Activos y Seguimiento.

Resultado/pedido: pendiente.

## 3. Producto agotado

1. Cambiar a Administrador: el flujo debe pedir credenciales. Marcar un producto Agotado pulsando su chip en Gestión del menú.
2. Volver a Estudiante, autenticar y abrir ese producto.
3. Intentar agregarlo. Si estaba en una bolsa previa, intentar confirmar.
4. Al terminar, restaurar su disponibilidad si se desea conservar el menú original.

Esperado: chip Agotado, agregar deshabilitado y confirmación rechazada si contiene un producto que dejó de estar disponible.

Resultado observado: pendiente.

## 4. Avanzar a Preparando

1. Crear o localizar un pedido Recibido del estudiante; anotar su id.
2. Cambiar a Administrador, ingresar y buscarlo en Nuevos.
3. Pulsar Pasar a Preparando y comprobar el chip/filtro.
4. Volver a Estudiante, ingresar y abrir Seguimiento del mismo id, sin cerrar ni reiniciar la aplicación.

Esperado: estado Preparando en ambos perfiles. El dato se obtiene del Flow de Room. En una sola sesión de emulador no se ven los dos perfiles simultáneamente.

Resultado/pedido: pendiente.

## 5. Cancelar

1. Como administrador cancelar un pedido activo del estudiante; anotar id y total.
2. Cambiar a Estudiante, ingresar y abrir Mis pedidos → Historial.

Esperado: pedido Cancelado en Historial, fuera de Activos. Si se cancela el pedido de la prueba 2, anotarlo para las pruebas de precio/ventas.

Resultado/pedido: pendiente.

## 6. Conservar precio histórico

1. Anotar nombre, precio unitario y total de un pedido ya creado.
2. Como administrador editar el precio de uno de sus productos, por ejemplo de S/ 12.00 a S/ 13.00.
3. Revisar el detalle del pedido antiguo.
4. Crear un pedido nuevo con el producto editado. Si se restaura el precio original, hacerlo después de registrar la evidencia.

Esperado: el pedido antiguo conserva nombre/precio/total de su creación; el nuevo usa el precio actualizado.

Resultado/pedidos: pendiente.

## 7. Ventas del día

1. Como administrador registrar el total inicial de ventas del día.
2. Crear un pedido nuevo del estudiante y anotar su total T; verificar que las ventas aumentan en T.
3. Cancelar ese pedido y revisar nuevamente el panel.

Esperado: se excluyen cancelados y pedidos de días anteriores; tras cancelar se resta T. El gráfico por hora es fijo y no se usa para comprobar ventas.

Resultado/valores: pendiente.

## 8. Cerrar y reabrir

1. Activar modo oscuro y anotar un pedido existente.
2. Cerrar la aplicación desde recientes; abrirla nuevamente.
3. Comprobar el tema, ingresar y revisar el pedido.

Esperado en esta versión solicitada: modo oscuro y pedidos persisten, pero se piden credenciales. El PDF original pedía recordar el perfil y omitir el login; se cambió por petición del usuario. No afirmar que pasa ese requisito original sin esta salvedad.

Resultado observado: pendiente.

## 9. Rotación

1. Como estudiante agregar productos, registrar cantidades y total y rotar con los controles del emulador.
2. Comprobar la bolsa y la sesión. Abrir un detalle, modificar cantidad/nota y volver a rotar.

Esperado: no se pierde el carrito ni la sesión por una rotación. Cantidad/nota se conservan en el detalle. No confundir rotación con muerte del proceso: la bolsa no está persistida en disco.

Resultado observado: pendiente.

## Revisiones adicionales solicitadas

- Escribir `cafe` en el buscador: debe mostrar Café americano; borrar el texto restaura el filtro de categoría actual.
- Cambiar de perfil: vuelve a Login con el rol destino seleccionado y campos vacíos.
- Apagar Recibir pedidos: el estudiante ve Cafetería cerrada y no puede confirmar.
- Guardar/editar/eliminar un producto; eliminar pide confirmación.
- Notificaciones persiste como preferencia local; no hay notificaciones del sistema.

## Registro final

| Nº | Estado | Evidencia/observación |
| --- | --- | --- |
| 1 | Pendiente | — |
| 2 | Parcial | Pedido #0009 Recibido: 1 Menú S/ 12.00 + 1 Empanada S/ 4.50 = S/ 16.50. Captura de bolsa vacía, sin secuencia que pruebe el vaciado tras envío. |
| 3 | Parcial | Café americano Agotado en cliente/admin y botón + deshabilitado. Falta intento de confirmación con producto agotado. |
| 4 | Parcial | #0008 Listo en ambos perfiles; #0006 Preparando en estudiante. Falta secuencia de transición sin reinicio. |
| 5 | Pendiente | — |
| 6 | Pendiente | — |
| 7 | Parcial | Panel muestra S/ 106.50 y 8 pedidos. Falta verificar suma y exclusión de cancelados. |
| 8 | Parcial | Modo oscuro visible; no se muestra cierre/reapertura. |
| 9 | Pendiente | — |

## Evidencias recibidas

Las once imágenes están en [capturas](capturas/README.md). Revisadas visualmente; no ejecutadas por el asistente. Las pruebas 1, 5, 6 y 9 no tienen evidencia específica y las demás solo evidencia parcial. Ninguna se marca automáticamente como aprobada.

En la confirmación aportada la bolsa está vacía y el botón de confirmar está deshabilitado, lo que documenta ese estado de la interfaz, pero no el envío de un pedido. Además, el título de esa captura dice Seguimiento: se conserva la inconsistencia como observación, sin retocar la imagen.
