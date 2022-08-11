package com.example.marvelapp.extension

import androidx.test.platform.app.InstrumentationRegistry
import com.example.marvelapp.KoinTestApp
import okio.IOException
import java.io.InputStreamReader

fun String.asJsonString(): String {
    try {
        val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
            .applicationContext as KoinTestApp).assets.open(this)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    } catch (e: IOException) {
        throw e
    }
}