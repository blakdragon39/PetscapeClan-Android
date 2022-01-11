package com.blakdragon.petscapeclan.ui.fragments.members

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.models.Achievement
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.Pet
import com.blakdragon.petscapeclan.models.Rank
import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.PetType
import com.blakdragon.petscapeclan.models.enums.RankType
import com.blakdragon.petscapeclan.ui.components.CardedList
import com.blakdragon.petscapeclan.ui.theme.PetscapeTheme
import java.time.LocalDate

@Composable
fun ClanMemberScreen(clanMember: ClanMember) {
    Column(modifier = Modifier.fillMaxWidth()) {
        RankDisplay(clanMember = clanMember)
        Alts(clanMember = clanMember)
        Spacer(modifier = Modifier.height(8.dp))
        LabeledValue(label = stringResource(R.string.member_boss_kc), value = clanMember.bossKc.toString())
        LabeledValue(label = stringResource(R.string.member_points), value = clanMember.points.toString())
        LabeledValue(label = stringResource(R.string.member_possible_rank), value = clanMember.possibleRank.label)
        Spacer(modifier = Modifier.height(12.dp))
        PetListSection(clanMember = clanMember)
        Spacer(modifier = Modifier.height(16.dp))
        AchievementListSection(clanMember = clanMember)
    }
}

@Composable
private fun RankDisplay(clanMember: ClanMember) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(clanMember.rank.type.iconId),
            contentDescription = "Rank Icon",
            modifier = Modifier.width(60.dp).height(60.dp)
        )
        Text(text = clanMember.rank.label, fontSize = 20.sp)
    }
}

@Composable
private fun Alts(clanMember: ClanMember) {
    Column {
        Text(text = stringResource(R.string.member_alts), fontWeight = FontWeight.Bold)
        clanMember.alts.forEach {
            Text(text = "• $it")
        }
    }
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Row {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = ":")
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value)
    }
}

@Composable
private fun PetListSection(clanMember: ClanMember) =
    ListSection(
        header = stringResource(R.string.member_pets),
        has = clanMember.pets.size,
        outOf = PetType.validPets().size,
        listItems = clanMember.pets.map { it.label }
    )

@Composable
private fun AchievementListSection(clanMember: ClanMember) =
    ListSection(
        header = stringResource(R.string.member_achievements),
        has = clanMember.achievements.size,
        outOf = AchievementType.validAchievements().size,
        listItems = clanMember.achievements.map { it.label }
    )

@Composable
private fun ListSection(
    header: String,
    has: Int,
    outOf: Int,
    listItems: List<String>
) {
    ListHeader(header = header, has = has, outOf = outOf)
    Spacer(modifier = Modifier.height(12.dp))
    CardedList(items = listItems, modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
    )
}

@Composable
private fun ListHeader(header: String, has: Int?, outOf: Int?) {
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
private fun PreviewClanMemberScreen() = PetscapeTheme {
    ClanMemberScreen(
        ClanMember(
            id = "",
            runescapeName = "Blakdragon",
            rank = Rank(RankType.Dragon, "Dragon", 0, 0),
            joinDate = LocalDate.now(),
            lastSeen = LocalDate.now(),
            bossKc = 40000,
            pets = listOf(
                Pet(PetType.AbyssalOrphan, "Abyssal Orphan"),
                Pet(PetType.CallistoCub, "Callisto Cub")
            ),
            achievements = listOf(
                Achievement(AchievementType.AchievementCape, "Achievement Cape"),
                Achievement(AchievementType.ChampionCape, "Champion Cape")
            ),
            points = 100,
            alts = listOf("MsBlakdragon"),
            possibleRank = Rank(RankType.Dragon, "Dragon", 0, 0)
        )
    )
}