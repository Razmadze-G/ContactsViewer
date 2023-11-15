package com.example.contactsviewer.helper

import android.app.AlertDialog
import android.content.Context

class ContactPermissionDialog {

    companion object PermissionDialog {
        fun showDialogForPermissionRequest(context: Context) {
            AlertDialog
                .Builder(context, 1)
                .setTitle("Read Contacts Permission")
                .setMessage(
                    "This application needs read contact permission, " +
                            "Allow ContactsViewer to read contacts?"
                )
                .setPositiveButton("Go To Settings") { dialog, _ ->
                    dialog.cancel()
                }
                .setNegativeButton("Deny") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }
}