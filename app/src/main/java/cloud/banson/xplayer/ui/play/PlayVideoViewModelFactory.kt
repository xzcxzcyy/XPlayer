package cloud.banson.xplayer.ui.play

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayVideoViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayVideoViewModel::class.java)) {
            return PlayVideoViewModel(appContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}