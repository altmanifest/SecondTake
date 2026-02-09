package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.application.TitleProvider
import com.altmanifest.secondtake.domain.Genre
import com.altmanifest.secondtake.domain.Rating
import com.altmanifest.secondtake.domain.Title
import kotlin.time.Duration.Companion.days

class TitleProvider: TitleProvider {
    override fun getAll() : Set<Title> {
        return mockActionMovies.toSet()
    }

    override fun getByGenre(genre: Genre): Set<Title> {
        return mockActionMovies.toSet()
    }

    companion object {
        val mockActionMovies = listOf(
            Title(
                value = "Mad Max: Fury Road",
                posterUrl = "https://image.tmdb.org/t/p/original/8tZYtuWezp8JbcsvHYO0O46tFbo.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 8.1, age = 3000.days),
                id = Title.Id("w")
            ),
            Title(
                value = "John Wick",
                posterUrl = "https://i.ebayimg.com/images/g/2IMAAOSw-7RVHsuR/s-l400.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 7.4, age = 3200.days),
                id = Title.Id("a")
            ),
            Title(
                value = "The Dark Knight",
                posterUrl = "https://image.tmdb.org/t/p/original/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 9.0, age = 5000.days),
                id = Title.Id("c")
            ),
            Title(
                value = "Inception",
                posterUrl = "https://image.tmdb.org/t/p/original/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 8.8, age = 4500.days),
                id = Title.Id("v")
            ),
            Title(
                value = "Terminator 2: Judgment Day",
                posterUrl = "https://m.media-amazon.com/images/I/71EiohCUjUL._AC_UF894,1000_QL80_.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 8.6, age = 10000.days),
                id = Title.Id("n")
            ),
            Title(
                value = "Gladiator",
                posterUrl = "https://image.tmdb.org/t/p/original/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 8.5, age = 8000.days),
                id = Title.Id("m")
            ),
            Title(
                value = "The Matrix",
                posterUrl = "https://image.tmdb.org/t/p/original/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 8.7, age = 8500.days),
                id = Title.Id("l")
            ),
            Title(
                value = "Die Hard",
                posterUrl = "https://media.posterlounge.com/img/products/710000/705263/705263_poster.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 8.2, age = 12000.days),
                id = Title.Id("o")
            ),
            Title(
                value = "Mission: Impossible - Fallout",
                posterUrl = "https://i.ebayimg.com/images/g/~i0AAOSw~AhbXqwW/s-l1200.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 7.7, age = 1500.days),
                id = Title.Id("p")
            ),
            Title(
                value = "Avengers: Endgame",
                posterUrl = "https://image.tmdb.org/t/p/original/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                genre = Genre("Action"),
                rating = Rating(value = 8.4, age = 1200.days),
                id = Title.Id("z")
            )
        )
    }
}