package com.pieterbommele.dunkbuzz.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pieterbommele.dunkbuzz.R
import com.pieterbommele.dunkbuzz.ui.theme.DunkBuzzTheme

@Composable
fun TeamItem(
    modifier: Modifier = Modifier,
    id: Int = 0,
    teamName: String = "",
    teamAbbreviation: String = ""
) {
    val context = LocalContext.current
    val imageName = "nba_$id"  // Replace with your dynamic ID
    val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .animateContentSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Assuming the logo is square, adjust the size accordingly
            Image(
                painter = painterResource(id = imageResId),
                modifier = Modifier.size(75.dp),
                contentDescription = "$teamName logo"
            )
            Spacer(modifier = Modifier.width(16.dp)) // Space between the logo and the text
            Column {
                Text(
                    text = teamName,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
                Text(
                    text = "#$teamAbbreviation",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
            }
        }
    }
}

@Preview
@Composable
fun TeamItemPreview() {
    DunkBuzzTheme {
        TeamItem(
            id = 1, // This should correspond to the drawable resource name
            teamName = "Los Angeles Lakers",
            teamAbbreviation = "LAL"
        )
    }
}