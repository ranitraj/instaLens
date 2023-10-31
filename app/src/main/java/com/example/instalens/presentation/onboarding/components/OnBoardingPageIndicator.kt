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
import com.example.instalens.presentation.Dimens

@Composable
fun OnBoardingPageIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    selectedPage: Int,
    selectedPageColor: Color = MaterialTheme.colorScheme.primary,
    unselectedPageColor: Color = colorResource(id = R.color.gray_50)
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