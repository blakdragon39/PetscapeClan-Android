package com.blakdragon.petscapeclan.ui.fragments.members

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.Rank
import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.PetType
import com.blakdragon.petscapeclan.models.enums.RankType
import com.blakdragon.petscapeclan.ui.components.CardedList
import com.blakdragon.petscapeclan.ui.theme.PetscapeTheme
import java.time.LocalDate

@Composable
fun ClanMemberScreen(clanMember: ClanMember) {
    val numPets = clanMember.pets.size
    val totalPets = PetType.validPets().size

    val numAchievements = clanMember.achievements.size
    val totalAchievements = AchievementType.validAchievements().size

    Column(modifier = Modifier.fillMaxWidth()) {
        ListHeader(header = stringResource(R.string.member_pets), has = numPets, outOf = totalPets)
        Spacer(modifier = Modifier.height(12.dp))
        CardedList(items = clanMember.pets.map { it.label }, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp))
        Spacer(modifier = Modifier.height(16.dp))
        ListHeader(header = stringResource(R.string.member_achievements), has = numAchievements, outOf = totalAchievements)
        Spacer(modifier = Modifier.height(12.dp))
        CardedList(items = clanMember.achievements.map { it.label }, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp))
    }
}

@Composable
fun ListHeader(header: String, has: Int?, outOf: Int?) {
    Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxWidth()) {
        Text(text = header, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = has.toString())
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "/")
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = outOf.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClanMemberScreen() = PetscapeTheme {
    ClanMemberScreen(
        ClanMember(
            id = "",
            runescapeName = "Blakdragon",
            rank = Rank(RankType.Dragon, "Dragon", 0, 0),
            joinDate = LocalDate.now(),
            lastSeen = LocalDate.now(),
            bossKc = 40000,
            pets = listOf(),
            achievements = listOf(),
            points = 100,
            alts = listOf("MsBlakdragon"),
            possibleRank = Rank(RankType.Dragon, "Dragon", 0, 0)
        )
    )
}