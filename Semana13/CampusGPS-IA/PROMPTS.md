# Uso de IA en CampusGPS

Las tres mejoras se implementaron con ChatGPT. La guía pide usar Gemini en Android Studio. No se ha realizado una sesión con Gemini.

| Petición | Propuesta aplicada | Qué se revisó y por qué |
| --- | --- | --- |
| Incorporar y realizar las tres mejoras elegidas para esta rama | Filtrar por categoría con chips y un flujo derivado en el ViewModel. | El mapa y la lista reciben los mismos lugares visibles. Si se oculta el lugar seleccionado, se limpia la selección. |
| Misma petición | Seleccionar un radio de 100, 200 o 500 m, dibujar el círculo y marcar lugares cercanos. | Se reutilizan las distancias calculadas en el ViewModel. El mapa dibuja el radio desde la posición devuelta por el repositorio. |
| Misma petición | Activar el punto azul y el botón de ubicación nativos. | Se verifica el permiso antes de activar la capa y se vuelve a consultar al regresar desde Ajustes. |

## Pruebas en el emulador

- Seleccionar cada categoría y verificar que coincidan los lugares visibles en el mapa y la lista.
- Configurar la ubicación de prueba de Lima, obtener la posición y seleccionar 100, 200 y 500 m.
- Confirmar que el punto azul y el botón de Google Maps aparezcan con permiso y se oculten tras revocarlo.
