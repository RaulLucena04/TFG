# Respuestas — 100 preguntas para la defensa del TFG

*Respuestas orientativas para estudio; adáptalas a lo que realmente implementaste y documentaste en tu memoria.*

---

## 1–10 · Metodología, alcance y rigor

**1.** La aportación no es «otra app de apuestas», sino un **sistema integrado** (backend + persistencia + dos clientes) con un **protocolo de comunicación definido** (TCP + tramas + acciones), reglas de negocio propias (partidos, apuestas, resolución, tienda) y documentación del flujo. Lo defendible es la **integración**, las **decisiones técnicas** y la **trazabilidad** del dato, no competir con productos comerciales cerrados.

**2.** Ejemplo: «Un único backend puede servir a escritorio y móvil con el mismo contrato». Se valida si **ambos clientes** funcionan contra el mismo servidor y las **acciones** documentadas coinciden con el comportamiento observado (login, listados, finalizar partido). Si no hubo hipótesis formal, admítelo: el TFG fue **iterativo** y la validación fue **prueba manual + compilación + revisión de código**.

**3.** Frase tipo: «Centralizar la gestión de partidos y apuestas de un torneo simulado con persistencia y clientes multiplataforma». Un stakeholder lo adoptaría si reduce **coste de operación** (un solo servidor) o sirve como **base** para un piloto interno.

**4.** Prometiste seguridad básica (contraseñas no en claro), usabilidad (configuración de servidor en Android) y mantenibilidad (capas socket/dispatcher/servicio). Mide lo que puedas demostrar: **tiempo de respuesta** en LAN, **logs** del servidor, **tests** de arranque del backend, checklist de flujos críticos.

**5.** Alcance pequeño: es un TFG acotado en tiempo; profundizas en **integración real**. Alcance grande: admite **MVP** y deja extensiones (TLS, notificaciones, tests E2E) como trabajo futuro explícito en conclusiones.

**6.** Riesgos típicos: entorno Windows/OneDrive con Gradle, MySQL no levantado, IP incorrecta en móvil físico, desalineación cliente–servidor. El que más duele suele ser **red + despliegue**; documenta cómo lo resolviste.

**7.** Ejemplos: tests automatizados más temprano, unificar solo Compose o solo Fragments, CI desde el principio, menos duplicación de modelos entre plataformas.

**8.** Memoria con diagramas de despliegue y secuencia; `README` del repo; documentos de **protocolo** y **flujo**; comentarios en clases críticas (`SocketDispatcher`, servicios transaccionales).

**9.** Limitaciones honestas: sin TLS, sin horizonte de escala masiva, datos sintéticos, poca cobertura de tests, sin panel de auditoría completo. La validez académica viene de **coherencia** y **demostración** dentro de esas fronteras.

**10.** Integración significa que **cambias una regla en el backend** y **afecta a ambos clientes**; que el **protocolo** es tuyo; que hay **persistencia** y **flujos** no triviales (finalizar partido + apuestas). Un tutorial copiado no tiene esa trazabilidad ni tus decisiones documentadas.

---

## 11–20 · Arquitectura y diseño

**11.** Típico: PC con MySQL + proceso Spring Boot (puerto app por defecto si hay web; **socket 9090**); clientes Android y JavaFX en otras máquinas o emulador; flechas: clientes → TCP:9090 → servicios → JDBC → MySQL.

**12.** Mejora: **despliegue y evolución** del contrato en un solo lugar (servidor). Empeora: **tres codebases** a mantener; mitigación con DTOs claros y documentación del protocolo.

**13.** MVVM si usas `ViewModel` + estado observable y pantallas «tontas»; si la lógica está repartida sin criterio, reconócelo y propón refactor. La defensa gana con **autocrítica**.

**14.** Primero el **pool de hilos** del socket o la **BD** (conexiones, locks); el `ServerSocket` acepta muchas conexiones pero el cuello suele ser **I/O de BD** y **lógica transaccional**.

**15.** Negocio en **servicios** y `SocketDispatcher`; presentación solo orquesta llamadas y estado UI. Si hay reglas en la UI, son validaciones **UX**, no fuente de verdad.

**16.** Ejemplo: modelos duplicados User/Usuario/JSON; mitigación: generación de código, OpenAPI futuro, o capa DTO compartida (complejo en TFG).

**17.** Es **deuda razonable** en TFG: pragmatismo. Ideal sería contrato único generado; defiendes **coste/beneficio** del tiempo disponible.

**18.** Invariantes: «no se resuelven apuestas dos veces», «puntos consistentes con resultado»; en código: **transacción** en finalización + resolución; lo que queda en papel: auditoría legal completa.

**19.** Rechazaste REST en el mismo puerto que el socket para no mezclar semánticas; o rechazaste WebSockets porque tu caso es **request/response** sin push servidor.

**20.** Ejemplo: **S**ingle Responsibility: `SocketClientHandler` solo I/O, dispatcher solo enrutado; **O**pen/Closed: nuevas acciones tocando dispatcher; admite acoplamientos donde no aplicaste OCP al 100%.

---

## 21–30 · Red, sockets y protocolo

**21.** TCP da **flujo fiable y ordenado**; UDP no garantiza entrega ni orden para mensajes de aplicación tipo petición/respuesta con JSON.

**22.** JSON puede contener saltos de línea; sin longitud fijas mal el parsing y mezclas mensajes en el stream TCP.

**23.** `DataInputStream.readInt()` es **big-endian** por especificación Java; cliente y servidor deben usar el mismo convenio; si uno escribiera LE, leerías longitudes absurdas (similar al bug «POST» interpretado como entero).

**24.** Sin push nativo: **polling** del cliente tras acciones admin, o segundo canal (SSE/WebSocket) en otro puerto; o mensajes «long poll» simulados en el mismo socket (diseño complejo).

**25.** Un socket por petición: **simple** y aísla fallos; persistente: menos overhead TCP pero más estado y timeouts en el handler.

**26.** Los primeros bytes forman palabras HTTP (`POST`, `GET`); log y mensaje de diagnóstico; comparar con trama esperada `{` o tamaño razonable.

**27.** Fin de stream inesperado: cerraron el socket antes de terminar la trama, protocolo mezclado (HTTP vs tramas), o timeout; hay que correlacionar con logs del servidor.

**28.** Campo `protocolVersion` en el JSON o prefijo de acción `v2.user.login`; clientes antiguos ignoran acciones nuevas; servidor mantiene ramas compatibles un tiempo.

**29.** En LAN: **sniffing** en Wi‑Fi sin cifrado, MITM en redes hostiles; asumiste red de laboratorio **semi confiable**; en producción exigirías TLS y autenticación fuerte.

**30.** Aceptable en TFG/demo controlada; indefendible con datos reales de pago o exposición en Internet sin cifrado.

---

## 31–40 · Backend (Spring, JPA)

**31.** `@Transactional` agrupa operaciones en **una unidad atómica**: commit único o rollback si hay excepción no capturada; evita estados donde el partido quedó finalizado y las apuestas a medias.

**32.** `REQUIRED` (por defecto) **une** a la transacción existente o crea una nueva; importa si llamas a métodos `@Transactional` anidados: comparten contexto salvo que configures `REQUIRES_NEW`.

**33.** Respuesta honesta mirando tu código: si todo está en una transacción, **rollback global**; si capturas excepciones dentro sin propagar, podría haber **inconsistencia** — idealmente propagas o compensas explícitamente.

**34.** Evitas perder actualizaciones: otra petición pudo cambiar puntos; **recargar** asegura sumar sobre el estado más reciente o detectar conflicto.

**35.** Hibernate trackea cambios en entidades gestionadas y genera UPDATE al commit; puede generar **updates inesperados** si mutas entidades cargadas sin querer.

**36.** Lazy por defecto en asociaciones; N+1 si listas partidos y accedes a equipos en bucle sin `JOIN FETCH` o DTO; demuestra con `show-sql` y cuenta de queries.

**37.** JPA y BD pueden usar **bloqueo optimista** (`@Version`) o pesimista; sin versión, la última escritura gana; riesgo de **lost update** si no diseñas bloqueo.

**38.** Productividad y menos boilerplate; coste: magia y performance menos predecible; para TFG el trade-off suele ser razonable.

**39.** Validación de negocio en **servicio**; entidad con invariantes mínimos; dispatcher solo **parseo y delegación** para no inflar la capa de transporte.

**40.** Mapa `action → Command`, o clases por dominio (`UserActions`, `MatchActions`); dispatcher solo despacha.

---

## 41–50 · Base de datos

**41.** Requisito del proyecto, tooling del equipo, o familiaridad; PostgreSQL sería igualmente válido; SQLite si fuera single-user local.

**42.** Índices en **FK** (`usuario_id`, `partido_id`), en campos de búsqueda frecuente (`username` único); demuestra `EXPLAIN` en consultas lentas.

**43.** Flyway/Liquibase con migraciones versionadas; nunca `ddl-auto=update` ciego en prod.

**44.** Podrías duplicar datos de equipo en partido para histórico; aceptable si documentas **denormalización intencional** para snapshot de nombres.

**45.** Restricción **UNIQUE** compuesta, o comprobación en servicio antes de insertar; idealmente ambos.

**46.** Hibernate puede **alterar tablas** automáticamente; riesgo de pérdida de datos o migraciones impredecibles; en prod migraciones explícitas.

**47.** `mysqldump` de `nba_app`, zip, y restore en otra máquina; script documentado en README.

**48.** PII: email, usuario; contraseñas con hash; minimizar logs con datos sensibles; PayPal en sandbox.

**49.** Depende de `ON DELETE RESTRICT/CASCADE`; si no hay FK física, riesgo de huérfanos — defiende integridad referencial explícita.

**50.** Nombres genéricos «Equipo A», estadísticas inventadas; no usar logos/marcas registradas sin permiso; cita uso académico en memoria.

---

## 51–60 · Seguridad

**51.** Idealmente **BCrypt** o similar; si hay legado en texto plano, admítelo como **migración** y mitigación (forzar cambio de contraseña).

**52.** Tablas precalculadas hash→contraseña; mitigación: **salt** por usuario + algoritmo lento.

**53.** CSRF ataca sesiones web en navegador; tu socket no usa cookies de sesión del mismo modelo; riesgo distinto (token en payload si lo añadieras).

**54.** JPA parametriza queries; no concatenar SQL con input usuario; riesgo bajo si no usas `@Query` nativo inseguro.

**55.** Cualquier máquina en la red puede intentar conectar; en cafetería es grave; mitigación firewall, bind a interfaz LAN, autenticación fuerte.

**56.** Tras login, **token JWT** o sesión UUID en cada mensaje; servidor valida token en cache/BD sin revalidar password.

**57.** Limitar intentos por IP/usuario, backoff, captcha en entorno web; en socket, contador en servidor.

**58.** Cleartext permite HTTP sin TLS en desarrollo; inaceptable con credenciales reales en Internet público.

**59.** Revocar credenciales en PayPal, rotar en `application.properties`, **borrar del historial Git** con `git filter-repo` y considerar secretos filtrados comprometidos para siempre.

**60.** Ejemplo: **Spoofing** de cliente (credenciales robadas), **Tampering** de payload JSON sin firma, **Repudiation** sin logs; la más seria suele ser **credenciales débiles + red abierta**.

---

## 61–70 · Android

**61.** `AsyncTask` está deprecado; corrutinas son el modelo moderno, composables con cancelación e integración con lifecycle.

**62.** `coroutineScope` cancela hijos al destruir el scope; evitas fugas y trabajo innecesario al salir de la pantalla.

**63.** Gson no serializa `LocalDateTime` ISO-8601 igual que Jackson por defecto; **TypeAdapter** o formato string acordado con backend.

**64.** `remember` se pierde en rotación/process death; `rememberSaveable` sobrevive a muchos casos de recreación de Activity.

**65.** `10.0.2.2` mapea al host del PC desde el emulador; en físico falla con timeout/refused si no pones la IP LAN del PC.

**66.** Red en main thread lanza `NetworkOnMainThreadException` en APIs modernas; ANR si bloqueas UI de otra forma.

**67.** ViewModel sobrevive a rotación; no pongas **Context** de Activity ni vistas; estado UI derivado del modelo.

**68.** `waitUntil`, semánticas de test, congelar animaciones en opciones de test, ids semánticos.

**69.** `AppContext` da `ApplicationContext` global; riesgo: retener **Activity**, o fugas si pasas contextos incorrectos a singletons.

**70.** Defendible si migras gradualmente; deuda si duplica navegación sin plan; di que el TFG priorizó **entregar funcionalidad** con convivencia temporal.

---

## 71–75 · JavaFX y comparación

**71.** Menor footprint que Chromium embebido; buen encaje si el equipo domina Java; Electron brilla en UI web compartida con navegador.

**72.** `Platform.runLater` para tocar UI desde hilos de red; servicios `Task`/`Service` de JavaFX.

**73.** FXML separa layout de lógica; acelera iteración visual; coste: navegación entre archivos.

**74.** Comparten **mismo JSON de acciones** y tramas; duele duplicar modelos y validaciones en Java y Kotlin.

**75.** Reproduce con **tercera herramienta** (cliente mínimo, logs en servidor); si el servidor responde bien con prueba manual, el bug es del cliente.

---

## 76–80 · Calidad y DevOps

**76.** Di el número real (p. ej. solo test de contexto Spring); honestidad: «baja cobertura, mitigada con pruebas manuales documentadas».

**77.** Login + listar partidos + crear apuesta + ver ranking en secuencia con datos conocidos.

**78.** Jobs matrix: JDK 17, `mvn test`, `./gradlew assembleDebug`, cache de dependencias.

**79.** `pom.xml`/`build.gradle` con versiones fijas + wrapper de Gradle/Maven committed.

**80.** Bugs bloqueantes, vulnerabilidades de dependencias; ignorar estilo menor si no afecta legibilidad (con justificación).

---

## 81–85 · Rendimiento

**81.** Cached pool crece con carga; cuello: BD o CPU en JSON; sin medición, admite que es **estimación**.

**82.** Deberías limitar tamaño de frame en servidor (como ya haces con tope razonable) y rechazar; evita OOM.

**83.** Cada hilo de socket pide conexión del pool; agotar pool → esperas; dimensionar pool según carga esperada.

**84.** En payloads pequeños la diferencia es marginal; red y BD dominan.

**85.** `jstack`, VisualVM, profiler de IDE, logs de tiempo por acción en dispatcher.

---

## 86–90 · PayPal y negocio

**86.** Sandbox no mueve dinero real ni carga legal completa; APIs y errores pueden diferir.

**87.** Ideal: transacción única o **saga** con compensación; idempotencia con id de operación único.

**88.** Deshabilitar botón tras click, debounce, token de idempotencia en servidor.

**89.** Fuera de alcance académico; reconoces limitación y propondrías fuentes oficiales de resultados y moderación.

**90.** Log de administrador + timestamp + payload de `match.finalize`; tabla de auditoría si existiera.

---

## 91–95 · Ética y legal

**91.** Uso académico no comercial suele ser más tolerable; marcas y logos son más delicados; cita **fair use** y recomendación del tutor; no afirmar legalidad sin asesoría.

**92.** Consentimiento informado, minimización de datos, anonimización en demos públicas.

**93.** Aclaras que son **puntos ficticios** / simulación; disclaimer de no juego con dinero real en esta versión.

**94.** Ranking por puntos favorece volumen de aciertos vs calidad; alternativas: ELO, porcentaje de acierto ponderado.

**95.** Harassing, apuestas ilegales: límites técnicos (KYC fuera de alcance), términos de uso, desactivación de features peligrosas.

---

## 96–100 · Trampas de tribunal

**96.** Ejemplo: «Centralizo torneo simulado NBA con apuestas ficticias; un backend Spring con MySQL sirve a Android y escritorio por un protocolo TCP con JSON; el admin cierra partidos y el sistema resuelve apuestas y puntos de forma persistente.»

**97.** Vídeo corto grabado, capturas, diagramas en PDF, o emulador + backend en el mismo portátil sin Wi‑Fi externa.

**98.** Ejemplo: duplicación de lógica entre clientes; no refactorizaste por **deadline**; plan de extracción a módulo común o contrato generado.

**99.** Elige una fuerte (p. ej. «¿Cómo garantizas consistencia bajo concurrencia?») y respóndela con transacciones + límites del diseño.

**100.** Vídeo de respaldo, APK preinstalada, base de datos de demo embebida en script, o narración paso a paso con logs si la demo falla.

---

*Estudia adaptando cada respuesta a tu memoria y al código real del repositorio.*
