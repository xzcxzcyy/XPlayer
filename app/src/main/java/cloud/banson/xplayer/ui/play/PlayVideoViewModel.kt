package cloud.banson.xplayer.ui.play

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.data.VideoDatabase

class PlayVideoViewModel(private val appContext: Context) : ViewModel() {
    private val database = VideoDatabase.getInstance(appContext).videoDatabaseDao
    val playList: LiveData<List<Video>> = database.getItemList()
}