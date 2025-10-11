package uz.akbarovdev.safechat.presentations.home.domain.repositories

import uz.akbarovdev.safechat.presentations.auth.domain.models.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun searchUser(query: String): List<User>
}