package vn.sunasterisk.travelapp.utils

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter

object BindingUtils {
    interface OnKeyPressedListener {
        fun onKeyPressed()
    }

    @JvmStatic
    @BindingAdapter("onKeyDown")
    fun onKeyDone(editText: EditText, action: OnKeyPressedListener) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) action.onKeyPressed()
            false
        }
    }
}
