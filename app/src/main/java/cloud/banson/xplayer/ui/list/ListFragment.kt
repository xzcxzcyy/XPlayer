package cloud.banson.xplayer.ui.list

import android.database.DatabaseUtils
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cloud.banson.xplayer.R
import cloud.banson.xplayer.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding

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
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.list_fragment, container, false
        )
    }

    private fun initListener() {

    }
}