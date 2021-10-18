package com.sendbizcard.ui.ourServices

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sendbizcard.base.BaseAdapter
import com.sendbizcard.databinding.RowOurServicesItemBinding
import com.sendbizcard.models.request.addCard.ServicesItem
import javax.inject.Inject


class OurServicesAdapter @Inject constructor() : BaseAdapter<ServicesItem, OurServicesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurServicesViewHolder {
        val binding = RowOurServicesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OurServicesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OurServicesViewHolder, position: Int) {
        holder.loadData(list[position],position)
    }

    override fun getItemCount(): Int {
        return listSize
    }
}