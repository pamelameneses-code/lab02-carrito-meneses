# Rick y Morty · Catálogo de personajes vivos

Laboratorio 11–12 de Programación en Móviles. Proyecto individual en `Semana12/RickMortyCatalogo`.

La lista usa la primera página de `GET /api/character?status=alive`. La ficha usa `GET /api/character/{id}`. Retrofit descarga los datos, el repositorio los convierte y Room alimenta la lista y los favoritos. Firebase Authentication mantiene la sesión y Firestore proporciona el rol de cada cuenta.

## Abrir y compilar

1. En Android Studio, abre la carpeta `Semana12/RickMortyCatalogo`, que contiene `settings.gradle.kts`.
2. Usa el SDK de Android 37 y el JDK de Gradle configurado por el proyecto. Pulsa **Sync Project with Gradle Files**.
3. Completa la configuración de Firebase para probar el ingreso. El proyecto también permite compilar sin el archivo local de Firebase y muestra un aviso de configuración en el login.
4. En un emulador con Google Play, pulsa **Run**. Para generar el APK de depuración desde PowerShell, ejecuta `./gradlew.bat :app:assembleDebug`. El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

## Configurar Firebase y las cuentas de prueba

1. Crea un proyecto en [Firebase Console](https://console.firebase.google.com/) y registra una app Android con `com.tecsup.rickymorty`.
2. Descarga `google-services.json` y guárdalo en `app/`. El archivo está excluido mediante `.gitignore`. Conserva las contraseñas de prueba fuera de Git.
3. En **Authentication → Sign-in method**, activa **Correo electrónico/contraseña**. En **Usuarios**, crea una cuenta normal y una de administración.
4. En **Firestore Database**, crea la colección `usuarios`. Para cada cuenta crea un documento cuyo ID sea el UID de Authentication. Agrega los campos de texto `nombre` y `rol`. Usa `usuario` en la cuenta normal y `admin` en la otra.
5. En **Firestore → Reglas**, publica el contenido de `firestore.rules`. Cada usuario autenticado puede leer su documento. Los clientes tienen las escrituras deshabilitadas.
6. Vuelve a sincronizar Gradle y ejecuta la app.

## Cómo se organiza

- `ui/` contiene las pantallas Compose y sus ViewModels. Las pantallas observan `UiState` y llaman a los ViewModels.
- `data/remote/` define el cliente Retrofit y los DTO. `data/local/` contiene la entidad, el DAO y la base Room.
- `data/repository/` conecta la API con Room y conserva los favoritos durante la sincronización. `data/auth/` consulta Firebase y Firestore.
- `domain/model/` contiene los modelos de personaje y sesión. `notification/` envía el aviso mediante un canal y abre la app al pulsarlo.
- `AppContainer` crea las dependencias y la fábrica de ViewModels. Vive durante toda la ejecución de la app.

## Pruebas de aceptación

| Caso | Qué comprobar |
| --- | --- |
| PA-01 | La cuenta normal entra al catálogo y ve Favoritos. |
| PA-02 | La cuenta de administración también tiene acceso al panel. |
| PA-03 | Una contraseña incorrecta muestra un error. |
| PA-04 | Correo o contraseña vacíos muestran validación local. |
| PA-05 | La primera descarga muestra la lista y la notificación si se concede el permiso. |
| PA-06 | Tocar un personaje abre su ficha con datos del segundo endpoint. |
| PA-07 | Un favorito persiste al cerrar y reabrir la app. |
| PA-08 | En modo avión, el catálogo previamente descargado se muestra con un aviso. |
| PA-09 | Sin caché ni conexión aparece Reintentar. Para conservar la sesión de prueba, limpia la caché desde el panel antes de activar modo avión. |
| PA-10 | Sincronizar conserva los favoritos marcados. |
| PA-11 | Limpiar caché pone los totales en cero. Sincronizar vuelve a descargar. |
| PA-12 | Cerrar sesión devuelve al login, incluso al reabrir. |
| PA-13 | Girar la pantalla conserva el estado del catálogo. |
| PA-14 | Al negar el permiso de notificaciones, el catálogo sigue funcionando. |

## Capturas de entrega

Después de configurar las dos cuentas, registra el login, la lista, la ficha de un personaje, Favoritos, el panel de administrador y la notificación de sincronización. Guarda las capturas en `docs/capturas/` y enlázalas aquí.

## Reparto del trabajo

Entrega individual de Pamela Meneses. La implementación de la fase base de esta copia se preparó con asistencia de Codex a petición de la estudiante. La guía del curso define `main` como fase sin IA.
