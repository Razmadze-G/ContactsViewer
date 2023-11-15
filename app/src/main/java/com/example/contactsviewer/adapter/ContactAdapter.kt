package com.example.contactsviewer.adapter

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsviewer.R
import com.example.contactsviewer.data.Contact


class ContactAdapter(private val contactsList: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_item, parent, false
        )
        return ContactViewHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contactsList[position]
        holder.name.text = currentContact.name
        holder.number.text = currentContact.number
        holder.secondNumber.text = currentContact.secondNumber
        if(currentContact.image?.isNotEmpty() == true){
            val image = BitmapFactory.decodeByteArray(currentContact.image, 0, currentContact.image.size)
            holder.image.setImageBitmap(image)
        }
    }

    override fun getItemCount(): Int = contactsList.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.contactImage)
        val name: TextView = itemView.findViewById(R.id.nameField)
        val number: TextView = itemView.findViewById(R.id.numberField)
        val secondNumber: TextView = itemView.findViewById(R.id.secondNumberField)
    }
}