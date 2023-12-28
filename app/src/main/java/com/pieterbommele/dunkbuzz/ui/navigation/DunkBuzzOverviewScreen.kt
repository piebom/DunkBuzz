package com.pieterbommele.dunkbuzz.ui.navigation
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.pieterbommele.dunkbuzz.R

enum class DunkBuzzOverviewScreen(@StringRes val title: Int, val icon: Int) {
    Teams(title = R.string.Teams, icon = R.drawable.user),
    Matches(title = R.string.Matches, R.drawable.user),
}