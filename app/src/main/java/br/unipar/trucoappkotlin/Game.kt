package br.unipar.trucoappkotlin

import android.os.Parcel
import android.os.Parcelable

data class Game(
    val time1: String,
    val time2: String,
    val ptTime1: Int,
    val ptTime2: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(time1)
        parcel.writeString(time2)
        parcel.writeInt(ptTime1)
        parcel.writeInt(ptTime2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }
}
