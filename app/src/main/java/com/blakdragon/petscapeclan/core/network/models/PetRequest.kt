package com.blakdragon.petscapeclan.core.network.models

import com.blakdragon.petscapeclan.models.enums.PetType

data class PetRequest(
    val type: PetType
)