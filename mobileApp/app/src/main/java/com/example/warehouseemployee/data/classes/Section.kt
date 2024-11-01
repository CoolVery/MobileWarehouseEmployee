package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Section (
    @SerialName("id")
    val id: Int,
    @SerialName("name_section")
    val nameSection: String,
    @SerialName("id_warehouse")
    val idWarehouse: Int,
    @SerialName("abbreviated_name")
    val abbreviatedName: String
)