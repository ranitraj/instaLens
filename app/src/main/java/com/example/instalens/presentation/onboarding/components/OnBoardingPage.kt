package com.example.instalens.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.instalens.R
import com.example.instalens.presentation.Dimens
import com.example.instalens.presentation.onboarding.Page

@Composable
fun OnBoardingPage(
    page: Page,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.6f),
            painter = painterResource(id = page.thumbnailResource),
            contentDescription = stringResource(id = R.string.onboarding_image_description),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Dimens.Padding24dp))
        Text(
            modifier = Modifier.padding(horizontal = Dimens.Padding16dp),
            text = page.pageTitle,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
            color = colorResource(id = R.color.text_title)
        )
        Text(
            modifier = Modifier.padding(horizontal = Dimens.Padding16dp),
            text = page.pageDescription,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
            color = colorResource(id = R.color.text_title)
        )
    }
}