package com.pieterbommele.dunkbuzz.ui.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.pieterbommele.dunkbuzz.R

/**
 * Composable function for the DunkBuzz top app bar.
 *
 * @param currentScreenTitle The resource ID of the title to be displayed in the app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DunkBuzzAppAppBar(
    currentScreenTitle: Int,
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    modifier = Modifier.size(dimensionResource(R.dimen.logo)),
                    contentDescription = "Log"
                )
            }
        },
    )
}
