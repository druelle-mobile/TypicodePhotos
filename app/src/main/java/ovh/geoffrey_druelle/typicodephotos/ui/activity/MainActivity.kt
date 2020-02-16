package ovh.geoffrey_druelle.typicodephotos.ui.activity

import android.os.Bundle
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.typicodephotos.R
import ovh.geoffrey_druelle.typicodephotos.core.BaseActivity
import ovh.geoffrey_druelle.typicodephotos.databinding.ActivityMainBinding
import ovh.geoffrey_druelle.typicodephotos.utils.ext.hide
import ovh.geoffrey_druelle.typicodephotos.utils.ext.show

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        lateinit var instance: MainActivity
    }

    override fun getLayoutResId(): Int = R.layout.activity_main
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.toolbar)
        setUpToolBar()
    }

    private fun setUpToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreenFragment -> binding.toolbar.hide()
                R.id.listFragment -> {
                    binding.toolbar.show()
                }
                R.id.detailsFragment -> {
                    binding.toolbar.show()
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }
}
