package com.example.instalens.presentation.onboarding.model

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

val pagesList = listOf(
    Page(
        pageTitle = "Detect multiple objects in realtime",
        pageDescription = "Introducing Insta Lens, designed to identify multiple objects in real-time. Harness the power of advanced machine learning to instantly recognize and label objects within your camera's view. Perfect for both casual users and professionals looking to add another layer of interactivity to their device.",
        thumbnailResource = R.drawable.thumbnail_page_1
    ),
    Page(
        pageTitle = "Set threshold level and count objects",
        pageDescription = "Enhance your object detection experience by setting custom threshold levels, allowing for precise recognition. With the added capability to count identified objects, you gain deeper insights and fine-tuned control over your environment's analysis.",
        thumbnailResource = R.drawable.thumbnail_page_2
    ),
    Page(
        pageTitle = "Capture and save the image",
        pageDescription = "Easily snap and immortalize moments with our seamless capture feature. Once you've taken the perfect shot, save it directly to your device's gallery, ensuring that every significant detail is preserved for future reference.",
        thumbnailResource = R.drawable.thumbnail_page_3
    )
)
