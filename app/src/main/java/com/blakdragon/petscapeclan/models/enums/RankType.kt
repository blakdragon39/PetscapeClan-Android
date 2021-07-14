package com.blakdragon.petscapeclan.models.enums

import androidx.annotation.DrawableRes
import com.blakdragon.petscapeclan.R

enum class RankType(@DrawableRes val iconId: Int, ) {
    Owner(R.drawable.rank_owner),
    DeputyOwner(R.drawable.rank_deputy_owner),
    Admin(R.drawable.rank_admin),
    Dragon(R.drawable.rank_dragon),
    Rune(R.drawable.rank_rune),
    Adamant(R.drawable.rank_adamant),
    Mithril(R.drawable.rank_mithril),
    Gold(R.drawable.rank_gold),
    Steel(R.drawable.rank_steel),
    Iron(R.drawable.rank_iron),
    Bronze(R.drawable.rank_bronze)
}
