package com.pieterbommele.dunkbuzz.ui.navigation
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pieterbommele.dunkbuzz.ui.matchesScreen.MatchesScreen
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverview

/**
 * Composable function for setting up the navigation component.
 *
 * @param navController The NavHostController responsible for navigation.
 * @param modifier The modifier for the NavHost composable.
 * @param fabActionVisible Whether the Floating Action Button (FAB) action is visible.
 * @param fabResetAction The action to perform when the FAB reset action is triggered.
 */
@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    fabActionVisible: Boolean = false,
    fabResetAction: () -> Unit = {},
) {
    val slideEffect = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
    val popupEffect = tween<IntOffset>(durationMillis = 2000, easing = CubicBezierEasing(0.08f, 0.93f, 0.68f, 1.27f))
    NavHost(
        navController = navController,
        startDestination = DunkBuzzOverviewScreen.Teams.name,
        modifier = modifier,
    ) {
        composable(
            route = DunkBuzzOverviewScreen.Teams.name,
            enterTransition = {
                when (initialState.destination.route) {
                    "details" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "details" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "details" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "details" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            TeamOverview()
        }
        composable(
            route = DunkBuzzOverviewScreen.Matches.name,
            enterTransition = {
                when (initialState.destination.route) {
                    "details" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "details" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "details" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "details" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            MatchesScreen()
        }
    }
}
