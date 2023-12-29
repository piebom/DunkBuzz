package com.pieterbommele.dunkbuzz.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pieterbommele.dunkbuzz.R
import com.pieterbommele.dunkbuzz.ui.components.DunkBuzzAppAppBar
import com.pieterbommele.dunkbuzz.ui.components.DunkBuzzBottomAppBar
import com.pieterbommele.dunkbuzz.ui.components.DunkBuzzNavigationRail
import com.pieterbommele.dunkbuzz.ui.navigation.DunkBuzzOverviewScreen
import com.pieterbommele.dunkbuzz.ui.navigation.navComponent
import com.pieterbommele.dunkbuzz.ui.util.DunkBuzzNavigationType

/**
 * Composable function representing the main DunkBuzz application.
 *
 * @param navigationType The type of navigation pattern to use (e.g., BOTTOM_NAVIGATION, NAVIGATION_RAIL).
 * @param navController The NavHostController responsible for navigation within the app.
 */
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun DunkBuzzApp(
    navigationType: DunkBuzzNavigationType,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val goToMatches = { navController.navigate(DunkBuzzOverviewScreen.Matches.name) { launchSingleTop = true } }
    val goToTeams = { navController.navigate(DunkBuzzOverviewScreen.Teams.name) { launchSingleTop = true } }

    val currentScreenTitle = DunkBuzzOverviewScreen.valueOf(
        backStackEntry?.destination?.route ?: DunkBuzzOverviewScreen.Matches.name
    ).title

    var isAddNewVisible by remember { mutableStateOf(false) }
    if (navigationType == DunkBuzzNavigationType.BOTTOM_NAVIGATION) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            topBar = {
                DunkBuzzAppAppBar(
                    currentScreenTitle = currentScreenTitle,
                )
            },
            bottomBar = {
                DunkBuzzBottomAppBar(goToTeams, goToMatches)
            },
        ) { innerPadding ->
            navComponent(navController, modifier = Modifier.padding(innerPadding))
        }
    } else {
        Row {
            AnimatedVisibility(visible = navigationType == DunkBuzzNavigationType.NAVIGATION_RAIL) {
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                DunkBuzzNavigationRail(
                    selectedDestination = navController.currentDestination,
                    onTabPressed = { node: String -> navController.navigate(node) },
                )
            }
            Scaffold(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                topBar = {
                    DunkBuzzAppAppBar(
                        currentScreenTitle = currentScreenTitle,
                    )
                },
            ) { innerPadding ->

                navComponent(navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}
