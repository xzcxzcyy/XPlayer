package cloud.banson.xplayer.ui.select

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import cloud.banson.xplayer.R
import cloud.banson.xplayer.data.VideoInfo
import cloud.banson.xplayer.data.VideoDatabase
import cloud.banson.xplayer.databinding.SelectVideoFragmentBinding
import cloud.banson.xplayer.util.copyTo
import kotlinx.coroutines.*
import java.io.File

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
            val destination = File(context!!.filesDir, it.name)
            it.uri.copyTo(destination)
            /*val newItem = VideoInfo(destination.toUri(), it.name, it.size, it.thumbNail)
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    videoDatabaseDao.insert(newItem)
                }
            }*/
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
}