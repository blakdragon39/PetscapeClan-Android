package com.blakdragon.petscapeoffline.models

class User(
    val id: String,
    val token: String,
    val clanMembers: List<String>,
    val displayName: String,
    val isApproved: Boolean,
    val isAdmin: Boolean,
    val isSuperAdmin: Boolean
)