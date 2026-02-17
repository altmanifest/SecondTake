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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altmanifest.secondtake.application.AvailableProvider
import com.altmanifest.secondtake.application.CompareTitlesUseCase
import com.altmanifest.secondtake.application.ConnectedProviderUseCase
import com.altmanifest.secondtake.application.DefinedProvider
import com.altmanifest.secondtake.data.ConnectedProviderSource
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
    val store = remember { Store(createStore()) }

    NavHost(
        navController = navController,
        startDestination = Routes.Onboarding,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 500)
            )
        },
        modifier = modifier
    ) {
        composable<Routes.Onboarding> {
            OnboardingScreen(
                onContinueButtonClicked = { navController.navigate(route = Routes.Start) },
                modifier = modifier,
                viewmodel = remember {
                    OnboardingViewmodel(
                        ConnectedProviderUseCase(
                            source = ConnectedProviderSource(
                                store = store,
                            ),
                            config = ConnectedProviderUseCase.Config(
                                availableProviders = listOf(
                                    AvailableProvider.Planned(DefinedProvider.IMDB),
                                    AvailableProvider.Planned(DefinedProvider.FILMWEB),
                                    AvailableProvider.Planned(DefinedProvider.ONLYFILMS),
                                    AvailableProvider.Disconnected(DefinedProvider.MOCKIFY)
                                )
                            )
                        )
                    )
                }
            )
        }
        composable<Routes.Start> {
            StartScreen(
                onCompareButtonClicked = { navController.navigate(route = Routes.ProviderSelection) },
                onForgottenTitlesLinkClicked = { navController.navigate(route = Routes.ForgottenTitles) },
                modifier = modifier
            )
        }
        composable<Routes.ProviderSelection> {
            ProviderSelectionScreen(
                onProviderButtonClicked = { navController.navigate(route = Routes.ContentTypeMovieShow) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier,
                viewModel = ProviderSelectionViewModel(),
            )
        }
        composable<Routes.ContentTypeMovieShow> {
            ContentTypeMovieShowScreen(
                viewModel = comparisonSetupViewModel,
                onMovieButtonClicked = { navController.navigate(route = Routes.Genre) },
                onShowButtonClicked = { navController.navigate(route = Routes.ContentTypeShowEpisode) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable<Routes.ContentTypeShowEpisode> {
            ContentTypeShowEpisodeScreen(
                viewModel = comparisonSetupViewModel,
                onEpisodeButtonClicked = { navController.navigate(route = Routes.SelectShow) },
                onShowButtonClicked = { navController.navigate(route = Routes.Genre) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable<Routes.Genre> {
            GenreScreen(
                viewModel = remember { GenreViewModel(genreAccessor = mockTitleOwner) },
                onContinueButtonClicked = { navController.navigate(Routes.Comparison(genre = it)) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable<Routes.SelectShow> {
            SelectShowScreen(
                viewModel = comparisonSetupViewModel,
                onContinueButtonClicked = { navController.navigate(route = Routes.SelectSeason) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable<Routes.SelectSeason> {
            SelectSeasonScreen(
                viewModel = comparisonSetupViewModel,
                onContinueButtonClicked = { navController.navigate(route = Routes.Comparison) },
                onBackButtonClicked = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable<Routes.Comparison> {
            ComparisonScreen(
                onHomeButtonClicked = { navController.navigate(route = Routes.Start) },
                onBackButtonClicked = { navController.popBackStack() },
                viewModel = viewModel {
                    ComparisonViewModel(
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
                                store
                            ),
                            titleOwner = mockTitleOwner
                        ), savedStateHandle = it.savedStateHandle
                    )
                },
                modifier = modifier
            )
        }
        composable<Routes.ForgottenTitles> {
            ForgottenTitlesScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = remember {
                    ForgottenTitlesViewModel(
                        forgottenTitleSource = ForgottenTitlesSource(
                            store
                        ),
                    )
                },
                modifier = modifier
            )
        }
    }
}