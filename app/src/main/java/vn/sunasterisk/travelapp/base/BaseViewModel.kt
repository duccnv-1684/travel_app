package vn.sunasterisk.travelapp.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import vn.sunasterisk.travelapp.data.DataManager
import vn.sunasterisk.travelapp.utils.SchedulerProvider
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(var dataManager: DataManager, private var schedulerProvider: SchedulerProvider) :
    ViewModel() {
    private lateinit var navigator: WeakReference<N>
    var compositeDisposable = CompositeDisposable()

    fun setNagator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator(): N = navigator.get()!!

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun launch(job: () -> Disposable) {
        compositeDisposable.add(job())
    }
}
