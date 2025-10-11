package uz.akbarovdev.safechat.presentations.auth.presentation.view_model

interface AuthEvent {
    data object LoginSuccess : AuthEvent
    data object RegisterSuccess : AuthEvent
    data object HasError : AuthEvent
}