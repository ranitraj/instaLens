package com.example.instalens.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.instalens.R

/**
 * Composable function that creates a secondary styled button with a text label.
 * This button is styled with less emphasis than the primary button and is used for less prominent actions.
 *
 * @param text The text to display on the button, which indicates the action of the button.
 * @param onClick The callback to be invoked when the button is interacted with by the user.
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = colorResource(id = R.color.text_body)
        )
    }
}