package com.blakdragon.petscapeclan.core

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDate

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(LocalDateAdapter())
    .build()

class LocalDateAdapter {
    @ToJson fun toJson(localDate: LocalDate): String {
        return localDate.toString()
    }

    @FromJson fun fromJson(json: String): LocalDate {
        return LocalDate.parse(json)
    }
}