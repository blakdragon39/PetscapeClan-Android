package com.blakdragon.petscapeclan.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val colorPalette = lightColors(
    primary = primary,
    secondary = secondary,
    onPrimary = onPrimary,
    surface = background
)

@Composable
fun PetscapeTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = colorPalette,
        typography = PetscapeTypography,
        shapes = shapes,
        content = content
    )
}