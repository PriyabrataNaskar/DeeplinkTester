package com.priyo.deeplinktesterpro.home.ui.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.priyo.deeplinktesterpro.R

fun Context.openAppViaDeepLink(
    deeplinkInput: String,
    onSuccess: () -> Unit,
) {
    if (deeplinkInput.isNotEmpty()) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkInput))
            startActivity(intent)
            onSuccess()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.error_open_deeplink), Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(this, getString(R.string.error_invalid_deeplink), Toast.LENGTH_SHORT).show()
    }
}


fun Context.copyToClipboard(text: String) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("deeplink", text)
    clipboard.setPrimaryClip(clip)
}

fun Context.hideKeyboard() {
    val activity = getActivity()
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}