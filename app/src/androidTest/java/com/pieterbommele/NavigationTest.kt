package com.pieterbommele

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.pieterbommele.dunkbuzz.ui.DunkBuzzApp
import com.pieterbommele.dunkbuzz.ui.util.DunkBuzzNavigationType
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            DunkBuzzApp(navController = navController, navigationType = DunkBuzzNavigationType.BOTTOM_NAVIGATION)
        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule.onNodeWithTag("Top_Teams").assertIsDisplayed()
    }

    @Test
    fun verifyNavigationToMatches() {
        composeTestRule.onNodeWithText("Matches").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithTag("Top_Matches").assertIsDisplayed()
    }
}
