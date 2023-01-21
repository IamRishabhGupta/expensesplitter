package com.example.expensesplitter.models

import android.os.Parcel
import android.os.Parcelable

data class Group(
    val title:String = "",
    var amtList: DoubleArray = ArrayList<Double>() as DoubleArray,
    var friendList: ArrayList<String> = ArrayList(),
    val date:String = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createDoubleArray()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(title)
        parcel?.writeDoubleArray(amtList)
        parcel?.writeStringList(friendList)
        parcel?.writeString(date)
    }

    companion object CREATOR : Parcelable.Creator<Group> {
        override fun createFromParcel(parcel: Parcel): Group {
            return Group(parcel)
        }

        override fun newArray(size: Int): Array<Group?> {
            return arrayOfNulls(size)
        }
    }
}
