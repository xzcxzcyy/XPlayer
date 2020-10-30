package cloud.banson.xplayer.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDatabaseDao {
    @Insert
    fun insert(item: Video)

    @Update
    fun update(item: Video)

    @Delete
    fun delete(item: Video)

    @Query("SELECT * FROM internalVideo WHERE id=:targetId")
    fun getItemById(targetId: Long): Video?

    @Query("SELECT * FROM internalVideo")
    fun getItemList(): LiveData<List<Video>>
}