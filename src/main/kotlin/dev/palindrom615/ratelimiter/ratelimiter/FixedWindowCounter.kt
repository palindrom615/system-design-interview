package dev.palindrom615.ratelimiter.ratelimiter

import kotlin.jvm.Throws

interface FixedWindowCounter {
    @Throws(RateLimitError::class)
    fun request(remoteAddr: String): Int
}