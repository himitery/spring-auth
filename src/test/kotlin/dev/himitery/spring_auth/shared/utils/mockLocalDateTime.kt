package dev.himitery.spring_auth.shared.utils

import org.junit.jupiter.api.function.Executable
import org.mockito.Mockito.mockStatic
import java.time.LocalDateTime

fun mockLocalDateTime(
    executable: Executable,
    value: LocalDateTime,
    vararg values: LocalDateTime
) {
    return mockStatic(LocalDateTime::class.java).use { mock ->
        mock.`when`<LocalDateTime> { LocalDateTime.now() }.thenReturn(value, *values)
        executable.execute()
    }
}