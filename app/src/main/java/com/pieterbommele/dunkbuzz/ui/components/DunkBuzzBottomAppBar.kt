package com.pieterbommele.dunkbuzz.ui.components
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.pieterbommele.dunkbuzz.R
import com.pieterbommele.dunkbuzz.ui.theme.DeepBlue
import com.pieterbommele.dunkbuzz.ui.theme.DeepBlueDark
import com.pieterbommele.dunkbuzz.ui.theme.DunkBuzzTheme
import com.pieterbommele.dunkbuzz.ui.theme.Primary
import com.pieterbommele.dunkbuzz.ui.theme.PrimaryVariant

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DunkBuzzBottomAppBar(goTeams: () -> Unit, goMatches: () -> Unit) {
        DunkBuzzTheme {
            val items = listOf(
                BottomNavigationItem(
                    title = "Teams",
                    selectedIcon = ImageVector.vectorResource(R.drawable.team),
                    unselectedIcon = ImageVector.vectorResource(R.drawable.team),
                    hasNews = false,
                ),
                BottomNavigationItem(
                    title = "Matches",
                    selectedIcon = ImageVector.vectorResource(R.drawable.matches),
                    unselectedIcon = ImageVector.vectorResource(R.drawable.matches),
                    hasNews = false,
                ),
            )
            var selectedItemIndex by rememberSaveable {
                mutableStateOf(0)
            }
                        NavigationBar(containerColor = DeepBlue, contentColor = Color.Transparent) {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = DeepBlueDark,
                                    ),
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        if(selectedItemIndex != index) {
                                            selectedItemIndex = index
                                            when (index) {
                                                0 -> goTeams()
                                                1 -> goMatches()
                                            }
                                        }
                                    },
                                    label = {
                                        Text(color = Primary, text = item.title, fontFamily = FontFamily(Font(R.font.bebasnueue, weight = FontWeight.Normal)))
                                    },
                                    icon = {

                                        BadgedBox(
                                            badge = {
                                                if (item.badgeCount != null) {
                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if (item.hasNews) {
                                                    Badge()
                                                }
                                            }
                                        ) {
                                            Icon(

                                                tint = Primary,
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
}