package com.example.instalens.presentation.onboarding

import androidx.annotation.DrawableRes

data class Page(
    val pageTitle: String,
    val pageDescription: String,
    @DrawableRes val thumbnailResource: Int
)
