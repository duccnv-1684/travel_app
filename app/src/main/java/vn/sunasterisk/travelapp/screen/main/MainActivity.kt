package vn.sunasterisk.travelapp.screen.main

import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.sunasterisk.travelapp.R
import vn.sunasterisk.travelapp.BR
import vn.sunasterisk.travelapp.base.BaseActivity
import vn.sunasterisk.travelapp.databinding.ActivityMainBinding
import vn.sunasterisk.travelapp.utils.Logger

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {
    private val mainViewModel: MainViewModel by viewModel()

    override fun getLayoutId() = R.layout.activity_main

    override fun getBindingVariable() = BR.viewModel

    override fun updateUi(saveInstanceState: Bundle?) {
        mainViewModel.setNagator(this)
    }

    override fun getViewModel(): MainViewModel = mainViewModel

    companion object {
        val logger = Logger.getLogger(this::class.java)
    }
}
