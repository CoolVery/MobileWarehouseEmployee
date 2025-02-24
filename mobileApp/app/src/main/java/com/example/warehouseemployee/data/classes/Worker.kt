package com.example.warehouseemployee.data.classes

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Worker (
    @SerialName("id")
    val id: Int,
    @SerialName("id_worker")
    val idWorker: String,
    @SerialName("id_role")
    val idRole: Int,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("patronymic")
    val patronymic: String,
    @SerialName("id_warehouse")
    val idWarehouse: Int
)