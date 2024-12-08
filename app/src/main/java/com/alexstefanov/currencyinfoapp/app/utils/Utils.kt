package com.alexstefanov.currencyinfoapp.app.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun showToastMessage(context: Context, @StringRes messageRes: Int) {
    Toast.makeText(
        context, context.getString(messageRes),
        Toast.LENGTH_SHORT
    ).show()
}