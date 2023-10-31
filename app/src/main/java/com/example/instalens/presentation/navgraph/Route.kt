package com.example.instalens.presentation.navgraph

import com.example.instalens.presentation.utils.Routes

/**
 * Routing objects for each UI screen for NavGraph
 */
sealed class Route(
    val route: String
) {
    // Starting point
    object AppStartNavigation: Route(route = Routes.ROUTE_APP_START_NAVIGATION)
    object HomeNavigation: Route(route = Routes.ROUTE_HOME_NAVIGATION)

    // Creating Route objects for each Screen
    object OnBoardingScreen: Route(route = Routes.ROUTE_ONBOARDING_SCREEN)
    object HomeScreen: Route(route = Routes.ROUTE_HOME_SCREEN)
}
