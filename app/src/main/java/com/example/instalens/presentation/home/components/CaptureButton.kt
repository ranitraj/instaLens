package com.example.instalens.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.instalens.R
import com.example.instalens.presentation.utils.Dimens

@Composable
fun CaptureButton(
    drawableResourceId: Int,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = drawableResourceId),
        contentDescription = stringResource(id = R.string.capture_button_description),
        modifier = Modifier
            .size(Dimens.CaptureButtonSize)
            .clip(CircleShape)
            .clickable(onClick = onClick)
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CaptureButtonPreview() {
    CaptureButton(
        drawableResourceId = R.drawable.ic_capture,
        onClick = {}
    )
}