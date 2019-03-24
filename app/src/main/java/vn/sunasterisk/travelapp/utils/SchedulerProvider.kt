package vn.sunasterisk.travelapp.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider {

    private fun getMainThreadScheduler() = AndroidSchedulers.mainThread()

    private fun getIoThreadScheduler() = Schedulers.io()

    fun <T> ioToMainObservableScheduler(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        upstream.subscribeOn(getIoThreadScheduler())
            .observeOn(getMainThreadScheduler())
    }

    fun <T> ioToMainSingleScheduler(): SingleTransformer<T, T> = SingleTransformer { upstream ->
        upstream.subscribeOn(getIoThreadScheduler())
            .observeOn(getMainThreadScheduler())
    }

    fun <T> ioToMainFlowableScheduler(): FlowableTransformer<T, T> = FlowableTransformer { upstream ->
        upstream.subscribeOn(getIoThreadScheduler())
            .observeOn(getMainThreadScheduler())
    }

    fun <T> ioToMainMaybeScheduler(): MaybeTransformer<T, T> = MaybeTransformer { upstream ->
        upstream.subscribeOn(getIoThreadScheduler())
            .observeOn(getMainThreadScheduler())
    }

    fun ioToMainCompletableScheduler(): CompletableTransformer = CompletableTransformer { upstream ->
        upstream.subscribeOn(getIoThreadScheduler())
            .observeOn(getMainThreadScheduler())
    }

}
