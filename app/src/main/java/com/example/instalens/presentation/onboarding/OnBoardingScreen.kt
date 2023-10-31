package com.example.instalens.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.instalens.R
import com.example.instalens.presentation.Dimens
import com.example.instalens.presentation.common.PrimaryButton
import com.example.instalens.presentation.common.SecondaryButton
import com.example.instalens.presentation.onboarding.components.OnBoardingPage
import com.example.instalens.presentation.onboarding.components.OnBoardingPageIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState: PagerState = rememberPagerState(initialPage = 0)

        // Initializing stringResources as it cannot be accessed inside 'derivedStateOf'
        val buttonNext = stringResource(id = R.string.button_next)
        val buttonBack = stringResource(id = R.string.button_back)
        val buttonGetStarted = stringResource(id = R.string.button_get_started)

        // Customizing OnBoarding-Screen Buttons depending on position of the current-page emitted by PagerState
        val buttonState = remember {
            derivedStateOf {
                when(pagerState.currentPage) {
                    0 -> listOf("", buttonNext) // Show only Next Button
                    1 -> listOf(buttonBack, buttonNext) // Show both Next & Back Buttons
                    2 -> listOf(buttonBack, buttonGetStarted) // Show Back & Get-Started Buttons
                    else -> listOf("", "")
                }
            }
        }

        // Initializing HorizontalPager component
        HorizontalPager(state = pagerState, pageCount = pagesList.size) {
            OnBoardingPage(page = pagesList[pagerState.currentPage])
        }

    }
}