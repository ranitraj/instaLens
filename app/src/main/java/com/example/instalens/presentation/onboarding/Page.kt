package com.example.instalens.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.instalens.R

/**
 * Represents data class for an OnBoarding Page comprising of title, description, and a thumbnail.
 *
 * @property pageTitle The title of the page.
 * @property pageDescription A brief description about the page.
 * @property thumbnailResource A drawable resource ID representing the thumbnail image of the page.
 */
data class Page(
    val pageTitle: String,
    val pageDescription: String,
    @DrawableRes val thumbnailResource: Int
)

// TODO: Mode to data layer
val pagesList = listOf(
    Page(
        pageTitle = "Detect multiple objects in realtime",
        pageDescription = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        thumbnailResource = R.drawable.thumbnail_page_1
    ),
    Page(
        pageTitle = "Set threshold level and count objects",
        pageDescription = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        thumbnailResource = R.drawable.thumbnail_page_2
    ),
    Page(
        pageTitle = "Capture and Save the image",
        pageDescription = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        thumbnailResource = R.drawable.thumbnail_page_3
    )
)
