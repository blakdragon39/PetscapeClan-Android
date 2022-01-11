package com.blakdragon.petscapeclan.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.blakdragon.petscapeclan.R

val PetscapeTypography = Typography(
    defaultFontFamily = FontFamily(Font(R.font.source_sans_pro)),
    body1 = TextStyle(color = onPrimary)
)