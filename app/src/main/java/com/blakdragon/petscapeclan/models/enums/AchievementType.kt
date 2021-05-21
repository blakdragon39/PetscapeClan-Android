package com.blakdragon.petscapeclan.models.enums

import androidx.annotation.StringRes
import com.blakdragon.petscapeclan.R

enum class AchievementType(@StringRes val labelId: Int) {
    Total1500(R.string.achievement_total_1500),
    Total2000(R.string.achievement_total_2000),
    MaxCape(R.string.achievement_max_cape),
    Base70Stats(R.string.achievement_base_70),
    Base80Stats(R.string.achievement_base_80),
    Base90Stats(R.string.achievement_base_90),
    Combat126(R.string.achievement_combat_126),
    QuestCape(R.string.achievement_quest_cape),
    AchievementCape(R.string.achievement_achievement_cape),
    MusicCape(R.string.achievement_music_cape),
    ChampionCape(R.string.achievement_champions_cape),
    InfernalCape(R.string.achievement_inferncal_cape),
    XericsGuard(R.string.achievement_xerics_guard),
    XericsWarrior(R.string.achievement_xerics_warrior),
    XericsSentinal(R.string.achievement_xerics_sentinal),
    XericsGeneral(R.string.achievement_xerics_general),
    XericsChampion(R.string.achievement_xerics_champion),
    SinhazaShroud1(R.string.achievement_sinhaza_shroud_1),
    SinhazaShroud2(R.string.achievement_sinhaza_shroud_2),
    SinhazaShroud3(R.string.achievement_sinhaza_shroud_3),
    SinhazaShroud4(R.string.achievement_sinhaza_shroud_4),
    SinhazaShroud5(R.string.achievement_sinhaza_shroud_5),
    Ironman(R.string.achievement_ironman),
    UltimateIronman(R.string.achievement_ultimate_ironman)
}
