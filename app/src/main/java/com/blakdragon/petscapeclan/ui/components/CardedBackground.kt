package com.blakdragon.petscapeclan.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardedBackground(
    modifier: Modifier,
    content: @Composable () -> Unit
) = Card(
    shape = RoundedCornerShape(16.dp),
    backgroundColor = MaterialTheme.colors.onPrimary,
    modifier = modifier,
    content = content
)