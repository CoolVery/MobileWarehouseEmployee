package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Role (
    @SerialName("id")
    val id: Int,
    @SerialName("name_role")
    val nameRole: String
)