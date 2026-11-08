# Uso de IA — Fase 2 de rickmorty-ia

## Consulta 1: definir qué pide la guía

- **Fecha:** 8 de noviembre de 2026.
- **Herramienta:** OpenAI Codex.
- **Prompt:** «bien ahora en mi guia que pide para implementar en el mejora con ia».
- **Qué respondió:** la fase incluye mejoras de interfaz y errores, al menos tres pruebas unitarias del ViewModel con un repositorio falso, al menos tres commits en `mejora-ia` y este registro de consultas.
- **Qué se usó y por qué:** se organizó el trabajo en interfaz, errores y pruebas. Se conservó la implementación base de Favoritos.
- **Qué se aprendió:** el buscador es una mejora elegida para este proyecto; la guía no exige una función de búsqueda en particular.

## Consulta 2: rediseñar Catálogo y Favoritos

- **Fecha:** 8 de noviembre de 2026.
- **Herramienta:** OpenAI Codex.
- **Prompt:** «puedes hacerlo mas q nada la interaz que halla otra ventana para ver los favoritos que la parte cuando busques un personaje sea vea bien bonito maso menos asi como mis anteriores capturas q te mande del cafe campus algo asi».
- **Qué respondió:** propuso navegación inferior, tarjetas con fotos y corazones, una pantalla propia para Favoritos y búsqueda por nombre.
- **Qué se usó y por qué:** se añadieron las pestañas Catálogo y Favoritos, con Panel Admin visible solo para ese rol. El buscador filtra el catálogo local sin volver a descargarlo. Los corazones usan los favoritos persistentes de Room.
- **Qué se aprendió:** el mismo componente de tarjeta puede usarse en el catálogo y en Favoritos. El ViewModel filtra los datos y Compose muestra el resultado.
- **Comprobación:** las capturas del teléfono muestran las tarjetas, la pestaña Favoritos y el acceso al panel con el rol administrador.

## Consulta 3: terminar errores, pruebas y documentación

- **Fecha:** 8 de noviembre de 2026.
- **Herramienta:** OpenAI Codex.
- **Prompt:** «ya has lo q falta en si», después de enumerar el manejo de errores, las pruebas del ViewModel y `PROMPTS.md`.
- **Qué respondió:** centralizar los mensajes y verificar `ListaViewModel` con un repositorio y un notificador falsos.
- **Qué se usó y por qué:** se distinguen falta de conexión, tiempo de espera, recurso no encontrado, exceso de solicitudes y fallos del servidor. El catálogo conserva los datos guardados si falla la sincronización.
- **Qué se aprendió:** con dependencias falsas se puede probar carga, aviso, caché, reintento y búsqueda sin depender de Firebase, un teléfono o una llamada real a la API.
- **Comprobación:** Gradle compiló el código, los recursos y las pruebas; las seis pruebas de `ListaViewModelTest` pasaron con JUnit 4.13.2.

Para repetir las pruebas en Windows: `gradlew.bat :app:testDebugUnitTest` desde la carpeta del proyecto.
