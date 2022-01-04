package com.blakdragon.petscapeclan.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.blakdragon.petscapeclan.R

object PetscapeTheme {

    object Colors {
        val primary = Color(0xff3c332e)
        val onPrimary = Color(0xfffaf3dd)
        val secondary = Color(0xff27211d)
        val background = Color(0xff574c45)
    }

    object Font {
        val sourceSansPro = FontFamily(
            Font(R.font.source_sans_pro)
        )
    }
}