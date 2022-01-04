package com.blakdragon.petscapeclan.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blakdragon.petscapeclan.ui.PetscapeTheme

@Composable
fun CardedList(items: List<String>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.height(300.dp),
        backgroundColor = PetscapeTheme.Colors.onPrimary
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(items) { item -> ListItem(item) }
        }
    }
}

@Composable
fun ListItem(text: String) {
    Text(
        text = text,
        color = PetscapeTheme.Colors.secondary,
        fontFamily = PetscapeTheme.Font.sourceSansPro,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun CardedListPreview() {
    CardedList(listOf(
        "First item",
        "Second item",
        "Third item!"
    ))
}