package uz.akbarovdev.safechat.presentations.home.data.repositories

import uz.akbarovdev.safechat.presentations.home.data.remote.UserApi
import uz.akbarovdev.safechat.presentations.home.domain.models.User
import uz.akbarovdev.safechat.presentations.home.domain.repositories.UserRepository

class UserRepositoryIml(
    val userApi: UserApi
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        val response = userApi.getUsers()
        return if (response.isSuccessful) {
            response.body()?.users ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun searchUser(query: String): List<User> {
        TODO("Not yet implemented")
    }
}