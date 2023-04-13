package es.rudo.androidbaseproject.helpers

object Constants {
    // REGEXP PATTERNS
    const val USERNAME_PATTERN = "^[\\w.@+-]+\\Z"
    const val NAME_PATTERN = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\h]+$"
    const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+"
    const val PASSWORD_PATTERN = "((?=.*\\d)(?=.*[A-ZÑÁÉÍÓÚ]).{8,})"

    // DATE AND TIME PATTERNS
    const val SHORT_DATE_PATTERN = "dd MMM"
    const val MEDIUM_DATE_PATTERN = "dd/MM/yyyy"
    const val SERVER_DATE_PATTERN = "yyyy-MM-dd"
    const val SERVER_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    const val SERVER_USER_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
    const val IMAGE_DATETIME_PATTERN = "yyyyMMdd_HHmmss"

    const val HOUR_PATTERN = "HH:mm"
    const val LONG_HOUR_PATTERN = "HH:mm:ss"
    const val LONG_DATETIME_PATTERN = "dd/MM/yyyy • hh:mm aa"

    // WEBSERVICES CODES
    const val SERVER_SUCCESS_CODE = 200
    const val SERVER_CREATED_CODE = 201
    const val SERVER_NOCONTENT_CODE = 204
    const val SERVER_BADREQUEST_CODE = 400
    const val SERVER_UNAUTHORIZED_CODE = 401
    const val SERVER_FORBIDDEN_CODE = 403
    const val SERVER_NOTFOUND_CODE = 404
    const val SERVER_NOTACCEPTABLE_CODE = 406
    const val SERVER_TIMEOUT_CODE = 408
    const val SERVER_CONFLICT_CODE = 409
    const val SERVER_INTERNALSERVER_CODE = 500

    const val RETRY_TIME_IN_MILLIS = 10000L

    //DIALOG CODES
    const val DIALOG_KEY_TITLE = "title"
    const val DIALOG_KEY_DESCRIPTION = "description"
    const val DIALOG_KEY_ACCEPT = "accept"
    const val DIALOG_KEY_CANCEL = "cancel"
    const val DIALOG_KEY_NON_CANCELABLE = "non_cancelable"
}