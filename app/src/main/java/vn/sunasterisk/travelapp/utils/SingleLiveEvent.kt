package vn.sunasterisk.travelapp.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) Logger.getLogger(this::class.java)
            .warn("Multiple observers registered but only one will be notified of changes")
        super.observe(owner, Observer<T> { t ->
            if (pending.compareAndSet(true, false)) observer.onChanged(t)
        })
    }

    @MainThread
    override fun setValue(value: T) {
        pending.set(true)
        super.setValue(value)
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}
