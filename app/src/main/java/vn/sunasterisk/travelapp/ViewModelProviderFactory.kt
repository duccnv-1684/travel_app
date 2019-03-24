package vn.sunasterisk.travelapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory<V : Any>(private var viewModel: V) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel::class.java)) return viewModel as T
        throw IllegalArgumentException("Unknown class name")
    }
}
