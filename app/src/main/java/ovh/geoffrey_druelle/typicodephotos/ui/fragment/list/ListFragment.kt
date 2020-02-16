package ovh.geoffrey_druelle.typicodephotos.ui.fragment.list

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.typicodephotos.R
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp
import ovh.geoffrey_druelle.typicodephotos.core.BaseFragment
import ovh.geoffrey_druelle.typicodephotos.databinding.ListFragmentBinding
import ovh.geoffrey_druelle.typicodephotos.ui.activity.MainActivity

class ListFragment : BaseFragment<ListFragmentBinding>() {

    override fun getLayoutResId(): Int = R.layout.list_fragment

    private lateinit var viewModel: ListViewModel
    private var exit: Boolean = false
    private var swipeCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initListObs()
        setSwipeRefreshLayout()
        implementBackCallback()

        return root
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            swipeCount += 1
            if (swipeCount > 0)
                viewModel.requestPhotoData()
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initListObs() {
        viewModel.photoList.observe(this, Observer {

        })
    }

    private fun implementBackCallback() {
        val onBackPressedCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    quitApp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun quitApp() {
        if (exit) MainActivity.instance.finish()
        else {
            Toast.makeText(
                TypicodePhotosApp.appContext,
                getString(R.string.press_back),
                Toast.LENGTH_SHORT
            ).show()
            exit = true
            Handler().postDelayed({ exit = false }, 3000)
        }
    }
}
