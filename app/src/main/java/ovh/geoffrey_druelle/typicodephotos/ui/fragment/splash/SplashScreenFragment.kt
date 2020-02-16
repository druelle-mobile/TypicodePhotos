package ovh.geoffrey_druelle.typicodephotos.ui.fragment.splash

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel

import ovh.geoffrey_druelle.typicodephotos.R
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp.Companion.appContext
import ovh.geoffrey_druelle.typicodephotos.core.BaseFragment
import ovh.geoffrey_druelle.typicodephotos.databinding.SplashScreenFragmentBinding
import ovh.geoffrey_druelle.typicodephotos.ui.activity.MainActivity

class SplashScreenFragment : BaseFragment<SplashScreenFragmentBinding>() {

    private lateinit var viewModel: SplashScreenViewModel

    private var exit: Boolean = false

    override fun getLayoutResId(): Int = R.layout.splash_screen_fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val splashLayout = root.findViewById<ConstraintLayout>(R.id.splash_layout)
        initBackgroundAnimation(splashLayout)
        initObservers()
        implementBackCallback()

        return root
    }

    private fun initBackgroundAnimation(splashLayout: ConstraintLayout) {
        val animationDrawable = splashLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    private fun initObservers() {
        viewModel.navToList.observe(this, Observer {
            b ->
            if (b) navigateToList()
        })
        viewModel.isConnected.observe(this, Observer { b ->
            if (!b) showNoConnectionSnackBar()
        })
        viewModel.noDataNoConnection.observe(this, Observer { b ->
            if (b) showNoDataNoConnectionSnackbar()
        })
    }

    private fun navigateToList() {
        val action = R.id.action_splashScreenFragment_to_listFragment
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun showNoConnectionSnackBar() {
        view?.let {
            Snackbar.make(it, getString(R.string.net_error), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.yes)) { navigateToList() }
                .setAction(getString(R.string.quit)) {
                    exit = true
                    quitApp()
                }
                .show()
        }
    }

    private fun showNoDataNoConnectionSnackbar() {
        view?.let {
            Snackbar.make(it, getString(R.string.no_data_no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.quit)) {
                    exit = true
                    quitApp()
                }
                .show()
        }
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
            Toast.makeText(appContext, getString(R.string.press_back), Toast.LENGTH_SHORT).show()
            exit = true
            Handler().postDelayed({ exit = false }, 3000)
        }
    }
}
