package vn.sunasterisk.travelapp

import android.app.Application
import org.koin.core.context.startKoin
import vn.sunasterisk.travelapp.di.mvvmModule
import vn.sunasterisk.travelapp.utils.Logger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.init(true)
        startKoin { modules(mvvmModule) }
    }
}
