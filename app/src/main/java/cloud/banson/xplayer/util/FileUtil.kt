package cloud.banson.xplayer.util

import android.net.Uri
import android.util.Log
import java.io.File
import java.lang.Exception

fun File.copyTo(destination: File) {
    inputStream().use { input ->
        destination.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}

fun Uri.copyTo(destination: File) {
    try {
        val source = File(path!!)
        source.copyTo(destination)
    } catch (e: Exception) {
        Log.e("file copy utils", "Uri.copyTo: ${e.message}")
    }
}
