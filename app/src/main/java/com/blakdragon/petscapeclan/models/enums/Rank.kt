package com.blakdragon.petscapeclan.models.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blakdragon.petscapeclan.R

enum class Rank(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int,
    val points: Int,
) {
    Owner(
        R.drawable.rank_owner,
        R.string.rank_owner,
        Int.MAX_VALUE
    ),
    DeputyOwner(
        R.drawable.rank_deputy_owner,
        R.string.rank_deputy_owner,
        Int.MAX_VALUE
    ),
    Admin(
        R.drawable.rank_admin,
        R.string.rank_admin,
        Int.MAX_VALUE
    ),
    Dragon(
        R.drawable.rank_dragon,
        R.string.rank_dragon,
        85
    ),
    Rune(
        R.drawable.rank_rune,
        R.string.rank_rune,
        70
    ),
    Adamant(
        R.drawable.rank_adamant,
        R.string.rank_adamant,
        55
    ),
    Mithril(
        R.drawable.rank_mithril,
        R.string.rank_mithril,
        45
    ),
    Gold(
        R.drawable.rank_gold,
        R.string.rank_gold,
        35
    ),
    Steel(
        R.drawable.rank_steel,
        R.string.rank_steel,
        20
    ),
    Iron(
        R.drawable.rank_iron,
        R.string.rank_iron,
        10
    ),
    Bronze(
        R.drawable.rank_bronze,
        R.string.rank_bronze,
        1
    )
}
