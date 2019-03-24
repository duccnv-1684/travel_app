package vn.sunasterisk.travelapp.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.LayoutRes
import java.lang.ref.WeakReference

class AppToast private constructor(private val internalToast: Toast?) {
    init {
        if (internalToast == null) throw NullPointerException("AppToast request non-null parameter")
    }

    fun cancel() {
        internalToast!!.cancel()
    }

    @JvmOverloads
    fun show(cancelCurrent: Boolean = true) {
        if (cancelCurrent) {
            val cachedGlobalToast = globalToast
            cachedGlobalToast?.cancel()
        }
        globalToast = this
        internalToast!!.show()
    }

    companion object {
        @Volatile
        private var weakToast: WeakReference<AppToast>? = null
        private var globalToast: AppToast?
            get() = weakToast?.get()
            set(globalAppToast) {
                AppToast.weakToast = WeakReference<AppToast>(globalAppToast)
            }

        @SuppressLint("ShowToast")
        fun makeText(context: Context, text: CharSequence, duration: Int): AppToast {
            return AppToast(Toast.makeText(context, text, duration))
        }

        @SuppressLint("ShowToast")
        @Throws(Resources.NotFoundException::class)
        fun makeText(context: Context, resId: Int, duration: Int): AppToast {
            return AppToast(Toast.makeText(context, resId, duration))
        }

        @SuppressLint("ShowToast")
        fun makeText(context: Context, text: CharSequence): AppToast {
            return AppToast(Toast.makeText(context, text, Toast.LENGTH_SHORT))
        }

        @SuppressLint("ShowToast")
        @Throws(Resources.NotFoundException::class)
        fun makeText(context: Context, resId: Int): AppToast {
            return AppToast(Toast.makeText(context, resId, Toast.LENGTH_SHORT))
        }

        @SuppressLint("ShowToast")
        @Throws(Resources.NotFoundException::class)
        fun makeCustom(context: Context, @LayoutRes layoutResId: Int): AppToast {
            val toast = Toast(context)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.duration = Toast.LENGTH_SHORT
            val inflater = LayoutInflater.from(context)
            toast.view = inflater.inflate(layoutResId, null)

            return AppToast(toast)
        }

        fun showText(context: Context, text: CharSequence, duration: Int) {
            AppToast.makeText(context, text, duration).show()
        }

        @Throws(Resources.NotFoundException::class)
        fun showText(context: Context, resId: Int, duration: Int) {
            AppToast.makeText(context, resId, duration).show()
        }

        fun showText(context: Context, text: CharSequence) {
            AppToast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        @Throws(Resources.NotFoundException::class)
        fun showText(context: Context, resId: Int) {
            AppToast.makeText(context, resId, Toast.LENGTH_SHORT).show()
        }

        @Throws(Resources.NotFoundException::class)
        fun showCustom(context: Context, @LayoutRes layoutResId: Int) {
            AppToast.makeCustom(context, layoutResId).show()
        }
    }
}
