package com.example.instalens.presentation.navgraph

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.instalens.presentation.home.HomeScreen
import com.example.instalens.presentation.onboarding.OnBoardingScreen
import com.example.instalens.presentation.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun NavGraph(
    context: Context,
    startDestination: String
) {
    val navController: NavHostController = rememberNavController()

    // NavHost for allowing self contained navigation to occur
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // OnBoarding Screen SubGraph
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                // Saving userConfig flag to DataStore via viewModel
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        // Home Screen Navigation
        navigation(
            route = Route.HomeNavigation.route,
            startDestination = Route.HomeScreen.route
        ) {
            composable(
                route = Route.HomeScreen.route
            ) {
                HomeScreen(context = context)
            }
        }
    }
}