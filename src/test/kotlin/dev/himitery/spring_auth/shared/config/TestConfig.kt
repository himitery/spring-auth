package dev.himitery.spring_auth.shared.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import org.springframework.boot.test.context.TestConfiguration

@TestConfiguration
class TestConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(
        SpringExtension,
        SpringTestExtension(SpringTestLifecycleMode.Test)
    )
}