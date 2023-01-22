package com.example.expensesplitter.models

import android.os.Parcel
import android.os.Parcelable

data class friend(
    var name : String,
    var id : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<friend> {
        override fun createFromParcel(parcel: Parcel): friend {
            return friend(parcel)
        }

        override fun newArray(size: Int): Array<friend?> {
            return arrayOfNulls(size)
        }
    }
}
