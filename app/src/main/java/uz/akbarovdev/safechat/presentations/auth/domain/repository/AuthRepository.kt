package uz.akbarovdev.safechat.presentations.auth.domain.repository

import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.auth.domain.models.LoginRequest
import uz.akbarovdev.safechat.presentations.auth.domain.models.RegisterRequest
import uz.akbarovdev.safechat.presentations.auth.domain.models.User


interface AuthRepository {
    suspend fun login(loginReq: LoginRequest): Handler<User>
    suspend fun register(registerReq:RegisterRequest): Handler<User>
}