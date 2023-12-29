package com.pieterbommele.dunkbuzz

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.pieterbommele.dunkbuzz.ui.DunkBuzzApp
import com.pieterbommele.dunkbuzz.ui.theme.DunkBuzzTheme
import com.pieterbommele.dunkbuzz.ui.util.DunkBuzzNavigationType

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
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
                            DunkBuzzApp(DunkBuzzNavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            DunkBuzzApp(DunkBuzzNavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            DunkBuzzApp(navigationType = DunkBuzzNavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            DunkBuzzApp(navigationType = DunkBuzzNavigationType.BOTTOM_NAVIGATION)
                        }
                    }
                }
            }
        }
    }
}
