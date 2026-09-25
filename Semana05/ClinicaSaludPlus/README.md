# Clínica Salud+ - Tarea de Evaluación (Semanas 1-6)

**Curso:** Programación en Móviles (Tecsup)
**Docente:** Juan León S.
**Modalidad:** Individual — Fase 1 (sin IA) + Fase 2 (con IA)

**Requerimientos Funcionales — Proyecto Clínica Salud+**
**RF01: **Agendamiento Dinámico de Citas Médicas (ScheduleScreen & ConfirmationScreen)

Descripción: El sistema debe permitir al usuario seleccionar un médico, una fecha y un horario disponible. Al confirmar la reserva, la cita debe registrarse dinámicamente en el estado global de la aplicación (MockData.appointmentsList) asignándole un identificador único y evitando la duplicación de registros mediante una validación previa de disponibilidad.

**RF02: **Visualización y Gestión de Citas Próximas (MyAppointmentsScreen)

Descripción: El usuario debe poder visualizar en tiempo real el listado completo de sus citas médicas agendadas, mostrando la información relevante (nombre del médico, especialidad, fecha y hora). La pantalla debe refrescarse automáticamente al agregar o eliminar registros.

**RF03:** Cancelación Individual y Masiva de Citas (MyAppointmentsScreen)

Descripción: La aplicación debe permitir la cancelación de una cita individual mediante un botón específico con confirmación, eliminando el registro del estado global. Asimismo, debe ofrecer la funcionalidad de "Borrar todas" para limpiar completamente la lista de citas agendadas de forma masiva.

**RF04:** Consulta de Historial Médico Pasado (MedicalHistoryScreen)

Descripción: El sistema debe proporcionar una pantalla de consulta de historial médico de solo lectura donde el usuario pueda revisar el registro de atenciones anteriores completadas (incluyendo diagnósticos y prescripciones médicas), manteniendo este listado independiente y desvinculado del flujo activo de agendamiento de nuevas citas.

## Descripción

App de reserva de citas médicas con navegación secuencial
(Inicio → Perfil del médico → Agendar cita → Confirmación) y
navegación secundaria mediante NavigationDrawer (Inicio, Mis citas,
Historial médico).

## Pantallas

- **Inicio**: chips de especialidad (LazyRow) + lista de médicos (LazyColumn)
- **Perfil del médico**: datos del médico elegido, botón "Agendar cita"
- **Agendar cita**: selección de fecha y hora (mínimo 3 opciones cada una)
- **Confirmación**: resumen de la cita agendada
- **Mis citas**: lista de citas agendadas con su estado
- **Historial médico**: tercer destino del menú lateral

## Tecnologías

- Kotlin, Jetpack Compose, Material 3
- Navigation Compose
- Estado con remember/mutableStateOf (sin ViewModel)

## Resultado final

_(agregar aquí las capturas de pantalla del proyecto corriendo)_
