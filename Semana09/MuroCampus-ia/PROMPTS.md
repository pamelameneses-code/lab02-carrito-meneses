# Registro de asistencia de IA

Las mejoras se implementaron con Codex. Este registro recoge la solicitud real y las decisiones tomadas. Gemini en Android Studio queda pendiente para el requisito específico del laboratorio.

| Prompt que escribí | Qué respondió la IA | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| «ahora cambialo a la rama mejora ia y crea un arcivo dentro que debe ser MuroCampus-ia y ahi dentro de eso sube los comit que pide el laboratorio que pdorias implementar en mejora ia de la semana 9 todavia no subas los comits solo guardalo en el local okei» | Codex implementó búsqueda por título, un interceptor de OkHttp y paginación de 20 en 20. | La búsqueda filtra los datos descargados. El interceptor fija `Accept: application/json`. La paginación usa `_start` y `_limit` con **Cargar más** y permite reintentar una página fallida. | La búsqueda local evita peticiones por cada carácter. El encabezado se configura una sola vez. El repositorio mantiene el acceso a la API y los errores se muestran sin perder las páginas ya cargadas. |
