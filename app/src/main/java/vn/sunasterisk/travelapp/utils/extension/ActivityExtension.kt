package vn.sunasterisk.travelapp.utils.extension

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle

fun Activity.startActivity(clazz: Class<*>) {
    val intent = Intent(this, clazz)
    startActivity(intent)
}

fun Activity.startActivity(clazz: Class<*>, bundle: Bundle) {
    val intent = Intent(this, clazz)
    intent.putExtras(bundle)
    startActivity(intent)
}

fun Activity.startActivityForResult(clazz: Class<*>, requestCode: Int) {
    val intent = Intent(this, clazz)
    startActivityForResult(intent, requestCode)
}

fun Activity.startActivityForResult(clazz: Class<*>, requestCode: Int, bundle: Bundle) {
    val intent = Intent(this, clazz)
    intent.putExtras(bundle)
    startActivityForResult(intent, requestCode)
}

fun Activity.startActivityWithFlags(clazz: Class<*>, vararg flags: Int) {
    val intent = Intent(this, clazz)
    flags.forEach { intent.addFlags(it) }
    startActivity(intent)
}

fun Activity.isConnectedInternet(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return if (connectivityManager != null) {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo != null && networkInfo.isConnected
    } else {
        false
    }
}

fun Service.startActivity(clazz: Class<*>, bundle: Bundle) {
    val intent = Intent(this, clazz)
    intent.putExtras(bundle)
    startActivity(intent)
}

fun Service.startActivity(clazz: Class<*>) {
    val intent = Intent(this, clazz)
    startActivity(intent)
}
