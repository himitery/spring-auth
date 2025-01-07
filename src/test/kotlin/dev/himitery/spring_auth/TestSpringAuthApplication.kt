package dev.himitery.spring_auth

import dev.himitery.spring_auth.config.TestcontainersConfig
import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<SpringAuthApplication>()
        .with(TestcontainersConfig::class)
        .run(*args)
}
