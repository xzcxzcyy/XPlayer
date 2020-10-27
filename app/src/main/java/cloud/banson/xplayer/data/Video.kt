package cloud.banson.xplayer.data

import android.net.Uri
import java.net.URI

data class Video constructor(
    val uri: Uri,
    val name: String,
    val duration: Int,
    val size: Int
)