package com.blakdragon.petscapeoffline.models

class User(
    val id: String,
    val token: String,
    val clanMembers: List<String>,
    val displayName: String,
    val approved: Boolean,
    val admin: Boolean,
    val superAdmin: Boolean
)