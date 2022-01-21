package com.blakdragon.petscapeclan.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.ui.components.CardedBackground
import com.blakdragon.petscapeclan.ui.fragments.home.HomeViewModel
import com.blakdragon.petscapeclan.ui.theme.PetscapeTheme
import com.blakdragon.petscapeclan.utils.previewClanMember1
import com.blakdragon.petscapeclan.utils.previewClanMember2
import com.google.accompanist.swiperefresh.SwipeRefresh

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
//    SwipeRefresh(
//        state = viewModel.clanMembersLoading.value,
//        onRefresh = { viewModel.getClanMembers() },
//        modifier = Modifier.padding(16.dp)
//    ) {
        CardedBackground(modifier = Modifier.padding(16.dp)) {
            LazyColumn {
                items(viewModel.filteredClanMembers.value ?: emptyList()) {
                    ClanMemberCell(clanMember = it)
                }
            }
        }
//    }
}

@Composable
private fun ClanMemberCell(clanMember: ClanMember) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { /* todo */ }
            )
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = clanMember.runescapeName,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = clanMember.altsText(),
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Image(
            painter = painterResource(clanMember.rank.type.iconId),
            contentDescription = "Rank for ${clanMember.runescapeName}",
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() = PetscapeTheme {
    HomeScreen(HomeViewModel().apply {
        filteredClanMembers.value = listOf(
            previewClanMember1, previewClanMember2
        )
    })
}