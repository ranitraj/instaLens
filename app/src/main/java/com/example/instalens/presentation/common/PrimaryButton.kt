package com.example.instalens.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.instalens.R
import com.example.instalens.presentation.utils.Dimens

/**
 * Composable function that creates a primary styled button with a text label.
 * This button follows the app's primary color theme and is intended to stand out as the main action button.
 *
 * @param text The text to display on the button, representing the button's action.
 * @param onClick The callback to be invoked when the button is clicked.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = colorResource(id = R.color.gray_50)
        ),
        shape = RoundedCornerShape(size = Dimens.ButtonCornerRadius)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}