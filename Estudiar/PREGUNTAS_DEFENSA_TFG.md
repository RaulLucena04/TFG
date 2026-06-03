# 100 preguntas exigentes para la defensa del TFG

*Enfocadas a proyectos tipo ingeniería del software / aplicación cliente–servidor (referencia: NBA Predictor, sockets, Spring Boot, Android, MySQL).*

---

## Metodología, alcance y rigor

1. ¿Cuál es exactamente la **aportación original** de tu TFG frente a usar tres aplicaciones existentes que ya hagan lo mismo?
2. ¿Qué **hipótesis** planteaste al inicio y cómo las **validaste o refutaste** con evidencia?
3. Define en una frase el **problema** que resuelves y por qué un stakeholder lo pagaría o lo adoptaría.
4. ¿Qué **requisitos no funcionales** (rendimiento, seguridad, mantenibilidad) prometiste y cuáles **mediste** de verdad?
5. ¿Por qué el **alcance** que elegiste es defendible frente a un tribunal que diga «eso es poco» o «eso es demasiado»?
6. ¿Qué **riesgos** identificaste en la planificación y cuál fue el que más te afectó en la práctica?
7. ¿Qué harías distinto si **rebobinaras** el calendario del TFG con el conocimiento actual?
8. ¿Cómo documentaste las **decisiones de arquitectura** (ADRs, diagramas, memoria) para que otro desarrollador te entienda en 2028?
9. ¿Qué **limitaciones** admite tu trabajo sin que se caiga la validez del proyecto?
10. ¿Cómo demuestras que no es un **«tutorial ensamblado»** sino un sistema integrado con criterio propio?

---

## Arquitectura y diseño

11. Dibuja mentalmente el **diagrama de despliegue**: qué proceso corre en qué máquina y qué puertos usa cada uno.
12. ¿Por qué **separar** cliente Android, cliente escritorio y backend en tres artefactos mejora o empeora el mantenimiento?
13. ¿Qué patrón de arquitectura encaja mejor tu app Android (**MVVM**, **MVI**, «spaghetti con Compose») y por qué?
14. Si mañana debes soportar **100 000 usuarios concurrentes**, ¿qué componente revienta primero y por qué?
15. ¿Dónde está el **límite** entre «lógica de negocio» y «capa de presentación» en tu código real?
16. ¿Qué **acoplamientos** tienes entre módulos que te gustaría eliminar y qué coste tendría?
17. ¿Defenderías en serio usar **el mismo modelo de dominio** serializado en tres lenguajes/plataformas o lo consideras deuda técnica?
18. ¿Qué **invariantes** del sistema (reglas que nunca deben romperse) garantizas en código y cuáles solo «en el papel»?
19. Explica una **decisión** que rechazaste (por ejemplo REST puro, GraphQL, cola de mensajes) con argumentos técnicos, no de moda.
20. Si un compañero dice que tu diseño viola **SOLID**, ¿qué le respondes con ejemplos concretos de tu repo?

---

## Red, sockets y protocolo

21. ¿Por qué **TCP** y no **UDP** para tu protocolo de aplicación?
22. ¿Qué problema resuelve el **prefijo de longitud** (int32 + JSON) que no resuelva «leer hasta newline»?
23. ¿En qué orden deben leerse **exactamente** los bytes en el wire y qué pasa si el cliente y el servidor usan **endianness** distinto?
24. ¿Tu protocolo es **sincrónico request/response**; cómo implementarías **notificaciones push** del servidor sin cambiar de transporte?
25. ¿Qué ventaja tiene **un socket por petición** frente a **un socket largo** con muchas peticiones en serie?
26. ¿Cómo detectarías en el servidor que el cliente envía **HTTP** por error al puerto del socket?
27. ¿Qué significa **«unexpected end of stream»** en OkHttp/Gson y por qué no siempre es «el servidor cayó»?
28. ¿Cómo versionarías el protocolo (`action` v1 vs v2) sin romper clientes antiguos?
29. ¿Qué **ataques** de red son relevantes en un TFG en LAN/Wi‑Fi doméstico y cuáles ignoraste de forma consciente?
30. ¿Por qué **no** usar TLS en el socket puede ser aceptable en tu contexto y cuándo sería indefendible?

---

## Backend (Spring, JPA, servicios)

31. ¿Qué hace `@Transactional` en `finalizarPartido` y qué **anomalías** evita frente a no usarlo?
32. ¿Cuál es la diferencia entre **`Propagation.REQUIRED`** y el valor por defecto en tu caso de uso?
33. Si `resolverApuestas` falla a mitad, ¿el partido queda finalizado, las apuestas a medias, o se hace rollback? Justifica con el código.
34. ¿Por qué **recargar el usuario desde BD** antes de sumar puntos puede ser necesario en entornos concurrentes?
35. ¿Qué es el **dirty checking** de Hibernate y cómo te ha mordido alguna vez?
36. ¿`FetchType.LAZY` vs `EAGER`: qué elegiste y qué **N+1** podrías demostrar con un log de SQL?
37. ¿Qué pasa si dos hilos del **pool de sockets** tocan la misma fila de `Usuario` a la vez?
38. ¿Por qué Spring Data **JpaRepository** y no JDBC a pelo ni jOOQ?
39. ¿Dónde validas reglas de negocio: **entidad**, **servicio**, **dispatcher**; y por qué no en los tres a la vez?
40. Si el **dispatcher** crece sin fin, ¿qué patrón aplicarías para mantenerlo (command, strategy, router por mapa)?

---

## Base de datos y datos

41. ¿Por qué **MySQL** y no PostgreSQL/SQLite para este TFG?
42. ¿Qué **índices** creaste o deberías crear para las consultas más frecuentes?
43. ¿Cómo migrarías el esquema en producción sin borrar datos de usuarios reales?
44. ¿Qué **anomalías** de normalización podrían existir en tu modelo `Partido`/`Apuesta` y las aceptas?
45. ¿Cómo garantizas que no haya **apuestas duplicadas** incoherentes con el mismo usuario y partido?
46. ¿Qué implica `ddl-auto=update` en producción y por qué es peligroso?
47. ¿Cómo harías **backup y restore** demostrable en 2 minutos ante el tribunal?
48. ¿Qué datos son **PII** en tu sistema y cómo los tratas (contraseñas, emails PayPal)?
49. Si borras un **equipo** referenciado por partidos, ¿qué política de integridad aplica tu BD?
50. ¿Cómo poblarías la BD con datos **realistas** pero libres de copyright de la NBA?

---

## Seguridad

51. ¿Cómo se almacenan las **contraseñas** y por qué no en texto plano «porque es un TFG»?
52. ¿Qué es un **rainbow table** y cómo lo mitigas?
53. ¿Qué es **CSRF** y aplica o no a tu cliente socket?
54. ¿Qué es **SQL injection** y cómo lo evitas con JPA/HQL parametrizado?
55. Si el socket escucha en `0.0.0.0`, ¿qué superficie de ataque abres en una red no confiable?
56. ¿Cómo autenticarías **peticiones socket** si no quisieras mandar usuario/contraseña en cada mensaje?
57. ¿Qué harías con **rate limiting** en login para frenar fuerza bruta?
58. ¿Por qué `usesCleartextTraffic` en Android puede ser necesario y cuándo sería inaceptable?
59. ¿Cómo rotarías **credenciales PayPal** si se filtran en un commit de Git?
60. ¿Qué amenaza modelas con **STRIDE** en tu sistema y cuál es la más seria?

---

## Cliente Android

61. ¿Por qué **corrutinas** y `Dispatchers.IO` para red y no `AsyncTask`?
62. ¿Qué es **structured concurrency** y cómo se relaciona con cancelar una petición al salir de una pantalla?
63. ¿Qué problemas da **Gson** con `LocalDateTime` y cómo lo resolviste?
64. ¿`remember` vs `rememberSaveable` en Compose: qué perderías si confundes uno con otro?
65. ¿Por qué `10.0.2.2` en el emulador y qué error típico da en **móvil físico** si no lo cambias?
66. ¿Cómo evitas **ANR** si hicieras operaciones de red en el hilo principal?
67. ¿Qué es **ViewModel** y qué estado no debería vivir ahí?
68. ¿Cómo pruebas la UI Compose **sin** flaky tests por animaciones?
69. ¿Qué hace `AppContext` y qué **fugas** de contexto podrías introducir?
70. ¿Por qué **Fragments** y **Compose** coexisten en tu proyecto y es defendible o es deuda?

---

## Cliente escritorio (JavaFX) y comparación

71. ¿Qué ventaja tiene JavaFX frente a **Electron** para tu caso?
72. ¿Cómo gestionas **hilos** en JavaFX sin congelar la UI?
73. ¿Por qué **FXML** + controladores y no todo en código?
74. ¿Qué comparten JavaFX y Android en el **contrato** con el servidor y qué duplicación duele?
75. Si un bug está solo en **un cliente**, ¿cómo localizas si la culpa es cliente o servidor?

---

## Calidad, pruebas y DevOps

76. ¿Qué **cobertura de tests** tienes y por qué ese número es honesto o vergonzoso?
77. ¿Qué prueba **end-to-end** mínima demostrarías en vivo en la defensa?
78. ¿Qué haría falta para un **CI** (GitHub Actions) que compile backend + Android + cliente?
79. ¿Cómo versionas **dependencias** (Maven, Gradle) para builds reproducibles en 2030?
80. ¿Qué métricas de **SonarQube** o lint te importan y cuáles ignoras?

---

## Rendimiento y escalabilidad

81. ¿Cuántas **conexiones simultáneas** aguanta tu `ServerSocket` con pool cached y cuál es el cuello de botella?
82. ¿Qué pasa si un cliente envía un JSON de **500 MB** en el payload?
83. ¿Cómo **pool de conexiones JDBC** interactúa con tu pool de hilos de sockets?
84. ¿Serializar JSON con **Jackson** vs **Gson**: impacto medible o irrelevante?
85. ¿Cómo perfilarías un **pico de CPU** en el backend sin adivinar?

---

## PayPal, tienda y reglas de negocio

86. ¿Qué garantiza PayPal **sandbox** que no garantiza producción en tu integración?
87. ¿Qué pasa si el canje de puntos **falla a mitad**: ¿rollback de puntos, compensación, idempotencia?
88. ¿Cómo evitas **doble canje** si el usuario pulsa el botón dos veces rápido?
89. ¿Qué reglas de **fair play** tienen tus apuestas frente a partidos manipulados (fuera de alcance pero pregúntalo)?
90. ¿Cómo auditarías **quién finalizó** un partido y con qué marcador?

---

## Ética, legal y contexto NBA

91. ¿Usar nombre/marca **NBA** en un TFG académico: qué límites legales conoces?
92. ¿Qué implica usar **datos personales** de compañeros como usuarios de prueba?
93. ¿Tu sistema fomenta **juego responsable** o apuestas reales; cómo lo contextualizas?
94. ¿Qué sesgos tiene un ranking por **puntos** frente a otros sistemas de puntuación?
95. ¿Podría tu software usarse de forma **dañina** y cómo lo mitigarías en un despliegue real?

---

## Preguntas «trampa» de tribunal

96. Resume tu TFG en **30 segundos** sin decir «es una aplicación que…».
97. Si te quitan **internet** en la defensa, ¿qué demo offline preparas?
98. ¿Cuál es la **línea de código** de la que más te arrepientes y por qué no la refactorizaste?
99. ¿Qué pregunta **esperabas** que te hicieran y no te han hecho? Respóndela ahora.
100. Si suspendes la defensa por un fallo en **demo en vivo**, ¿qué plan B tenías?

---

*Documento de estudio — no sustituye la guía oficial de tu centro.*
