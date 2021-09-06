package dev.palindrom615.ratelimiter.ratelimiter

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.jvm.Throws

@Component
class AtomicMapFixedWindowCounter(
    @Value("\${ratelimit.duration:60000}") val duration: Long,
    @Value("\${ratelimit.limit:10}") val limit: Int
) : FixedWindowCounter {
    private val map: ConcurrentHashMap<String, AtomicInteger> = ConcurrentHashMap()
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelayString = "\${ratelimit.duration:60000}")
    fun clear() {
        map.clear()
        logger.info("FixedWindowCounter cleared")
    }

    @Throws(RateLimitError::class)
    override fun request(remoteAddr: String): Int {
        map.putIfAbsent(remoteAddr, AtomicInteger(0))
        val count = map[remoteAddr]!!.incrementAndGet()
        if (count > limit) {
            throw RateLimitError("API limit exceeded! Try after ${duration / 1000} seconds")
        }
        return count
    }
}