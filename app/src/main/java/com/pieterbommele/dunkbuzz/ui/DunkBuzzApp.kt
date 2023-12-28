package com.pieterbommele.dunkbuzz.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pieterbommele.dunkbuzz.R
import com.pieterbommele.dunkbuzz.ui.components.DunkBuzzAppAppBar
import com.pieterbommele.dunkbuzz.ui.components.DunkBuzzBottomAppBar
import com.pieterbommele.dunkbuzz.ui.navigation.DunkBuzzOverviewScreen
import com.pieterbommele.dunkbuzz.ui.navigation.navComponent
import com.pieterbommele.dunkbuzz.ui.theme.DunkBuzzTheme
import com.pieterbommele.dunkbuzz.ui.util.TaskNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DunkBuzzApp(
    navigationType: TaskNavigationType,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val goToMatches = {navController.navigate(DunkBuzzOverviewScreen.Matches.name) {launchSingleTop = true}}
    val goToTeams = {navController.navigate(DunkBuzzOverviewScreen.Teams.name) {launchSingleTop = true}}

    val currentScreenTitle = DunkBuzzOverviewScreen.valueOf(
        backStackEntry?.destination?.route ?: DunkBuzzOverviewScreen.Matches.name
    ).title

    var isAddNewVisible by remember { mutableStateOf(false) }

    if(navigationType == TaskNavigationType.PERMANENT_NAVIGATION_DRAWER){
    }
    else if (navigationType == TaskNavigationType.BOTTOM_NAVIGATION){
        Scaffold(
            containerColor = Color.White,
            topBar = {
                DunkBuzzAppAppBar(
                    currentScreenTitle = currentScreenTitle,
                )
            },
            bottomBar = {
                DunkBuzzBottomAppBar(goToTeams,goToMatches)
            },
        ) { innerPadding ->
            navComponent(navController, modifier = Modifier.padding(innerPadding))
        }

    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun DunkBuzzPreview() {
    DunkBuzzTheme {
        // create a box to overlap image and texts
        Surface(modifier = Modifier.fillMaxWidth()) {
            DunkBuzzApp(TaskNavigationType.BOTTOM_NAVIGATION)
        }
    }
}
