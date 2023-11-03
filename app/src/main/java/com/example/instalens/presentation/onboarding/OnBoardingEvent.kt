package com.example.instalens.presentation.onboarding

/**
 * Defines the events related to the onboarding process.
 * A sealed class is used to represent a finite set of possible events.
 * This allows for exhaustive when statements over the events, ensuring all cases are handled.
 */
sealed class OnBoardingEvent {

    /**
     * Represents an event where user configuration needs to be written to the Preference Datastore.
     * This can include saving user settings or preferences that are set during the onboarding process.
     */
    object WriteUserConfigToDataStore: OnBoardingEvent()
}
