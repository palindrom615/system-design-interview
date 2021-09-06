package dev.palindrom615.ratelimiter.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class FirstController {
    @GetMapping("ep1")
    fun endpoint1(): String {
        return "endpoint 1"
    }
    @GetMapping("ep2")
    fun endpoint2(): String {
        return "endpoint 2"
    }
}