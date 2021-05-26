package com.blakdragon.petscapeclan.models.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blakdragon.petscapeclan.R

enum class Rank(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int
) {
    Owner(
        R.drawable.rank_owner,
        R.string.rank_owner
    ),
    DeputyOwner(
        R.drawable.rank_deputy_owner,
        R.string.rank_deputy_owner
    ),
    Admin(
        R.drawable.rank_admin,
        R.string.rank_admin
    ),
    Dragon(
        R.drawable.rank_dragon,
        R.string.rank_dragon
    ),
    Rune(
        R.drawable.rank_rune,
        R.string.rank_rune
    ),
    Adamant(
        R.drawable.rank_adamant,
        R.string.rank_adamant
    ),
    Mithril(
        R.drawable.rank_mithril,
        R.string.rank_mithril
    ),
    Gold(
        R.drawable.rank_gold,
        R.string.rank_gold
    ),
    Steel(
        R.drawable.rank_steel,
        R.string.rank_steel
    ),
    Iron(
        R.drawable.rank_iron,
        R.string.rank_iron
    ),
    Bronze(
        R.drawable.rank_bronze,
        R.string.rank_bronze
    )
}
