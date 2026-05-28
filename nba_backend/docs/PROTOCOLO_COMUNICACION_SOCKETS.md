# Protocolo de mensajes (cliente ↔ servidor)

**Regla:** solo el **cliente** envía peticiones. El **servidor** siempre responde a esa petición (no envía mensajes por su cuenta).

---

## Mensaje del cliente al servidor

```json
{
  "requestId": "uuid-opcional",
  "action": "nombre.de.la.operacion",
  "payload": { }
}
```

| `action` | `payload` (qué manda el cliente) |
|----------|----------------------------------|
| `user.register` | Objeto usuario (registro) |
| `user.login` | `{ "username", "password" }` |
| `user.list` | `{}` |
| `user.get` | `{ "id": número }` |
| `user.update` | Objeto usuario |
| `user.changePassword` | `{ "id", "password" }` |
| `match.list` | `{}` |
| `match.create` | Objeto partido |
| `match.finalize` | `{ "id", "puntosLocal", "puntosVisitante" }` |
| `match.byTeam` | `{ "equipoId": número }` |
| `team.list` | `{}` |
| `team.get` | `{ "id": número }` |
| `team.save` | Objeto equipo |
| `team.stats` | `{ "equipoId": número }` |
| `team.withStats` | `{}` |
| `player.list` | `{}` |
| `player.byTeam` | `{ "equipoId": número }` |
| `player.save` | Objeto jugador |
| `bet.create` | Objeto apuesta |
| `bet.byUser` | `{ "userId": número }` |
| `store.redeem` | Objeto canje de puntos |

---

## Mensaje del servidor al cliente

```json
{
  "requestId": "el mismo que envió el cliente",
  "ok": true,
  "data": …,
  "error": null
}
```

Si algo falla: `ok` es `false`, `data` es `null` y `error` trae un texto.

| `action` recibida | Qué devuelve el servidor en `data` (si `ok` es true) |
|-------------------|-----------------------------------------------------|
| `user.register` | Usuario creado |
| `user.login` | Usuario |
| `user.list` | Lista de usuarios |
| `user.get` | Usuario |
| `user.update` | Usuario actualizado |
| `user.changePassword` | `{ "message": "Contraseña actualizada" }` |
| `match.list` | Lista de partidos |
| `match.create` | Partido creado |
| `match.finalize` | Partido finalizado |
| `match.byTeam` | Lista de partidos del equipo |
| `team.list` | Lista de equipos |
| `team.get` | Equipo |
| `team.save` | Equipo guardado |
| `team.stats` | Estadísticas del equipo |
| `team.withStats` | Lista de equipos con estadísticas |
| `player.list` | Lista de jugadores |
| `player.byTeam` | Lista de jugadores del equipo |
| `player.save` | Jugador guardado |
| `bet.create` | Apuesta creada |
| `bet.byUser` | Lista de apuestas del usuario |
| `store.redeem` | Resultado del canje |

Si `action` no existe: `ok: false` y `error` con el motivo.
