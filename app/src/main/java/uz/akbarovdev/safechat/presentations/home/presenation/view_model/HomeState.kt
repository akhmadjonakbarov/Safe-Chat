package uz.akbarovdev.safechat.presentations.home.presenation.view_model

import uz.akbarovdev.safechat.presentations.home.domain.models.User

data class HomeState(
    val users: List<User> = emptyList()
)