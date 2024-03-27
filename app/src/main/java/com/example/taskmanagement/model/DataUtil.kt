package com.example.taskmanagement.model

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView


object DataUtil {

    fun isEmptyText(view: View?): Boolean {
        return if (view == null)
            true
        else
            getTextValue(view).isEmpty()
    }

    fun getTextValue(view: View): String {
        return (view as? EditText)?.text?.toString()?.trim { it <= ' ' }
            ?: ((view as? TextView)?.text?.toString()?.trim { it <= ' ' }
                ?: "")
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }
}