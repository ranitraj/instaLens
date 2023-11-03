package com.example.instalens.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.instalens.R
import com.example.instalens.presentation.utils.Dimens

/**
 * A composable function that displays a row of indicators for onboarding screens.
 * It visually represents the total number of pages and the currently selected page.
 *
 * @param modifier A [Modifier] to be applied to the row of indicators.
 * @param pageCount The total number of onboarding pages.
 * @param selectedPage The index of the currently selected onboarding page.
 * @param selectedPageColor The color to be used for the indicator of the selected page.
 * @param unselectedPageColor The color to be used for the indicators of unselected pages.
 */
@Composable
fun OnBoardingPageIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    selectedPage: Int,
    selectedPageColor: Color = MaterialTheme.colorScheme.primary,
    unselectedPageColor: Color = colorResource(id = R.color.page_indicator_unselected)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(pageCount) { currentPage ->
            Box(
                modifier = Modifier.size(Dimens.IndicatorSize)
                    .clip(CircleShape)
                    .background(color = if (currentPage == selectedPage) selectedPageColor else unselectedPageColor)
            )
        }
    }
}