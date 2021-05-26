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
    InfernalCape(R.string.achievement_infernal_cape),
    XericsCape(R.string.achievement_xerics_cape),
    SinhazaShroud(R.string.achievement_sinhaza_shroud),
    SlayerHelmSet(R.string.achievement_slayer_helm_set),

    TransmogDarkAcorn(R.string.achievement_transmog_dark_acorn),
    TransmogMetamorphicDust(R.string.achievement_transmog_metamorphic_dust),
    Transmog6Jads(R.string.achievement_transmog_6Jads),
    TransmogBlueHeron(R.string.achievement_transmog_blue_heron),
    TransmogSraracha(R.string.achievement_transmog_sraracha),

    Clues600Beginner(R.string.achievement_clues_600_beginner),
    Clues500Easy(R.string.achievement_clues_500_easy),
    Clues400Medium(R.string.achievement_clues_400_medium),
    Clues300Hard(R.string.achievement_clues_300_hard),
    Clues200Elite(R.string.achievement_clues_200_elite),
    Clues100Master(R.string.achievement_clues_100_master),

    Skill50mExp(R.string.achievement_skill_50m_exp),
    Skill100mExp(R.string.achievement_skill_100m_exp),
    Skill200mExp(R.string.achievement_skill_200m_exp),

    CollectionLog250(R.string.achievement_collection_log_250),
    CollectionLog500(R.string.achievement_collection_log_500),
    CollectionLog1000(R.string.achievement_collection_log_1000),


}
