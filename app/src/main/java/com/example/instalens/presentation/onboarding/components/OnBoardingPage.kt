package com.example.instalens.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.instalens.R
import com.example.instalens.presentation.utils.Dimens
import com.example.instalens.presentation.onboarding.model.Page
import com.example.instalens.ui.theme.InstaLensTheme

/**
 * Displays an individual onboarding page for an app's onboarding sequence.
 * It includes an image, a title, and a description for each page of the onboarding.
 *
 * @param page The data object that holds the content for the onboarding page,
 *             including the image resource ID, title, and description.
 * @param modifier A [Modifier] to be applied to the Column layout for custom styling and behavior.
 */
@Composable
fun OnBoardingPage(
    page: Page,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.7f),
            painter = painterResource(id = page.thumbnailResource),
            contentDescription = stringResource(id = R.string.onboarding_image_description),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Dimens.Padding24dp))
        Text(
            modifier = Modifier.padding(horizontal = Dimens.Padding16dp),
            text = page.pageTitle,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_title)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding8dp))
        Text(
            modifier = Modifier.padding(horizontal = Dimens.Padding16dp),
            text = page.pageDescription,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
            color = colorResource(id = R.color.text_body)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    InstaLensTheme {
        OnBoardingPage(
            page = Page(
                "Detect multiple objects in Realtime",
                "Just point the camera at the object you want to detect and our AI model will let you know what it is",
                R.drawable.thumbnail_page_2
            )
        )
    }
}