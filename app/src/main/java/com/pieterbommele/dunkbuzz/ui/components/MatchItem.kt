package com.pieterbommele.dunkbuzz.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pieterbommele.dunkbuzz.R

@Composable
fun MatchItem(
    modifier: Modifier = Modifier,
    home_team_id: Int,
    home_team_name: String,
    home_team_score: Int,
    visitor_team_id: Int,
    visitor_team_name: String,
    visitor_team_score: Int,
    quarter: Int
) {
    val context = LocalContext.current
    val imageNameHome = "nba_$home_team_id"
    val imageResIdHome = context.resources.getIdentifier(imageNameHome, "drawable", context.packageName)

    val imageNameVisitor = "nba_$visitor_team_id"
    val imageResIdVisitor = context.resources.getIdentifier(imageNameVisitor, "drawable", context.packageName)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Row(
            modifier = Modifier
                .animateContentSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(dimensionResource(R.dimen.smallSpacer)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = imageResIdHome),
                    contentDescription = "$home_team_name logo",
                    modifier = Modifier.size(dimensionResource(R.dimen.nbateam))
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.smallSpacer)))
                Text(
                    text = home_team_name,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.smallSpacer)))
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$home_team_score",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small))) // This spacer will likely be vertical
                Text(
                    text = "Q$quarter",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small))) // This spacer will likely be vertical
                Text(
                    text = "$visitor_team_score",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = imageResIdVisitor),
                    contentDescription = "$visitor_team_name logo",
                    modifier = Modifier.size(dimensionResource(R.dimen.nbateam))
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.smallSpacer)))
                Text(
                    text = visitor_team_name,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
            }
        }
    }
}