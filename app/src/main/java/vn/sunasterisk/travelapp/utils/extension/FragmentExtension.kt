package vn.sunasterisk.travelapp.utils.extension

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.startActivity(clazz: Class<*>) {
    val intent = Intent(context, clazz)
    startActivity(intent)
}

fun Fragment.startActivity(clazz: Class<*>, bundle: Bundle) {
    val intent = Intent(context, clazz)
    intent.putExtras(bundle)
    startActivity(intent)
}

fun Fragment.startActivityForResult(clazz: Class<*>, requestCode: Int) {
    val intent = Intent(context, clazz)
    activity?.startActivityForResult(intent, requestCode)
}

fun Fragment.startActivityForResult(clazz: Class<*>, requestCode: Int, bundle: Bundle) {
    val intent = Intent(context, clazz)
    intent.putExtras(bundle)
    activity?.startActivityForResult(intent, requestCode)
}

fun Fragment.startActivityWithFlags(clazz: Class<*>, vararg flags: Int) {
    val intent = Intent(context, clazz)
    flags.forEach { intent.addFlags(it) }
    activity?.startActivity(intent)
}

fun Fragment.isConnectedInternet(): Boolean {
    val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return if (connectivityManager != null) {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo != null && networkInfo.isConnected
    } else {
        false
    }
}
