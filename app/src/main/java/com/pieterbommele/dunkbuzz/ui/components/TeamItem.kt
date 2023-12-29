package com.pieterbommele.dunkbuzz.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.pieterbommele.dunkbuzz.R

/**
 * A composable function that displays a team item card. It shows the team's logo alongside its name and abbreviation.
 * The logo image is dynamically retrieved based on the team ID provided.
 *
 * The card is styled according to the MaterialTheme and adapts its size based on the content.
 *
 * @param modifier Modifier to be applied to the card layout. It allows further customization like padding, alignment, etc.
 * @param id The unique identifier for the team, used to fetch the team's logo from drawable resources.
 * @param teamName The full name of the team to be displayed.
 * @param teamAbbreviation The abbreviated name of the team, typically 2-3 letters representing the team.
 *
 * Usage:
 * ```
 * TeamItem(
 *     id = 1,
 *     teamName = "Los Angeles Lakers",
 *     teamAbbreviation = "LAL"
 * )
 * ```
 */
@Composable
fun TeamItem(
    modifier: Modifier = Modifier,
    id: Int = 0,
    teamName: String = "",
    teamAbbreviation: String = ""
) {
    val context = LocalContext.current
    val imageName = "nba_$id" // Replace with your dynamic ID
    val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small)),
    ) {
        Row(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(dimensionResource(R.dimen.smallSpacer)),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Assuming the logo is square, adjust the size accordingly
            Image(
                painter = painterResource(id = imageResId),
                modifier = Modifier.size(dimensionResource(R.dimen.nbateam)),
                contentDescription = "$teamName logo"
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.smallSpacer))) // Space between the logo and the text
            Column {
                Text(
                    text = teamName,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
                Text(
                    text = "#$teamAbbreviation",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
            }
        }
    }
}
