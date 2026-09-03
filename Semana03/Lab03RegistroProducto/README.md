

\## Mejora con IA (rama mejora-ia)



| Prompt que usé | Qué generó la IA | Qué acepté o corregí |

|---|---|---|

| Le pedí que agregara validación de campos vacíos (mostrar error en rojo si falta un dato) y un botón "Limpiar" que vacíe el formulario, sin tocar el resto del diseño. | Generó una variable de estado `mostrarError`, la lógica de validación con `isBlank()`, un mensaje de error en rojo, y un `OutlinedButton` con la función de limpiar los 3 campos. | Acepté la lógica de validación tal cual. Corregí el texto del mensaje de error para que sea más corto y claro, agregué un comentario explicando por qué usé `isBlank()`, y cambié el texto del botón de "LIMPIAR" a "LIMPIAR FORMULARIO" para que sea más descriptivo. |



\*\*Nota:\*\* Se usó Claude en lugar de Gemini porque no tuve acceso a Gemini en el momento del laboratorio.

