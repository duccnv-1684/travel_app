package vn.sunasterisk.travelapp.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import vn.sunasterisk.travelapp.R
import vn.sunasterisk.travelapp.utils.KeyboardUtils
import vn.sunasterisk.travelapp.widget.AppToast

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity() {
    lateinit var binding: T
    lateinit var loading: AlertDialog
    private var isCancelable = false

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    @LayoutRes
    open fun getLoadingLayoutId(): Int = -1

    open fun getThemeResId(): Int = -1

    protected abstract fun getBindingVariable(): Int

    protected abstract fun updateUi(saveInstanceState: Bundle?)

    protected abstract fun getViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        initDialog()
        updateUi(savedInstanceState)
    }

    override fun onBackPressed() {
        val fragmentInStack = supportFragmentManager.backStackEntryCount
        if (fragmentInStack == 1) finish() else super.onBackPressed()
    }

    @Throws
    open fun openFragment(resId: Int, fragmentClass: Class<*>, args: Bundle?, addToBackStack: Boolean) {
        val tag = fragmentClass.simpleName
        try {
            val isExisted = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClass as Class<Fragment>).newInstance().apply { arguments = args }
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.add(resId, fragment, tag)
                    if (addToBackStack) transaction.addToBackStack(tag)
                    transaction.commitAllowingStateLoss()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws
    open fun openFragment(
        resId: Int, fragmentClass: Class<*>, args: Bundle?,
        addBackStack: Boolean, vararg aniInt: Int
    ) {
        val tag = fragmentClass.simpleName
        try {
            val isExisted = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClass as Class<Fragment>).newInstance().apply { arguments = args }
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(aniInt[0], aniInt[1], aniInt[2], aniInt[3])

                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toast(msg: String) = AppToast.makeText(this, msg).show()

    fun tast(msg: String, duration: Int, cancelCurrent: Boolean) =
        AppToast.makeText(this, msg, duration).show(cancelCurrent)


    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.executePendingBindings()
        binding.setVariable(getBindingVariable(), getViewModel())
    }

    private fun initDialog() {
        val builder: AlertDialog.Builder = if (getThemeResId() != -1)
            AlertDialog.Builder(this, getThemeResId()) else AlertDialog.Builder(this)
        builder.setCancelable(isCancelable)
        builder.setView(if (getLoadingLayoutId() == -1) R.layout.layout_loading_diaglog_default else getLoadingLayoutId())
        loading = builder.create()
    }

    open fun showDialog() {
        runOnUiThread {
            if (!loading.isShowing) loading.show()
        }
    }

    open fun hideDialog() {
        runOnUiThread {
            if (loading.isShowing) loading.dismiss()
        }
    }

    fun setCancelableDialog(isCancelable: Boolean) {
        this.isCancelable = isCancelable
    }

    fun hideKeyboard() = KeyboardUtils.hideKeyboard(this)

    fun hideKeyboardOutSite(view: View) = KeyboardUtils.hideKeyboardWhenClickOutSide(view, this)

    fun hideKeyboardOutSiteText(view: View) = KeyboardUtils.hideKeyboardWhenClickOutsideText(view, this)

    fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
}
