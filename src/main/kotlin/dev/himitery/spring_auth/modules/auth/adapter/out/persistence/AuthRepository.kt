package dev.himitery.spring_auth.modules.auth.adapter.out.persistence

import dev.himitery.spring_auth.modules.auth.domain.model.Auth
import org.springframework.data.jpa.repository.JpaRepository


interface AuthRepository : JpaRepository<Auth, String>