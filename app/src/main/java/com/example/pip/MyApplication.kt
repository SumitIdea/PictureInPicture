package com.example.pip

import android.app.Application


class MyApplication : Application() {
    var mode = MODE_NONE
    var orgDensityDpi = 0

    companion object {
        const val MODE_NONE = 0
        const val MODE_FULL = 1
        const val MODE_PIP = 2
    }
}