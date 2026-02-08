package com.altmanifest.secondtake.domain

import kotlin.math.abs
import kotlin.time.Duration

data class Rating(val value: Double, val age: Duration) {
    fun pointDifference(other: Rating): Double = abs(other.value - this.value)
    fun comparableWith(other: Rating, tolerance: Int): Boolean = pointDifference(other) <= tolerance
    fun notComparableWith(other: Rating, tolerance: Int): Boolean = !comparableWith(other, tolerance)
}