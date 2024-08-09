package com.priyo.deeplinktesterpro.home.ui.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
    Log.e("tag", "${clip.itemCount} items copied to clipboard") //todo: remove logs
}