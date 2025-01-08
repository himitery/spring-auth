package dev.himitery.spring_auth

import dev.himitery.spring_auth.shared.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringAuthApplicationTests : FunSpec({

    test("context loads") {
    }
})
