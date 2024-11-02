package com.example.warehouseemployee.presentation.navigathion

interface Destination {
    val route: String
    val title: String
}

object AuthorizationDestination : Destination {
    override val route = "authorization"
    override val title = "Authorization"
}