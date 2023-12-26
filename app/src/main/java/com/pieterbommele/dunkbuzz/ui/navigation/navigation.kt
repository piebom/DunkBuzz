package com.pieterbommele.dunkbuzz.ui.navigation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TaskOverviewViewModel

@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    fabActionVisible: Boolean = false,
    fabResetAction: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = DunkBuzzOverviewScreen.Matches.name,
        modifier = modifier,
    ) {
        composable(route = DunkBuzzOverviewScreen.Teams.name) {
            TaskOverviewViewModel()
        }
        composable(route = DunkBuzzOverviewScreen.Matches.name) {
            TaskOverviewViewModel()
        }
        composable(route = DunkBuzzOverviewScreen.Standings.name) {
            TaskOverviewViewModel()
        }
    }
}