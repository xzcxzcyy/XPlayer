package cloud.banson.xplayer.ui.select

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import cloud.banson.xplayer.R
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.data.VideoDatabase
import cloud.banson.xplayer.databinding.SelectVideoFragmentBinding
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

class SelectVideoFragment : Fragment() {

    companion object {
        private const val TAG = "SelectVideoFragment"
    }

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var viewModel: SelectVideoViewModel
    private lateinit var binding: SelectVideoFragmentBinding
    private lateinit var myAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView(inflater, container)
        initData()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.select_video_fragment, container, false)
        val videoDatabaseDao = VideoDatabase.getInstance(context!!).videoDatabaseDao
        //TODO: CONVERT LISTENER
        myAdapter = VideoAdapter {
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    val destination = File(context!!.filesDir, it.name)
                    it.uri.copyTo(destination)
                    val newItem = Video(0, destination.toUri().path!!, destination.name)
                    videoDatabaseDao.insert(newItem)
                }
            }
        }
        binding.selectRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = myAdapter
        }
    }

    private fun initData() {
        val viewModelFactory = SelectVideoViewModelFactory(activity!!.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SelectVideoViewModel::class.java)
        viewModel.videoListLive.observe(viewLifecycleOwner) {
            myAdapter.submitList(it)
        }
    }

    private fun Uri.copyTo(destination: File) {
        val inChannel = context!!.contentResolver.openInputStream(this)
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