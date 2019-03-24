package vn.sunasterisk.travelapp.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import vn.sunasterisk.travelapp.widget.AppToast

abstract class BaseDialog<T : ViewDataBinding, V : ViewModel> : DialogFragment() {
    lateinit var binding: T

    abstract fun getBindingVariable(): Int

    protected abstract fun getViewModel(): V

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun updateUi(saveInstanceState: Bundle?)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val dialog = Dialog(root.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.setVariable(getBindingVariable(), getViewModel())
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi(savedInstanceState)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val transaction = fragmentManager!!.beginTransaction()
        val previousFragment: Fragment? = fragmentManager!!.findFragmentByTag(tag)
        previousFragment?.let {
            transaction.remove(it)
            transaction.addToBackStack(null)
            super.show(transaction, tag)
        }
    }

    @Throws
    open fun dismissDialog(fragmentManager: FragmentManager, tag: String) {
        val fragment: Fragment? = fragmentManager.findFragmentByTag(tag)
        fragment?.let {
            fragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .remove(it)
                .commitAllowingStateLoss()
        }
    }

    @Throws
    open fun dismissDialog(fragmentManager: FragmentManager, tag: String, animIn: Int, animOut: Int) {
        val fragment: Fragment? = fragmentManager.findFragmentByTag(tag)
        fragment?.let {
            fragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(animIn, animOut)
                .remove(it)
                .commitAllowingStateLoss()
        }
    }

    fun toast(msg: String) {
        context?.let {
            AppToast.makeText(it, msg).show()
        }
    }

    fun showDialog() {
        (activity as? BaseActivity<*, *>)?.showDialog()
    }

    fun hideDialog() {
        (activity as? BaseActivity<*, *>)?.hideDialog()
    }
}
