package com.pieterbommele.dunkbuzz.ui.navigation
import androidx.annotation.StringRes
import com.pieterbommele.dunkbuzz.R

/**
 * Enum representing screens in the DunkBuzz overview navigation.
 *
 * @param title The title resource ID for the screen.
 * @param icon The icon resource ID for the screen.
 */
enum class DunkBuzzOverviewScreen(@StringRes val title: Int, val icon: Int) {
    /**
     * Represents the "Teams" screen.
     */
    Teams(title = R.string.Teams, icon = R.drawable.team),

    /**
     * Represents the "Matches" screen.
     */
    Matches(title = R.string.Matches, icon = R.drawable.matches)
}
