package com.pieterbommele.dunkbuzz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.pieterbommele.dunkbuzz.ui.DunkBuzzApp
import com.pieterbommele.dunkbuzz.ui.theme.DunkBuzzTheme
import com.pieterbommele.dunkbuzz.ui.util.TaskNavigationType

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*//added because the app grows. The dex file is a Dalvic Executable (a part of the compilation process of Android)
        //if it becomes to large, the OS has issues handling it well...
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()*/
        Log.i("vm inspection", "Main activity onCreate")
        setContent {
            DunkBuzzTheme {
                // create a Surface to overlap image and texts
                Surface {
                    val windowSize = calculateWindowSizeClass(activity = this)
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            DunkBuzzApp(TaskNavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            DunkBuzzApp(TaskNavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            DunkBuzzApp(navigationType = TaskNavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            DunkBuzzApp(navigationType = TaskNavigationType.BOTTOM_NAVIGATION)
                        }
                    }
                }
            }
        }
    }
}