package cloud.banson.xplayer.ui.play

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cloud.banson.xplayer.R
import cloud.banson.xplayer.databinding.PlayVideoFragmentBinding
import cloud.banson.xplayer.util.MyApplication

class PlayVideoFragment : Fragment() {

    companion object {
        private const val TAG = "PLAY_VIDEO_FRAGMENT"
    }

    private lateinit var viewModel: PlayVideoViewModel
    private lateinit var binding: PlayVideoFragmentBinding
    private lateinit var videoAdapter: PlayVideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView(inflater, container)
        setUpDataAndListeners()
        return binding.root
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        val viewModelFactory = PlayVideoViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayVideoViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.play_video_fragment, container, false)
    }

    private fun setUpDataAndListeners() {
        val arguments = PlayVideoFragmentArgs.fromBundle(requireArguments())
        val onCompleteListener = { it: MediaPlayer ->
            it.stop()
            binding.viewPagerVideos.run {
                val nextPosition = (currentItem + 1) % videoAdapter.itemCount
                if (nextPosition < currentItem) {
                    setCurrentItem(nextPosition, false)
                } else {
                    setCurrentItem(nextPosition, true)
                }
            }
        }
        videoAdapter = PlayVideoAdapter(onCompleteListener)
        binding.viewPagerVideos.apply {
            adapter = videoAdapter
        }
        viewModel.playList.observe(viewLifecycleOwner, {
            videoAdapter.submitList(it)
            var startPos = 0
            if (arguments.id != -1L) {
                for (index in it.indices) {
                    if (it[index].id == arguments.id) {
                        startPos = index
                        break
                    }
                }
            }
            binding.viewPagerVideos.setCurrentItem(startPos, false)
        })
    }
}