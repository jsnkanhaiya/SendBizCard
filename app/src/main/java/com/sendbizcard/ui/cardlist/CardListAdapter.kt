package com.sendbizcard.ui.cardlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sendbizcard.base.BaseAdapter
import com.sendbizcard.databinding.RowSavedCardItemBinding
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.models.response.DataCardDetail
import javax.inject.Inject


class CardListAdapter @Inject constructor() : BaseAdapter<CardDetailsItem, CardListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val binding = RowSavedCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        holder.loadData(list[position],position)
    }

    override fun getItemCount(): Int {
        return listSize
    }
}