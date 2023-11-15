package com.example.contactsviewer

import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsviewer.adapter.ContactAdapter
import com.example.contactsviewer.data.Contact
import com.example.contactsviewer.helper.ContactPermissionDialog.PermissionDialog.showDialogForPermissionRequest
import com.example.contactsviewer.model.ContactsReader

private const val DEBOUNCE_DELAY = 400L
private val debounceHandler = Handler(Looper.getMainLooper())

class MainActivity : AppCompatActivity() {
    private val contactPermissionRequestCode = 69

    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO Incomplete - only works if user agrees about giving permission
        // (crashes in other every other scenario)
        if (ContextCompat.checkSelfPermission(this, permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(permission.READ_CONTACTS), contactPermissionRequestCode)
        }

        val contactsData = ContactsReader.getContacts(contentResolver)

        editText = findViewById(R.id.edit_text_for_search)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ContactAdapter(contactsData)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                debounceHandler.removeCallbacksAndMessages(null)
                debounceHandler.postDelayed(
                    { filter(contactsData, p0.toString()) },
                    DEBOUNCE_DELAY
                )

            }
        })
    }

    fun filter(contactsData: ArrayList<Contact>, searchWord: String) {
        val filteredContacts: ArrayList<Contact> = ArrayList<Contact>()
        for (contact in contactsData) {
            if (contact.name.contains(searchWord) ||
                contact.number!!.contains(searchWord) ||
                contact.secondNumber!!.contains(searchWord)
            ) {
                filteredContacts.add(contact)
            }
        }
        recyclerView.adapter = ContactAdapter(filteredContacts)
    }
}