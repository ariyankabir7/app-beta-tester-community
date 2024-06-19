package com.test.xyz

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import app.test.xyz.R
import com.google.android.material.snackbar.Snackbar


object Companion {
    const val siteUrl = "https://webnexa.site/webnexa_apps/BetaTester/"
    const val APP_VERSION = 1
    const val APP_ID = 5
    const val IRONSOURCE_APP_ID = "1e8cb97ad"
    const val META_INTER_ID = "810128631010176_810137797675926"
    const val META_NATIVE_ID = "1499350520703193_1499356817369230"


}

object Utils {
    private var alertDialog: AlertDialog? = null
    private var alertDialog2: AlertDialog? = null
    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    fun hideKeyboard(context: Context, editText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun copyToClipboard(context: Context, text: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Copied Text", text)
        Toast.makeText(context, "Copied !", Toast.LENGTH_SHORT).show()
        clipboardManager.setPrimaryClip(clipData)
    }

    fun getCopiedText(context: Context): String {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboardManager.hasPrimaryClip()) {
            val clipData = clipboardManager.primaryClip
            val item = clipData?.getItemAt(0)
            return item?.text?.toString() ?: ""
        }
        return ""
    }


    fun showCustomPopup(
        context: Context,
        layout: Int,
        viewId: Int,
        listener: View.OnClickListener
    ) {
        val customLayout = LayoutInflater.from(context).inflate(layout, null)
        val builder = AlertDialog.Builder(context, R.style.TransparentDialogTheme)
        builder.setView(customLayout)
        val alertDialog = builder.create()
        builder.setCancelable(true)

        customLayout.findViewById<View>(viewId).setOnClickListener {
            listener.onClick(it)
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    fun showLoadingPopUp(context: Context) {
        val customLayout = LayoutInflater.from(context).inflate(R.layout.popup_loading, null)
        val builder = AlertDialog.Builder(context, R.style.TransparentDialogTheme)
        builder.setView(customLayout)
        alertDialog = builder.create()
        alertDialog?.setCancelable(false)

        alertDialog?.show()
    }

    fun dismissLoadingPopUp() {
        alertDialog?.dismiss()
    }

    fun showCheckingPopUp(context: Context) {
        val customLayout = LayoutInflater.from(context).inflate(R.layout.popup_loading, null)
        val builder = AlertDialog.Builder(context, R.style.TransparentDialogTheme)
        builder.setView(customLayout)
        alertDialog2 = builder.create()
        alertDialog2?.setCancelable(false)

        alertDialog2?.show()
    }

    fun dismissCheckingPopUp() {
        alertDialog2?.dismiss()
    }

    fun showSnackBar(context: Context, view: View, text: String, bgColor: Int) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAction("ok") {
                // Responds to click on the action
            }
            .setBackgroundTint(bgColor)
            .setActionTextColor(context.resources.getColor(R.color.white))
            .show()
    }
}

object TinyDB {
    private const val PREF_NAME = "EB"

    // Method to save a string value in SharedPreferences
    fun saveString(context: Context, key: String?, value: String?) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    // Method to retrieve a string value from SharedPreferences
    fun getString(context: Context, key: String?, defaultValue: String?): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue)
    }

    // Method to save an integer value in SharedPreferences
    fun saveInt(context: Context, key: String?, value: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    // Method to retrieve an integer value from SharedPreferences
    fun getInt(context: Context, key: String?, defaultValue: Int): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getList(context: Context, key: String?): MutableList<String> {
        val old = getString(context, key, "")!!
        return old.split(",").toMutableList()

    }

    fun addItemList(context: Context, key: String?, value: String) {
        val old = getString(context, key, "")!!
        val oldArr = old.split(",").toMutableList()
        oldArr.add(value)
        val new = oldArr.joinToString(",")
        saveString(context, key, new)
    }

    // Method to save a boolean value in SharedPreferences
    fun saveBoolean(context: Context, key: String?, value: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    // Method to retrieve a boolean value from SharedPreferences
    fun getBoolean(context: Context, key: String?, defaultValue: Boolean): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // Method to clear all SharedPreferences data
    fun clearPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}