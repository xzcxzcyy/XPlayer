package cloud.banson.xplayer.ui.select

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.data.VideoDatabase
import cloud.banson.xplayer.data.VideoInfo
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

class SelectVideoViewModel(private val appContext: Context) : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val videoList = mutableListOf<VideoInfo>()
    private val videoDatabaseDao = VideoDatabase.getInstance(appContext).videoDatabaseDao
    val videoListLive = MutableLiveData<MutableList<VideoInfo>>()

    init {
        uiScope.launch {
            updateVideoList()
        }
    }

    fun onItemClick(item: VideoInfo) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val destination = File(appContext.filesDir, item.name)
                item.uri.copyTo(destination)
                val newItem = Video(
                    0,
                    destination.toUri().path!!,
                    destination.name,
                    System.currentTimeMillis()
                )
                videoDatabaseDao.insert(newItem)
            }
        }
    }

    @SuppressLint("NewApi")
    private suspend fun updateVideoList() {
        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE
            )

            val sortOrder = "${MediaStore.Video.Media.DATE_MODIFIED} DESC"

            val query = appContext.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )

            query?.use { cursor ->
                // Cache column indices.
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

                while (cursor.moveToNext()) {
                    // Get values of columns for a given video.
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val size = cursor.getInt(sizeColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val thumbNail = appContext.contentResolver.loadThumbnail(
                        contentUri,
                        Size(640, 640), null
                    )

                    // Stores column values and the contentUri in a local object
                    // that represents the media file.
                    videoList += VideoInfo(contentUri, name, size, thumbNail)
                }
            }
            withContext(Dispatchers.Main) {
                videoListLive.value = videoList
            }
        }
    }

    private fun Uri.copyTo(destination: File) {
        val inChannel = appContext.contentResolver.openInputStream(this)
        val outChannel = FileOutputStream(destination)
        try {
            val buffer = ByteArray(1024)
            var length: Int

            while (inChannel!!.read(buffer).also { length = it } > 0) {
                outChannel.write(buffer, 0, length)
            }
        } finally {
            inChannel?.close()
            outChannel.close()
        }
    }

}