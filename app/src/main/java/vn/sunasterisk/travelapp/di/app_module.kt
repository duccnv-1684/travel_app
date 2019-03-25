package vn.sunasterisk.travelapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.sunasterisk.travelapp.data.AppDataManager
import vn.sunasterisk.travelapp.data.DataManager
import vn.sunasterisk.travelapp.screen.main.MainViewModel
import vn.sunasterisk.travelapp.utils.SchedulerProvider

val appModule: Module = module {
    single<DataManager> { AppDataManager() }
    single { SchedulerProvider() }
}

val viewModule = module {
    viewModel { MainViewModel(get(), get()) }
}
val mvvmModule = listOf(appModule, viewModule)
