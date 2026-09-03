\# Laboratorio 03 - Registro de Producto con Jetpack Compose



\*\*Alumna:\*\* Meneses Mayhua Pamela Regina

\*\*Curso:\*\* Programación en Móviles - 4to Ciclo

\*\*Docente:\*\* Juan José León Suiyon



\## Descripción

Pantalla de registro de producto construida con Jetpack Compose. Permite ingresar 

nombre, precio y cantidad, y al presionar "AGREGAR PRODUCTO" muestra una Card con 

el resumen y el importe calculado (precio × cantidad).



\## Capturas

!\[Pantalla inicial](captura1.png)

!\[Producto registrado](captura2.png)



\## Reflexión: ¿qué pasaría sin remember?

Sin remember, la pantalla de Jetpack Compose perdería completamente la memoria de 

lo que el usuario está escribiendo o interactuando. Cada vez que Compose redibuja 

la pantalla (recomposición), las variables de estado volverían a su valor inicial 

(texto vacío), por lo que sería imposible escribir en los campos: cada letra que 

el usuario tecleara desaparecería de inmediato porque el valor no queda "recordado" 

entre un redibujado y otro. Por eso remember es indispensable para que un 

OutlinedTextField pueda mostrar y conservar lo que el usuario escribe.



\## Mejora con IA (rama mejora-ia)



| Prompt que usé | Qué generó la IA | Qué acepté o corregí |

|---|---|---|

| Le pedí que agregara validación de campos vacíos (mostrar error en rojo si falta un dato) y un botón "Limpiar" que vacíe el formulario, sin tocar el resto del diseño. | Generó una variable de estado `mostrarError`, la lógica de validación con `isBlank()`, un mensaje de error en rojo, y un `OutlinedButton` con la función de limpiar los 3 campos. | Acepté la lógica de validación tal cual. Corregí el texto del mensaje de error para que sea más corto y claro, agregué un comentario explicando por qué usé `isBlank()`, y cambié el texto del botón de "LIMPIAR" a "LIMPIAR FORMULARIO" para que sea más descriptivo. |



\*\*Nota:\*\* Se usó Claude en lugar de Gemini porque no tuve acceso a Gemini en el momento del laboratorio.

que no tuve acceso a Gemini en el momento del laboratorio.

