# CentroTareas

App Android en Kotlin y Jetpack Compose para practicar corrutinas con una tarea que muestra progreso, un cálculo de números primos y dos cargas simuladas.

## Arquitectura

```text
PantallaTareas ──eventos──► TareasViewModel ──► TareasRepository
PantallaTareas ◄─StateFlow─ TareasViewModel      TareasRepositoryLocal
```

```text
com.meneses.centrotareas/
├── MainActivity.kt
├── data/       # Contrato y repositorio local
├── model/      # Estados de primos y cargas
├── ui/         # Pantalla y tema
└── viewmodel/  # ViewModel y fábrica
```

## Observación del paso 1

En la versión inicial, la espera bloquea el hilo principal durante cuatro segundos. El cambio a «Trabajando...» queda pendiente de dibujarse y el botón «Tócame» deja de responder durante la espera. Si el bloqueo dura diez segundos y Android detecta que la app no procesa eventos de entrada, podría mostrar un aviso de aplicación que no responde (ANR).

## Para pensar

1. **¿Por qué no se veía «Trabajando...»?** La pantalla necesita que el hilo principal quede libre para dibujar el estado nuevo.
2. **¿Qué diferencia hay entre dormir un hilo y usar delay?** Dormir un hilo lo ocupa durante la espera. delay suspende la corrutina y libera el hilo para que atienda otras tareas.
3. **¿Por qué Dispatchers.Default y Dispatchers.IO?** Default se usa para cálculos que ocupan CPU. IO se usa para operaciones de entrada y salida que bloquean, como una llamada de red síncrona. Las cargas simuladas de esta app usan delay.
4. **¿Qué hace viewModelScope al cerrar la pantalla?** Cancela sus corrutinas cuando se libera el ViewModel. Durante una rotación Android conserva el ViewModel.
5. **¿Por qué async tarda cerca de tres segundos?** Las esperas de dos y tres segundos se solapan. Si una carga falla, coroutineScope cancela el trabajo hermano pendiente y el ViewModel muestra el error.

## Pruebas y capturas

Comprobar en el emulador y añadir una captura de cada estado:

- [ ] Barra de progreso en marcha mientras «Tócame» sigue sumando.
- [ ] Tarea cancelada con la barra detenida y el botón «Iniciar» disponible.
- [ ] Primos hasta 1000 con resultado 168.
- [ ] Comparación de cargas, aproximadamente 5000 ms y 3000 ms.
- [ ] Error simulado con «Reintentar». Desactivar el interruptor y comprobar el éxito al reintentar.

Comprobar también el filtro HILO en Logcat, la rotación durante una tarea y que pulsar «Iniciar» repetidamente no cree operaciones duplicadas.
