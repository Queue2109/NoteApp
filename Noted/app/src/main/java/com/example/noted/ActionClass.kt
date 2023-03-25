package com.example.noted
import android.os.Parcel
import android.os.Parcelable

data class ActionClass(val checked: Boolean, val description: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (checked) 1 else 0)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ActionClass> {
        override fun createFromParcel(parcel: Parcel): ActionClass {
            return ActionClass(parcel)
        }

        override fun newArray(size: Int): Array<ActionClass?> {
            return arrayOfNulls(size)
        }
    }
}
