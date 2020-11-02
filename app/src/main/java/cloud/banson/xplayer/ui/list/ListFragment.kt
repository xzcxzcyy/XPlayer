package cloud.banson.xplayer.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cloud.banson.xplayer.R
import cloud.banson.xplayer.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
        private const val TAG = "LISTFRAGMENT"
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var myAdapter: ExistListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        initView(inflater, container)
        initListener()
        return binding.root
    }

    private fun initViewModel() {
        ListViewModelFactory(context!!.applicationContext).also {
            viewModel = ViewModelProvider(this, it).get(ListViewModel::class.java)
        }
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.list_fragment, container, false
        )
    }

    private fun initListener() {
        binding.startImage.setOnClickListener {
            this.findNavController()
                .navigate(ListFragmentDirections.actionListFragmentToSelectVideoFragment())
        }
        myAdapter = ExistListAdapter {
            Log.d(TAG, "initListener: navigating to play view")
        }
        binding.resourceRecycler.adapter = myAdapter
        binding.resourceRecycler.layoutManager = LinearLayoutManager(context)

        viewModel.resourceList.observe(viewLifecycleOwner) {
            myAdapter.submitList(it)
        }
    }
}