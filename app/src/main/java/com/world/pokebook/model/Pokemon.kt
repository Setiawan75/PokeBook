package com.world.pokebook.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val name: String = "",
    val url: String = "",
) : Parcelable

data class Result(val results: ArrayList<Pokemon>)
