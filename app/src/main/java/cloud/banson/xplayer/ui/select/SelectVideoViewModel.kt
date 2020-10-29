package cloud.banson.xplayer.ui.select

import android.content.Context
import androidx.lifecycle.ViewModel
import cloud.banson.xplayer.data.Video

class SelectVideoViewModel(appContext: Context) : ViewModel() {
    val videoList = mutableListOf<Video>()
    
}