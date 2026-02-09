package com.altmanifest.secondtake

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altmanifest.secondtake.application.CompareTitlesUseCase
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.mock.MockForgottenTitleSource
import com.altmanifest.secondtake.mock.MockTitleOwner
import com.altmanifest.secondtake.service.RoundFactory
import com.altmanifest.secondtake.service.SessionFactory
import com.altmanifest.secondtake.ui.screens.ComparisonScreen
import com.altmanifest.secondtake.ui.screens.OnboardingScreen
import com.altmanifest.secondtake.ui.screens.ProviderSelectionScreen
import com.altmanifest.secondtake.ui.screens.StartScreen
import com.altmanifest.secondtake.ui.viewmodel.ComparisonViewModel
import kotlin.time.Duration

enum class SecondTakeRoutes {
    Onboarding,
    Start,
    ProviderSelection,
    ForgottenTitles,
    Comparison
}

@Composable
fun SecondTakeApp(
    navController: NavHostController = rememberNavController(),
) {
    val modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .safeContentPadding()
        .fillMaxSize()

    NavHost(
        navController = navController,
        startDestination = SecondTakeRoutes.Onboarding.name,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 500)
            )},
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 500)
            )},
        modifier = modifier
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
        composable(route = SecondTakeRoutes.ProviderSelection.name) {
            ProviderSelectionScreen(
                onProviderButtonClicked = { navController.navigate(route = SecondTakeRoutes.Comparison.name) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = SecondTakeRoutes.Comparison.name) {
            ComparisonScreen(
                onHomeButtonClicked = { navController.navigate(route = SecondTakeRoutes.Start.name) },
                onBackButtonClicked = { navController.popBackStack() },
                viewModel = remember {ComparisonViewModel(
                    useCase = CompareTitlesUseCase(
                        sessionFactory = SessionFactory(
                            roundFactory = RoundFactory(
                                comparisonConfig = Comparison.Config(
                                    maxPointDifference = 1.0,
                                    minRatingAge = Duration.ZERO
                                ),
                                capacity = Round.Capacity(10)
                            )
                        ),
                        forgottenTitleSource = MockForgottenTitleSource(),
                        titleOwner = MockTitleOwner()
                    ))},
                modifier = modifier
            )
        }
    }
}