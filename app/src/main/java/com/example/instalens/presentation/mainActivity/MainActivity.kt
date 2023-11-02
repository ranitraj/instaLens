package com.example.instalens.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.instalens.presentation.navgraph.NavGraph
import com.example.instalens.ui.theme.InstaLensTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private val TAG: String? = MainActivity::class.simpleName
    }
    
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Initializing Splash-Screen API & continue showing via 'setKeepOnScreenCondition' until condition satisfies
        installSplashScreen().apply { 
            setKeepOnScreenCondition{
                // Will exit once 'false' is obtained for redirectFlagState
                viewModel.redirectFlagState
            }
        }

        setContent {
            InstaLensTheme {
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    // Retrieving startDestination from viewModel & redirecting to appropriate screen
                    val startDestination = viewModel.startDestination
                    Log.d(TAG, "setContent() called with startDestination = $startDestination ")

                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}