\# Laboratorio 03: Registro de Notas — Jetpack Compose



Aplicación móvil para el registro y cálculo del promedio ponderado de 4 asignaturas con indicadores visuales de estado.



\---



\## Descripción

Aplicación en Jetpack Compose que calcula el promedio ponderado de 4 cursos 

(Fundamentos de Programación 20%, POO 25%, Programación en Móviles 30%, Base de 

Datos 25%). El usuario ajusta la nota de cada curso con un Slider (0-20), puede 

activar un Switch para redondear el promedio final, y debe marcar un Checkbox de 

confirmación para habilitar el botón "CALCULAR PROMEDIO". Al calcular, se muestra 

una Card con el promedio ponderado, el promedio final y una observación 

(Excelente/Aprobado/En recuperación/Desaprobado) con un color distinto según el 

rango de la nota.



\##  Especificaciones de Cursos y Pesos



\* \*\*Fundamentos de Programación:\*\* Peso 20%

\* \*\*Programación Orientada a Objetos:\*\* Peso 25%

\* \*\*Programación en Móviles:\*\* Peso 30%

\* \*\*Base de Datos:\*\* Peso 25%



\---



\##  Funcionalidades Implementadas



1\. \*\*Asignación de Notas (Sliders):\*\* Control de selección de notas de 0 a 20 en pasos enteros.

2\. \*\*Redondeo dinámico (Switch):\*\* Alterna entre promedio exacto con decimales o redondeado al entero más cercano (`roundToInt`).

3\. \*\*Confirmación de Seguridad (Checkbox):\*\* Habilita el botón de cálculo únicamente si las notas son confirmadas.

4\. \*\*Resultado y Observación (Card \& AssistChip):\*\* Muestra el promedio ponderado y un chip con color según el rango:

&#x20;  \* \*\*EXCELENTE (17 - 20):\*\* Color Verde Oscuro

&#x20;  \* \*\*APROBADO (13 - 16.99):\*\* Color Verde

&#x20;  \* \*\*EN RECUPERACIÓN (10 - 12.99):\*\* Color Ámbar

&#x20;  \* \*\*DESAPROBADO (0 - 9.99):\*\* Color Rojo



\---



\##  Evidencia de Funcionamiento



!\[Pantalla inicial](captura1.png)

!\[Resultado calculado](captura2.png)

\---



\*\*Desarrollado por:\*\* Pamela Meneses

\*\*Asignatura:\*\* Desarrollo de Aplicaciones Móviles

