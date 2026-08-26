# Laboratorio 02 - Carrito de Compras con IA (Herencia y Polimorfismo)

**Nombre:** Pamela Meneses Mayhua

## Descripcion

Este programa simula un carrito de compras aplicando Programacion Orientada a Objetos: usa una clase abstracta como base y varias subclases que heredan de ella, cada una con su propio calculo de precio segun el tipo de producto.

## Conceptos de POO implementados

- **Clase abstracta:** `Producto` define lo que todo producto debe tener (nombre, precio base, cantidad) y declara metodos abstractos que cada subclase debe implementar.
- **Herencia:** `ProductoElectronico` y `ProductoAccesorio` heredan de `Producto` usando la sintaxis `: Producto(...)`.
- **Polimorfismo:** cada subclase implementa `calcularPrecioUnitario()` y `categoria()` de forma distinta (Electronico aplica 18% de IGV, Accesorio no aplica impuesto), y el programa las trata a todas como `Producto` sin distinguir el tipo en el codigo.

## Funciones implementadas

- `calcularPrecioUnitario()`: calculado de forma distinta en cada subclase (polimorfismo).
- `calcularSubtotal()`: metodo heredado, compartido por todas las subclases.
- Busqueda de producto con `find`.
- Producto mas caro con `maxByOrNull`.
- Descuento automatico segun el monto total.

## Evidencia

(agregar captura del resultado en consola)
