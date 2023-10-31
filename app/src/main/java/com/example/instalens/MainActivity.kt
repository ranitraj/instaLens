package com.example.instalens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.instalens.presentation.onboarding.OnBoardingScreen
import com.example.instalens.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.example.instalens.ui.theme.InstaLensTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private val TAG: String? = MainActivity::class.simpleName
    }

    @Inject
    private lateinit var viewModel: OnBoardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Initializing Splash Screen API
        installSplashScreen()

        setContent {
            InstaLensTheme {
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    viewModel = hiltViewModel()
                    OnBoardingScreen(
                        event = viewModel::onEvent
                    )
                }
            }
        }
    }
}