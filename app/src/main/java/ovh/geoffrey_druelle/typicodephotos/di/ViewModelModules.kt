package ovh.geoffrey_druelle.typicodephotos.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ovh.geoffrey_druelle.typicodephotos.ui.activity.MainViewModel
import ovh.geoffrey_druelle.typicodephotos.ui.fragment.list.ListViewModel
import ovh.geoffrey_druelle.typicodephotos.ui.fragment.splash.SplashScreenViewModel

val viewModelModules = module {
    viewModel { MainViewModel() }
    viewModel { ListViewModel(api = get(named("api"))) }
    viewModel { SplashScreenViewModel(api = get(named("api"))) }
}