package com.dicoding.myview_app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Iphone(
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable