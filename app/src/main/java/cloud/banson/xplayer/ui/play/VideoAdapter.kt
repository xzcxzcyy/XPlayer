package cloud.banson.xplayer.ui.play

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.databinding.PlayVideoItemBinding
import cloud.banson.xplayer.util.VideoDiffCallback

class VideoAdapter(private val onCompleteListener: MediaPlayer.OnCompletionListener) :
    ListAdapter<Video, VideoAdapter.ViewHolder>(VideoDiffCallback()) {

    companion object {
        private const val TAG = "PLAY_VIDEO_ADAPTER"
    }

    class ViewHolder private constructor(
        private val binding: PlayVideoItemBinding,
        private val onCompleteListener: MediaPlayer.OnCompletionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup,
                onCompleteListener: MediaPlayer.OnCompletionListener
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlayVideoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onCompleteListener)
            }
        }

        fun bind(video: Video) {
            binding.textTitle.text = video.name
            val mVideoView = binding.videoView.apply {
                setVideoURI(Uri.parse(video.uriString))
                setOnPreparedListener { mediaPlayer ->
//                    binding.progressBar.visibility = View.GONE
                    mediaPlayer.start()
                }
                setOnCompletionListener(onCompleteListener)
            }
            binding.seekBar.apply {
                max = binding.videoView.duration
                progress = 0
            }
            binding.seekBar.let { seekbar ->
                val delayedAction: Runnable = object : Runnable {
                    override fun run() {
                        seekbar.progress = mVideoView.currentPosition
                        if (mVideoView.isPlaying) {
                            seekbar.postDelayed(this, 10)
                        }
                    }
                }
                seekbar.postDelayed(delayedAction, 10)
            }
        }

        fun stopOnLeaving() {
            binding.videoView.pause()
            Log.d(TAG, "stopOnLeaving: Pausing..")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, onCompleteListener)
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
