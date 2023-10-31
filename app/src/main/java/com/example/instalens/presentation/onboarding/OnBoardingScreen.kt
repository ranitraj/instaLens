package com.example.instalens.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.instalens.R
import com.example.instalens.presentation.onboarding.components.OnBoardingPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState: PagerState = rememberPagerState(initialPage = 0)

        // Initializing stringResources as it cannot be accessed inside 'derivedStateOf'
        val buttonNext = stringResource(id = R.string.button_next)
        val buttonBack = stringResource(id = R.string.button_back)

        // Customizing OnBoarding-Screen Buttons depending on position of the current-page emitted by PagerState
        val buttonState = remember {
            derivedStateOf {
                when(pagerState.currentPage) {
                    0 -> listOf("", buttonNext) // Show only Next Button
                    1 -> listOf(buttonBack, buttonNext) // Show both Next & Back Buttons
                    2 -> listOf(buttonBack, "") // Show only Back Button
                    else -> listOf("", "")
                }
            }
        }

        // Initializing HorizontalPager
        HorizontalPager(pageCount = pagesList.size) { currentPageIndex ->
            OnBoardingPage(page = pagesList[currentPageIndex])
        }
    }
}