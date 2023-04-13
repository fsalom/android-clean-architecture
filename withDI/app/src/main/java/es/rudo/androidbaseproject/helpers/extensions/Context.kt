package es.rudo.androidbaseproject.helpers.extensions

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import es.rudo.androidbaseproject.R
import es.rudo.androidbaseproject.databinding.DialogSimpleLayoutBinding
import es.rudo.androidbaseproject.helpers.Constants

val Context.isNetworkAvailable: Boolean
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.toast(@StringRes resId: Int) {
    toast(getString(resId))
}

fun Context.showSimpleDialog(
    data: Map<String, Any>,
    positive: (() -> Unit)? = null,
    negative: (() -> Unit)? = null
) {
    val dialogBinding: DialogSimpleLayoutBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_simple_layout,
            null,
            false
        )

    with(dialogBinding) {
        with(data) {
            if (containsKey(Constants.DIALOG_KEY_TITLE)) {
                textTitle.text = data[Constants.DIALOG_KEY_TITLE] as? String
            }
            if (containsKey(Constants.DIALOG_KEY_DESCRIPTION)) {
                textDialog.text = data[Constants.DIALOG_KEY_DESCRIPTION] as? String
            }
            if (containsKey(Constants.DIALOG_KEY_ACCEPT)) {
                buttonAccept.visibility = View.VISIBLE
                buttonAccept.text = data[Constants.DIALOG_KEY_ACCEPT] as? String
            }
            if (containsKey(Constants.DIALOG_KEY_CANCEL)) {
                buttonCancel.visibility = View.VISIBLE
                buttonCancel.text = data[Constants.DIALOG_KEY_CANCEL] as? String
            }
        }
    }

    Dialog(this).apply {
        setCancelable(!data.containsKey(Constants.DIALOG_KEY_NON_CANCELABLE))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(dialogBinding.root)
        window?.setBackgroundDrawableResource(R.drawable.background_dialog)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogBinding.buttonAccept.setOnClickListener {
            dismiss()
            positive?.invoke()
        }
        dialogBinding.buttonCancel.setOnClickListener {
            dismiss()
            negative?.invoke()
        }
    }.show()
}