package com.example.contactsviewer.model

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.Contacts

class ContactsReaderHelper {
    companion object Helper {
        fun getPhoto(contactId: String, resolver: ContentResolver): ByteArray? {
            val contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId.toLong())
            val photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY)
            val cursor = resolver.query(
                photoUri, arrayOf(Contacts.Photo.PHOTO),
                Contacts.Photo.CONTACT_ID + " = " + contactId, null, null
            ) ?: return null

            if (cursor.moveToFirst()) {
                val data = cursor.getBlob(0)
                if (data != null) {
                    return data
                }
            }
            cursor.close()
            return null
        }

        @SuppressLint("Recycle", "Range")
        fun getNumbers(contactId: String, hasNumber: Int, resolver: ContentResolver)
                : Pair<String, String> {
            if (hasNumber <= 0) return Pair("", "")
            val cursor = resolver.query(
                Phone.CONTENT_URI, null,
                Phone.CONTACT_ID + " = " + contactId, null, null
            )

            if (cursor != null && cursor.moveToFirst()) {
                val firstNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER))
                val normalizedFirstNumber = phoneNumberNormalizer(firstNumber)
                var normalizedSecondNumber = ""
                if (cursor.moveToNext()) {
                    val secondNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER))
                    normalizedSecondNumber = phoneNumberNormalizer(secondNumber)
                    if(normalizedFirstNumber == normalizedSecondNumber){
                        normalizedSecondNumber = ""
                    }
                }

                cursor.close()

                if(normalizedFirstNumber == normalizedSecondNumber)
                    normalizedSecondNumber = ""

                return Pair(normalizedFirstNumber, normalizedSecondNumber)
            }
            return Pair("", "")
        }

        private fun phoneNumberNormalizer(number: String): String {
            var modifiedNumber = number.replace("(", "")
            modifiedNumber = modifiedNumber.replace(")", "")
            modifiedNumber = modifiedNumber.replace("-", "")
            modifiedNumber = modifiedNumber.replace(" ", "")
            if(modifiedNumber.contains("+995")){
                modifiedNumber = modifiedNumber.substring(4)
            }
            return modifiedNumber
        }
    }
}