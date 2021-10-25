package com.sendbizcard.ui.contactList

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sendbizcard.base.BaseAdapter
import com.sendbizcard.databinding.RowContactListItemBinding
import com.sendbizcard.models.response.CardDetailsItem
import javax.inject.Inject

class ContactListAdapter @Inject constructor() : BaseAdapter<CardDetailsItem,ContactListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val binding = RowContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.loadData(list[position],position)
        holder.itemCardClickCallback = cardClickListener

    }

    override fun getItemCount(): Int {
        return listSize
    }
}