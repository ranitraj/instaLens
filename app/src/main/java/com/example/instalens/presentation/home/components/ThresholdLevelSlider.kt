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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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

@Composable
fun ThresholdLevelSlider(thresholdValue: (Float) -> Unit) {

    // Initialize slider with threshold-level/ confidence-score of 0.5
    var sliderValue by remember {
        mutableFloatStateOf(INITIAL_CONFIDENCE_SCORE)
    }

    // Compose Column comprising of Slider & Text showing the Confidence-Level
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Padding16dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                    thresholdValue(it)
                },
                valueRange = 0f..1f,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            Text(
                text = "${(sliderValue * 100).toInt()}%",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = colorResource(id = R.color.text_title)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewConfidenceSlider() {
    ThresholdLevelSlider {}
}