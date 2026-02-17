package com.altmanifest.secondtake

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altmanifest.secondtake.application.AvailableProvider
import com.altmanifest.secondtake.application.ConnectProviderUseCase
import com.altmanifest.secondtake.application.DefinedProvider
import com.altmanifest.secondtake.data.ProviderSource
import com.altmanifest.secondtake.data.Store
import com.altmanifest.secondtake.di.DependencyGraph
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Round
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
import com.altmanifest.secondtake.ui.viewmodel.OnboardingViewmodel
import com.altmanifest.secondtake.ui.viewmodel.ProviderSelectionViewModel
import kotlin.time.Duration

val localDependencyGraph = staticCompositionLocalOf<DependencyGraph> {
    error("No graph provided")
}

@Composable
fun SecondTakeApp(
    navController: NavHostController = rememberNavController(),
) {

    val modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .safeContentPadding()
        .fillMaxSize()

    val comparisonSetupViewModel = remember { ComparisonSetupViewModel() }
    val store = remember { Store(createStore()) }
    val providerSource = remember { ProviderSource(store) }

    val graph = remember { DependencyGraph(store) }

    CompositionLocalProvider(localDependencyGraph provides graph) {
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
                            ConnectProviderUseCase(
                                source = providerSource,
                                config = ConnectProviderUseCase.Config(
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
                    viewModel = ProviderSelectionViewModel(providerSource, {graph.selectProvider(it)}),
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
                    viewModel = remember { graph.createGenreViewmodel() },
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
                        graph.createComparisonViewmodel(
                            savedStateHandle = it.savedStateHandle,
                            comparisonConfig = Comparison.Config(
                                maxPointDifference = 1.0,
                                minRatingAge = Duration.ZERO
                            ),
                            capacity = Round.Capacity(10)
                        )
                    },
                    modifier = modifier
                )
            }
            composable<Routes.ForgottenTitles> {
                ForgottenTitlesScreen(
                    onBackClick = { navController.popBackStack() },
                    viewModel = remember {
                        graph.createForgottenTitlesViewmodel()
                    },
                    modifier = modifier
                )
            }
        }
    }
}