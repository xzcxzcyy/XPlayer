package cloud.banson.xplayer.data

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

@Entity(tableName = "internalVideos")
data class Video constructor(
    @ColumnInfo(name = "uri")
    val uri: Uri,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "size")
    val size: Int,

    @ColumnInfo(name = "thumbNail")
    var thumbNail: Bitmap?,

    @PrimaryKey(autoGenerate = true)
    var databaseId: Long = 0L
)