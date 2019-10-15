package com.example.submission5.di

import android.annotation.SuppressLint
import com.example.submission5.data.api.FilmServiceFactory
import com.google.gson.*
import org.koin.dsl.module
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
val networkModule = module {
    single {
        GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Date::class.java, object: JsonDeserializer<Date> {
                private val sdf = SimpleDateFormat("yyyy-MM-dd")
                @Throws(JsonParseException::class)
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): Date {
                    return try {
                        sdf.parse(json!!.asString) as Date
                    } catch (e: Exception) {
                        Date(0L)
                    }
                }

            })
            .create()
    }
    single { FilmServiceFactory.settingOkHttpClient() }
    single { FilmServiceFactory.getService(get(), get()) }
}
