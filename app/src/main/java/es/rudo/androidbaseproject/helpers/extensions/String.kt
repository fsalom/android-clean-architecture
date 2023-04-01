package es.rudo.androidbaseproject.helpers.extensions

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import es.rudo.androidbaseproject.helpers.Constants

fun String.isValidName() = matches(Constants.NAME_PATTERN.toRegex())

fun String.isValidEmail() = matches(Constants.EMAIL_PATTERN.toRegex())

fun String.isValidUsername() = matches(Constants.USERNAME_PATTERN.toRegex())

fun String.isValidPassword() = matches(Constants.PASSWORD_PATTERN.toRegex())

fun String?.fromHtml(): Spanned? {
    return when {
        this == null -> SpannableString("")
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        }
        else -> Html.fromHtml(this)
    }
}