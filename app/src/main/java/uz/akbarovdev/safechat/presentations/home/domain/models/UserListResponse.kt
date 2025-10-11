package uz.akbarovdev.safechat.presentations.home.domain.models

import uz.akbarovdev.safechat.presentations.auth.domain.models.User

data class UserListResponse(
    val users: List<User>
)
