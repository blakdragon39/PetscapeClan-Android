package com.blakdragon.petscapeclan.models

import android.os.Parcelable
import com.blakdragon.petscapeclan.models.enums.RankType
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
class Rank(
    @Json(name = "rank") val type: RankType,
    val label: String,
    val order: Int,
    val points: Int
) : Parcelable