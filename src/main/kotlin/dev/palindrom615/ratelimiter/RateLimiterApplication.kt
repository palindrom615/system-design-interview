package dev.palindrom615.ratelimiter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class RateLimiterApplication

fun main(args: Array<String>) {
    runApplication<RateLimiterApplication>(*args)
}
