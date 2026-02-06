package com.altmanifest.secondtake.domain

import kotlin.math.abs
import kotlin.time.Duration

data class Rating(val value: Int, val age: Duration) {
    fun pointDifference(other: Rating): Int = abs(other.value - this.value)
    fun comparableWith(other: Rating, tolerance: Int): Boolean = pointDifference(other) <= tolerance
    fun notComparableWith(other: Rating, tolerance: Int): Boolean = !comparableWith(other, tolerance)
}