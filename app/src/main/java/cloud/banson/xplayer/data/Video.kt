package cloud.banson.xplayer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "internalVideo")
data class Video(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "uri")
    var uriString: String = "",

    @ColumnInfo(name = "fileName")
    var name: String = "",

    @ColumnInfo(name = "time")
    var time: Long = 0L
)