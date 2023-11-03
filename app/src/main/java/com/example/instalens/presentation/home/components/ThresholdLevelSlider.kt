package com.example.instalens.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.instalens.R
import com.example.instalens.presentation.utils.Dimens
import com.example.instalens.utils.Constants.INITIAL_CONFIDENCE_SCORE
import kotlin.math.absoluteValue

/**
 * A composable function that presents a slider used for adjusting a threshold level,
 * typically for a confidence score or similar metric that ranges from 0 to 1.
 * The current value of the slider is displayed as a percentage to the user.
 *
 * @param sliderValue A [MutableState] holding the current value of the slider, which can be observed and modified.
 * @param thresholdValue A lambda function that is invoked when the slider value change is completed.
 *                       It provides the final value of the slider when the user stops interacting with it.
 */
@Composable
fun ThresholdLevelSlider(
    sliderValue: MutableState<Float>,
    thresholdValue: (Float) -> Unit
) {
    // Compose Column comprising of Slider & Text showing the Confidence-Level
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.Padding40dp,
                end = Dimens.Padding40dp,
                bottom = Dimens.Padding32dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Slider(
                value = sliderValue.value,
                onValueChange = {
                    sliderValue.value = String.format("%.2f", it).toFloat()
                },
                onValueChangeFinished = {
                    // Invoke the provided lambda function with the final value
                    thresholdValue(sliderValue.value)
                },
                valueRange = 0f..1f,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            Text(
                text = "${(sliderValue.value * 100).toInt()}%",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = colorResource(id = R.color.gray_50)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewConfidenceSlider() {
    val sliderValue = remember { mutableFloatStateOf(INITIAL_CONFIDENCE_SCORE) }
    ThresholdLevelSlider(sliderValue) {}
}