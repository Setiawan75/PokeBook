package com.world.pokebook.screen.shared.utils

import android.content.Intent
import android.os.Build
import android.os.Parcelable

inline fun<reified T: Parcelable> Intent.getParcelableIntent(key: String): T? {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        @Suppress("DEPRECATION")
        getParcelableExtra(key)
    } else {
        getParcelableExtra(key, T::class.java)
    }
}