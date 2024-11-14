package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cell(
    @SerialName("id")
    val id: Int,
    @SerialName("id_section")
    val idSection: Int,
    @SerialName("id_product")
    val idProduct: Int,
    @SerialName("name_cell")
    val nameCell: String,
    @SerialName("max_count_product_in_cell")
    val maxCountProductInCell: Int,
    @SerialName("count_product_in_cell")
    val countProductInCell: Int,
    @SerialName("weight_product_in_cell")
    val weightProductInCell: Float,
    @SerialName("abbreviated_name")
    val abbreviatedName: String
)