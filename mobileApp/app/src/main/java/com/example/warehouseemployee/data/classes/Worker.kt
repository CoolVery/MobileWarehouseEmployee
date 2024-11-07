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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(idWorker)
        parcel.writeInt(idRole)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(patronymic)
        parcel.writeInt(idWarehouse)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Worker> {
        override fun createFromParcel(parcel: Parcel): Worker {
            return Worker(parcel)
        }

        override fun newArray(size: Int): Array<Worker?> {
            return arrayOfNulls(size)
        }
    }
}
