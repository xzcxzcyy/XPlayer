package cloud.banson.xplayer.data

import android.graphics.Bitmap
import android.net.Uri
import java.net.URI

data class Video constructor(
    val uri: Uri,
    val name: String,
    val size: Int,
    var thumbNail: Bitmap?
)