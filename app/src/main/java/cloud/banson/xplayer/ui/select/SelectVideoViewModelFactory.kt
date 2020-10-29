package cloud.banson.xplayer.ui.select

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectVideoViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectVideoViewModel::class.java)) {
            return SelectVideoViewModel(appContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}