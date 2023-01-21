package com.example.expensesplitter.models

import android.os.Parcel
import android.os.Parcelable

data class Expense(
    val title:String = "",
    val amount:Double,
    val description:String = "",
    val tag:String = "",
    val date:String = "",
    val extra:Boolean
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(title)
        parcel?.writeDouble(amount)
        parcel?.writeString(description)
        parcel?.writeString(tag)
        parcel?.writeString(date)
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }
}
