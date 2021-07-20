package com.blakdragon.petscapeclan.models.enums

enum class AchievementType {
    Total1500,
    Total2000,
    MaxCape,
    Base70Stats,
    Base80Stats,
    Base90Stats,
    Combat126,

    QuestCape,
    AchievementCape,
    MusicCape,
    ChampionCape,
    InfernalCape,
    XericsCape,
    SinhazaShroud,
    SlayerHelmSet,

    TransmogDarkAcorn,
    TransmogMetamorphicDust,
    TransmogSanguineDust,
    Transmog6Jads,
    TransmogBlueHeron,
    TransmogSraracha,
    TransmogParasiticEgg,

    Clues600Beginner,
    Clues500Easy,
    Clues400Medium,
    Clues300Hard,
    Clues200Elite,
    Clues100Master,

    Skill50mExp,
    Skill100mExp,
    Skill200mExp,

    CollectionLog250,
    CollectionLog500,
    CollectionLog1000,

    Unknown;

    companion object {
        fun validAchievements(): List<AchievementType> = values().toMutableList().apply { remove(Unknown) }
    }
}
