package cloud.banson.xplayer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Video::class], version = 3, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    abstract val videoDatabaseDao: VideoDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: VideoDatabase? = null

        fun getInstance(context: Context): VideoDatabase {
            synchronized(VideoDatabase::class) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VideoDatabase::class.java,
                        "video_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}