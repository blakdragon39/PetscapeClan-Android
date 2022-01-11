package com.blakdragon.petscapeclan.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardedList(items: List<String>, modifier: Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.onPrimary
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(items) { item -> ListItem(item) }
        }
    }
}

@Composable
private fun ListItem(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
@Preview(showBackground = true)
private fun CardedListPreview() {
    CardedList(
        listOf(
            "First item",
            "Second item",
            "Third item!"
        ),
        modifier = Modifier.fillMaxWidth()
    )
}