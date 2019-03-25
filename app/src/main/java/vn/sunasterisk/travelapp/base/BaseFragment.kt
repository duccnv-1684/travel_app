package vn.sunasterisk.travelapp.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import vn.sunasterisk.travelapp.widget.AppToast

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(),
    ViewTreeObserver.OnGlobalLayoutListener {
    private var rootView: View? = null
    private var binding: T? = null

    protected abstract fun getViewModel(): V

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getBindingVariable(): Int

    protected abstract fun updateUi(saveInstanceState: Bundle?)

    override fun onGlobalLayout() {
        rootView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = getLayoutId()
        if (rootView != null) {
            val parent = rootView!!.parent as ViewGroup
            parent.removeView(rootView)
        } else {
            try {
                binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
                rootView = if (binding != null) binding!!.root
                else inflater.inflate(layoutId, container, false)
                rootView!!.viewTreeObserver.addOnGlobalLayoutListener(this)
                binding!!.setVariable(getBindingVariable(), view)
                binding!!.executePendingBindings()
                updateUi(savedInstanceState)
            } catch (e: InflateException) {
                e.printStackTrace()
            }

        }
        return rootView
    }

    @Throws
    open fun openFragment(resId: Int, fragmentClass: Class<*>, args: Bundle?, addToBackStack: Boolean) {
        val tag = fragmentClass.simpleName
        try {
            val isExisted = childFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClass as Class<Fragment>).newInstance().apply { arguments = args }
                    val transaction = childFragmentManager.beginTransaction()
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
            val isExisted = childFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClass as Class<Fragment>).newInstance().apply { arguments = args }
                    val transaction = childFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(aniInt[0], aniInt[1], aniInt[2], aniInt[3])
                    transaction.add(resId, fragment, tag)
                    if (addBackStack) transaction.addToBackStack(tag)
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

    fun toast(msg: String) {
        context?.let {
            AppToast.makeText(it, msg).show()
        }
    }

    fun toast(msg: String, duration: Int, cancelCurrent: Boolean) {
        context?.let {
            AppToast.makeText(it, msg, duration).show(cancelCurrent)
        }
    }

    fun showDialog() {
        activity?.let {
            if (it is BaseActivity<*, *>) {
                it.showDialog()
            }
        }
    }

    fun hideDialog() {
        activity?.let {
            if (it is BaseActivity<*, *>) {
                it.hideDialog()
            }
        }
    }

    fun hideKeyboard() {
        activity?.let {
            if (it is BaseActivity<*, *>) {
                it.hideKeyboard()
            }
        }
    }

    fun hideKeyboardOutSide(view: View) {
        activity?.let {
            if (it is BaseActivity<*, *>) {
                it.hideKeyboardOutSite(view)
            }
        }
    }

    fun hideKeyboardOutSideText(view: View) {
        activity?.let {
            if (it is BaseActivity<*, *>) {
                it.hideKeyboardOutSiteText(view)
            }
        }
    }

    fun onBackPressed() {
        activity?.onBackPressed()
    }

    fun finish() {
        activity?.finish()
    }
}
