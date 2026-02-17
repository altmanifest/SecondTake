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
import com.altmanifest.secondtake.data.ForgottenTitlesSource
import com.altmanifest.secondtake.data.Store
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.mock.MockTitleOwner
import com.altmanifest.secondtake.service.RoundFactory
import com.altmanifest.secondtake.service.SessionFactory
import com.altmanifest.secondtake.ui.screens.ComparisonScreen
import com.altmanifest.secondtake.ui.screens.ContentTypeMovieShowScreen
import com.altmanifest.secondtake.ui.screens.ContentTypeShowEpisodeScreen
import com.altmanifest.secondtake.ui.screens.ForgottenTitlesScreen
import com.altmanifest.secondtake.ui.screens.GenreScreen
import com.altmanifest.secondtake.ui.screens.OnboardingScreen
import com.altmanifest.secondtake.ui.screens.ProviderSelectionScreen
import com.altmanifest.secondtake.ui.screens.SelectSeasonScreen
import com.altmanifest.secondtake.ui.screens.SelectShowScreen
import com.altmanifest.secondtake.ui.screens.StartScreen
import com.altmanifest.secondtake.ui.viewmodel.ComparisonSetupViewModel
import com.altmanifest.secondtake.ui.viewmodel.ComparisonViewModel
import com.altmanifest.secondtake.ui.viewmodel.ForgottenTitlesViewModel
import com.altmanifest.secondtake.ui.viewmodel.GenreViewModel
import com.altmanifest.secondtake.ui.viewmodel.OnboardingViewmodel
import com.altmanifest.secondtake.ui.viewmodel.ProviderSelectionViewModel
import kotlin.time.Duration

@Composable
fun SecondTakeApp(
    navController: NavHostController = rememberNavController(),
) {
    val modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .safeContentPadding()
        .fillMaxSize()

    val comparisonSetupViewModel = remember { ComparisonSetupViewModel() }
    val mockTitleOwner = remember { MockTitleOwner() }

    NavHost(
        navController = navController,
        startDestination = Routes.Onboarding.name,
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
        composable(route = Routes.Onboarding.name) {
            OnboardingScreen(
                onContinueButtonClicked = { navController.navigate(route = Routes.Start.name) },
                modifier = modifier,
                viewmodel = OnboardingViewmodel()
            )
        }
        composable(route = Routes.Start.name) {
            StartScreen(
                onCompareButtonClicked = { navController.navigate(route = Routes.ProviderSelection.name) },
                onForgottenTitlesLinkClicked = { navController.navigate(route = Routes.ForgottenTitles.name) },
                modifier = modifier
            )
        }
        composable(route = Routes.ProviderSelection.name) {
            ProviderSelectionScreen(
                onProviderButtonClicked = { navController.navigate(route = Routes.ContentTypeMovieShow.name) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier,
                viewModel = ProviderSelectionViewModel(),
            )
        }
        composable(route = Routes.ContentTypeMovieShow.name) {
            ContentTypeMovieShowScreen(
                viewModel = comparisonSetupViewModel,
                onMovieButtonClicked = { navController.navigate(route = Routes.Genre.name) },
                onShowButtonClicked = { navController.navigate(route = Routes.ContentTypeShowEpisode.name) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = Routes.ContentTypeShowEpisode.name) {
            ContentTypeShowEpisodeScreen(
                viewModel = comparisonSetupViewModel,
                onEpisodeButtonClicked = { navController.navigate(route = Routes.SelectShow.name) },
                onShowButtonClicked = { navController.navigate(route = Routes.Genre.name) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = Routes.Genre.name) {
            GenreScreen(
                viewModel = GenreViewModel(genreAccessor = mockTitleOwner),
                onContinueButtonClicked = { navController.navigate(route = Routes.Comparison.name) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = Routes.SelectShow.name) {
            SelectShowScreen(
                viewModel = comparisonSetupViewModel,
                onContinueButtonClicked = { navController.navigate(route = Routes.SelectSeason.name) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = Routes.SelectSeason.name) {
            SelectSeasonScreen(
                viewModel = comparisonSetupViewModel,
                onContinueButtonClicked = { navController.navigate(route = Routes.Comparison.name) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = Routes.Comparison.name) {
            ComparisonScreen(
                onHomeButtonClicked = { navController.navigate(route = Routes.Start.name) },
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
                        forgottenTitleSource = ForgottenTitlesSource(
                            Store(createStore())
                        ),
                        titleOwner = mockTitleOwner
                    ))},
                modifier = modifier
            )
        }
        composable(route = Routes.ForgottenTitles.name) {
            ForgottenTitlesScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = remember {
                    ForgottenTitlesViewModel(
                        forgottenTitleSource = ForgottenTitlesSource(
                           Store(createStore())
                        ),
                    )},
                modifier = modifier
            )
        }
    }
}