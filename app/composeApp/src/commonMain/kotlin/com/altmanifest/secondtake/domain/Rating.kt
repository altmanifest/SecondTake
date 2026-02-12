package com.altmanifest.secondtake.domain

import kotlinx.serialization.Serializable
import kotlin.math.abs
import kotlin.time.Duration

@Serializable
data class Rating(val value: Double, val age: Duration) {
    fun pointDifference(other: Rating): Double = abs(other.value - this.value)
    fun comparableWith(other: Rating, tolerance: Double): Boolean = pointDifference(other) <= tolerance
    fun notComparableWith(other: Rating, tolerance: Double): Boolean = !comparableWith(other, tolerance)
}