package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product (
    @SerialName("id")
    val id: Int,
    @SerialName("product_name")
    val productName: String,
    @SerialName("article")
    val article: String,
    @SerialName("weight")
    val weight: Float,
    @SerialName("id_product_category")
    val idProductCategory: Int
)