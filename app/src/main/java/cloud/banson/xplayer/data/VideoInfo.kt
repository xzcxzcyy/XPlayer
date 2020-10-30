package cloud.banson.xplayer.data

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

data class VideoInfo constructor(
    var uri: Uri,
    var name: String,
    var size: Int,
    var thumbNail: Bitmap?,
    var databaseId: Long = 0L
)