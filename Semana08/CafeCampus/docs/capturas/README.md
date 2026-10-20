# Capturas reales pendientes

No usar los mockups del PDF como evidencia de la app implementada. No hay capturas de la aplicación generadas todavía.

## Cómo obtenerlas

1. Ejecutar la versión actual en el emulador desde Android Studio e ingresar con las cuentas ficticias.
2. Mostrar cada pantalla de la tabla sin teclado ni diálogos que tapen el contenido.
3. Usar Take Screenshot (icono de cámara) en el panel del emulador y guardar el PNG en esta carpeta con el nombre indicado. Estos nombres evitan espacios.
4. En Confirmar y Seguimiento usar un pedido real de prueba. No inventar números, estados ni ventas.

| Archivo | Pantalla |
| --- | --- |
| `01-cliente-menu.png` | Menú con productos |
| `02-cliente-confirmar.png` | Bolsa con dos productos antes de confirmar |
| `03-cliente-seguimiento.png` | Pedido confirmado con estado visible |
| `04-admin-panel.png` | Panel de control |
| `05-admin-pedidos.png` | Lista de pedidos |
| `06-admin-menu.png` | Gestión del menú |

## Insertar en el README

Cuando existan los seis PNG, agregar este bloque en la sección de capturas del README principal y actualizar los estados de la tabla:

```markdown
### Estudiante

![Menú del estudiante](docs/capturas/01-cliente-menu.png)
![Confirmar pedido](docs/capturas/02-cliente-confirmar.png)
![Seguimiento del pedido](docs/capturas/03-cliente-seguimiento.png)

### Administrador

![Panel de control](docs/capturas/04-admin-panel.png)
![Pedidos del administrador](docs/capturas/05-admin-pedidos.png)
![Gestión del menú](docs/capturas/06-admin-menu.png)
```

Las rutas de este bloque son relativas al README del repositorio para que GitHub las renderice. Guardar las imágenes no reemplaza la ejecución de las nueve pruebas.
