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
import com.example.instalens.presentation.utils.Dimens
import com.example.instalens.presentation.common.PrimaryButton
import com.example.instalens.presentation.common.SecondaryButton
import com.example.instalens.presentation.onboarding.components.OnBoardingPage
import com.example.instalens.presentation.onboarding.components.OnBoardingPageIndicator
import com.example.instalens.presentation.onboarding.model.pagesList
import kotlinx.coroutines.launch

/**
 * A Composable function that displays the onboarding screens using a pager.
 * It allows users to swipe through different pages with content introducing the app's features.
 * Depending on the current page, it shows appropriate navigation buttons and updates the onboarding progress indicator.
 *
 * @param event A lambda that emits OnBoardingEvent to the host of the Composable when certain user actions occur.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState: PagerState = rememberPagerState(initialPage = 0) {
            pagesList.size
        }

        // Initializing stringResources as it cannot be accessed inside 'derivedStateOf'
        val buttonNext = stringResource(id = R.string.button_next)
        val buttonBack = stringResource(id = R.string.button_back)
        val buttonBegin = stringResource(id = R.string.button_begin)

        // Customizing OnBoarding-Screen Buttons depending on position of the current-page emitted by PagerState
        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", buttonNext) // Show only Next Button
                    1 -> listOf(buttonBack, buttonNext) // Show both Next & Back Buttons
                    2 -> listOf(buttonBack, buttonBegin) // Show Back & Begin Buttons
                    else -> listOf("", "")
                }
            }
        }

        // Initializing HorizontalPager component
        HorizontalPager(state = pagerState) {pageIndex ->
            OnBoardingPage(page = pagesList[pageIndex])
        }
        Spacer(modifier = Modifier.weight(1f))

        // Creating Row with 'OnBoardingPageIndicator' and 'Buttons'
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Padding16dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OnBoardingPageIndicator(
                modifier = Modifier.width(Dimens.OnBoardingPageIndicatorWidth),
                pageCount = pagesList.size,
                selectedPage = pagerState.currentPage
            )

            // Nested 'Row' for displaying Back & Next buttons
            Row(verticalAlignment = Alignment.CenterVertically) {
                val pagerCoroutineScope = rememberCoroutineScope()

                if (buttonState.value[0].isNotEmpty()) {
                    // Handling logic to show 'Back' button
                    SecondaryButton(
                        text = buttonState.value[0],
                        onClick = {
                            pagerCoroutineScope.launch {
                                // Decrement page count
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )

                    // Spacing between the Secondary & Primary Buttons
                    Spacer(modifier = Modifier.padding(horizontal = Dimens.Padding8dp))
                }

                PrimaryButton(
                    text = buttonState.value[1],
                    onClick = {
                        pagerCoroutineScope.launch {
                            if (pagerState.currentPage == (pagesList.size - 1)) {
                                // At last page -> Writing config key to DataStore denoting OnBoarding Screens are seen
                                event(OnBoardingEvent.WriteUserConfigToDataStore)
                            } else {
                                // Increment page count
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.25f))
    }
}