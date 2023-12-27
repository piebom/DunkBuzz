package com.pieterbommele.dunkbuzz.ui.navigation
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pieterbommele.dunkbuzz.ui.matchesScreen.MatchesScreen
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverview
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverviewViewModel

@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    fabActionVisible: Boolean = false,
    fabResetAction: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = DunkBuzzOverviewScreen.Teams.name,
        modifier = modifier,
    ) {
        composable(route = DunkBuzzOverviewScreen.Teams.name, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) {
            TeamOverview()
        }
        composable(route = DunkBuzzOverviewScreen.Matches.name, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) {
            MatchesScreen()
        }
        composable(route = DunkBuzzOverviewScreen.Standings.name) {
        }
    }
}