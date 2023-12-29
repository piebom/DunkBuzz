package com.pieterbommele.dunkbuzz.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import com.pieterbommele.dunkbuzz.ui.navigation.DunkBuzzOverviewScreen

@Composable
fun DunkBuzzNavigationRail(selectedDestination: NavDestination?, onTabPressed: (String) -> Unit, modifier: Modifier = Modifier) {
    NavigationRail(modifier = modifier,containerColor = MaterialTheme.colorScheme.primary) {
        for (navItem in DunkBuzzOverviewScreen.entries) {
            NavigationRailItem(
                colors = NavigationRailItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                selected = selectedDestination?.route == navItem.name,
                onClick = { onTabPressed(navItem.name) },
                icon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.secondary,
                        imageVector = ImageVector.vectorResource(navItem.icon),
                        contentDescription = navItem.name,
                    )
                },
            )
        }
    }
}