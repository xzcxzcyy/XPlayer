package cloud.banson.xplayer.ui.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.data.VideoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ListViewModel(appContext: Context) : ViewModel() {
    private val database = VideoDatabase.getInstance(appContext).videoDatabaseDao

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    var resourceList: LiveData<List<Video>> = database.getItemList()
}