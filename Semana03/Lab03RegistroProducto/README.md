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

