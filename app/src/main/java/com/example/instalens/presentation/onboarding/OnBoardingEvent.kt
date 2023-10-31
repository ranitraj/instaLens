package com.example.instalens.presentation.onboarding

sealed class OnBoardingEvent {
    // Event to write user-config into Preference Datastore
    object WriteUserConfigToDataStore: OnBoardingEvent()
}
