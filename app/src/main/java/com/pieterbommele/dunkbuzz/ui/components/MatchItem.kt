package com.pieterbommele.dunkbuzz.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
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
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .animateContentSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = imageResIdHome),
                    contentDescription = "$home_team_name logo",
                    modifier = Modifier.size(75.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = home_team_name,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
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
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
                Spacer(modifier = Modifier.width(8.dp)) // This spacer will likely be vertical
                Text(
                    text = "Q$quarter",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
                Spacer(modifier = Modifier.width(8.dp)) // This spacer will likely be vertical
                Text(
                    text = "$visitor_team_score",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = imageResIdVisitor),
                    contentDescription = "$visitor_team_name logo",
                    modifier = Modifier.size(75.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = visitor_team_name,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                    ),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.bebasnueue, FontWeight.Normal))
                )
            }
        }
    }
}