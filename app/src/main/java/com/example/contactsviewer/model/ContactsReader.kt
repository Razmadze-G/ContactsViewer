package com.example.contactsviewer.model

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract.Contacts
import com.example.contactsviewer.data.Contact

class ContactsReader {
    companion object GetContactsData {
        @SuppressLint("Range")
        fun getContacts(resolver: ContentResolver): ArrayList<Contact> {
            val contactList = ArrayList<Contact>()

            val cursor = resolver.query(
                Contacts.CONTENT_URI,
                null, null, null, null
            )!!

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(Contacts._ID))
                    val name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME))
                    val hasNumber =
                        cursor.getString(cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER)).toInt()
                    val numbers = ContactsReaderHelper.getNumbers(id, hasNumber, resolver)
                    val image = ContactsReaderHelper.getPhoto(id, resolver)

                    contactList.add(Contact(id, name, numbers.first, numbers.second, image))
                }
            }
            cursor.close()
            return contactList
        }
    }
}