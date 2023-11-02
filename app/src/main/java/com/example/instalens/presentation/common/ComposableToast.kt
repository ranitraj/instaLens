package com.example.instalens.presentation.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ComposableToast(message: String) {
    val context = LocalContext.current

    // Show Toast
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}