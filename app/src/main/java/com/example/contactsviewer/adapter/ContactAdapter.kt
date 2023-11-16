package com.example.contactsviewer.adapter

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contactsviewer.MainActivity
import com.example.contactsviewer.R
import com.example.contactsviewer.data.Contact


class ContactAdapter(private val context: Context) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val contactsList: ArrayList<Contact> = arrayListOf()
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
            Glide.with(context)
                .load(currentContact.image)
                .circleCrop()
                .into(holder.image)
        } else {
            Glide.with(context)
                .load(R.drawable.contact_default_icon)
                .circleCrop()
                .into(holder.image)
        }
    }

    override fun getItemCount(): Int = contactsList.size

    fun setData(contactsList: ArrayList<Contact>){
        this.contactsList.clear()
        this.contactsList.addAll(contactsList)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.contactImage)
        val name: TextView = itemView.findViewById(R.id.nameField)
        val number: TextView = itemView.findViewById(R.id.numberField)
        val secondNumber: TextView = itemView.findViewById(R.id.secondNumberField)
    }
}