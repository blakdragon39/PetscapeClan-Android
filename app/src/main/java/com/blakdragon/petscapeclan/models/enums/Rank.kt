package com.blakdragon.petscapeclan.models.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blakdragon.petscapeclan.R

enum class Rank(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int
) {
    Bronze(
        R.drawable.rank_bronze,
        R.string.rank_bronze
    ),
    Iron(
        R.drawable.rank_iron,
        R.string.rank_iron
    ),
    Steel(
        R.drawable.rank_steel,
        R.string.rank_steel
    ),
    Gold(
        R.drawable.rank_gold,
        R.string.rank_gold
    ),
    Mithril(
        R.drawable.rank_mithril,
        R.string.rank_mithril
    ),
    Adamant(
        R.drawable.rank_adamant,
        R.string.rank_adamant
    ),
    Rune(
        R.drawable.rank_rune,
        R.string.rank_rune
    ),
    Dragon(
        R.drawable.rank_dragon,
        R.string.rank_dragon
    ),
    Admin(
        R.drawable.rank_admin,
        R.string.rank_admin
    ),
    DeputyOwner(
        R.drawable.rank_deputy_owner,
        R.string.rank_deputy_owner
    ),
    Owner(
        R.drawable.rank_owner,
        R.string.rank_owner
    )
}
