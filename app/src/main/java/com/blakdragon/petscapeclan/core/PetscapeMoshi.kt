package com.blakdragon.petscapeclan.core

import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.PetType
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(LocalDateAdapter())
    .add(LocalDateTimeAdapter())
    .add(PetType::class.java, EnumJsonAdapter.create(PetType::class.java).withUnknownFallback(PetType.Unknown))
    .add(AchievementType::class.java, EnumJsonAdapter.create(AchievementType::class.java).withUnknownFallback(AchievementType.Unknown))
    .build()

class LocalDateAdapter {
    @ToJson fun toJson(localDate: LocalDate): String {
        return localDate.toString()
    }

    @FromJson fun fromJson(json: String): LocalDate {
        return LocalDate.parse(json)
    }
}

class LocalDateTimeAdapter {
    @ToJson fun toJson(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    @FromJson fun fromJson(json: String): LocalDateTime {
        return LocalDateTime.parse(json, DateTimeFormatter.ISO_DATE_TIME)
    }
}