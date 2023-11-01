package com.example.instalens.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.instalens.R
import com.example.instalens.presentation.utils.Dimens

/**
 * Composable to display the count of objects present in the current frame
 * comprising of an icon and a text field to print the count.
 */
@Composable
fun ObjectCounter(
    objectCount: Int,
    drawableResourceId: Int
) {
    Row(
        modifier = Modifier
            .padding(Dimens.Padding16dp)
            .background(
                color = Color.Transparent.copy(alpha = 0.4f),
                shape = RoundedCornerShape(Dimens.ButtonCornerRadius)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Image to display Icon
        Image(
            painter = painterResource(id = drawableResourceId),
            contentDescription = stringResource(id = R.string.object_icon_description),
            modifier = Modifier
                .padding(Dimens.Padding16dp)
                .size(Dimens.ObjectCounterIconSize),
        )
        // Text
        Text(
            modifier = Modifier
                .padding(end = Dimens.Padding16dp, start = Dimens.Padding8dp),
            text = "$objectCount",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.gray_50)
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewObjectCountDisplay() {
    ObjectCounter(
        objectCount = 5,
        drawableResourceId = R.drawable.ic_object_count
    )
}