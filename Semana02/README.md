# Laboratorio 02 - Carrito de Compras en Kotlin

**Nombre:** Pamela Meneses Mayhua

## Descripción

Este programa simula el funcionamiento de un carrito de compras en Kotlin. 
Permite agregar productos con nombre, precio y cantidad, calcular el subtotal, 
el IGV (18%), el total a pagar, identificar el producto más caro, y aplicar un descuento automático según el monto total de la compra.

## Funciones implementadas

- `calcularSubtotal`: suma el precio por cantidad de todos los productos del carrito.
- `calcularIGV`: calcula el 18% del subtotal.
- `calcularTotal`: suma el subtotal más el IGV.
- `mostrarDetalle`: imprime el detalle del carrito con columnas alineadas y montos con 2 decimales.
- `calcularDescuento`: aplica 5% de descuento si el total supera S/3000, o 10% si supera S/5000, usando una estructura `when`.

## val vs var

`val` (Inmutable): protege los datos contra cambios accidentales.
En mi `data class Producto`, uso `val` para `nombre` y `precio` porque una vez que un producto se crea, su nombre y su precio de venta no deberían cambiar durante la ejecución del programa.

`var` (Mutable): permite reasignar valores cuando el estado realmente debe cambiar.
Uso `var` para `cantidad` porque el cliente sí puede agregar o quitar unidades del mismo producto mientras compra.

Si intento cambiar el precio después de crear el producto
(por ejemplo, `producto.precio = 3000.0`), Kotlin no me deja — da un error de compilación: `Val cannot be reassigned`, 
porque protege esa variable de ser modificada una vez asignada.

## Evidencia

<img width="1010" height="620" alt="image" src="https://github.com/user-attachments/assets/cf6674c9-7e93-4f10-90b3-0db70c4793d7" />
