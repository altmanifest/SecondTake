package com.altmanifest.secondtake

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class SecondTakeRoutes {
    Onboarding,
    Start,
    ProviderSelection,
    ForgottenTitles
}

@Composable
fun SecondTakeApp(
    navController: NavHostController = rememberNavController(),
) {
    val modifier = Modifier
        .background(color = MaterialTheme.colorScheme.primaryContainer)
        .safeContentPadding()
        .fillMaxSize()

    NavHost(
        navController = navController,
        startDestination = SecondTakeRoutes.Onboarding.name
    ) {
        composable(route = SecondTakeRoutes.Onboarding.name) {
            OnboardingScreen(
                onContinueButtonClicked = { navController.navigate(route = SecondTakeRoutes.Start.name) },
                modifier = modifier
            )
        }
        composable(route = SecondTakeRoutes.Start.name) {
            StartScreen(
                onCompareButtonClicked = { navController.navigate(route = SecondTakeRoutes.ProviderSelection.name) },
                onForgottenTitlesLinkClicked = { navController.navigate(route = SecondTakeRoutes.ForgottenTitles.name) },
                modifier = modifier
            )
        }
    }
}