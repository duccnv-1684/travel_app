@file:Suppress("UNCHECKED_CAST")

package vn.sunasterisk.travelapp.utils.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Fragment.withArguments(vararg arguments: Pair<String, Parcelable>): Fragment {
    val bundle = Bundle()
    arguments.forEach { bundle.putParcelable(it.first, it.second) }
    this.arguments = bundle
    return this
}

fun <T : Any> FragmentActivity.agument(key: String) = lazy { intent.extras!![key] as T }

fun <T : Any> FragmentActivity.argument(key: String, defaultValue: T? = null) = lazy {
    intent.extras!![key] as? T ?: defaultValue
}

fun <T : Any> Fragment.argument(key: String) = lazy { arguments?.get(key) as T }

fun <T : Any> Fragment.argument(key: String, defaultValue: T) = lazy {
    arguments?.get(key)  as? T ?: defaultValue
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply { arguments = Bundle().apply(argsBuilder) }
