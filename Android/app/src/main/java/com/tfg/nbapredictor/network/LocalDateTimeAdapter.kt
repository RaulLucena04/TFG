package com.tfg.nbapredictor.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime

/**
 * Adaptador personalizado de Gson para serializar y deserializar LocalDateTime.
 * 
 * <p>Convierte LocalDateTime a/desde formato ISO-8601 (String) para la comunicación
 * con la API REST. Esto es necesario porque Gson no tiene soporte nativo para
 * LocalDateTime de Java 8+.
 * 
 * @author TFG
 * @version 1.0
 */
class LocalDateTimeAdapter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDateTime {
        return LocalDateTime.parse(json.asString)
    }

    override fun serialize(src: LocalDateTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.toString()) // ISO-8601
    }
}
