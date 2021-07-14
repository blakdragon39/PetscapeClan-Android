package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.PetType

data class PetData(
    val type: PetType,
    val label: String
)