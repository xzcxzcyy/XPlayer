package cloud.banson.xplayer.ui.play

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.databinding.PlayVideoItemBinding
import cloud.banson.xplayer.util.VideoDiffCallback

class VideoAdapter : ListAdapter<Video, VideoAdapter.ViewHolder>(VideoDiffCallback()) {

    companion object {
        private const val TAG = "PLAY_VIDEO_ADAPTER"
    }

    class ViewHolder private constructor(
        private val binding: PlayVideoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlayVideoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(video: Video) {
            binding.apply {
                textTitle.text = video.name
                //TODO: SETUP PLAYING EVENT
            }
        }

        fun stopOnLeaving() {
//            binding.apply {
//                videoView.pause()
//            }
            Log.d(TAG, "stopOnLeaving: Pausing..")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
        Log.d(TAG, "onBindViewHolder: Auto Display...")
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopOnLeaving()
    }
}
