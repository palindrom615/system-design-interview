package dev.palindrom615.ratelimiter.ratelimiter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

@Component
class FixedWindowCounterRateLimiterFilter : Filter {
    final val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    lateinit var counter: FixedWindowCounter
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        try {
            counter.request(request!!.remoteAddr)
        } catch(e: RateLimitError) {
            logger.error("API limit exceeded from ${request!!.remoteAddr}")
            (response as HttpServletResponse).status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.writer.write(e.message)
            return
        }
        chain?.doFilter(request, response)
    }
}