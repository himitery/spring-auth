package dev.himitery.spring_auth

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<SpringAuthApplication>().with(TestcontainersConfiguration::class).run(*args)
}
