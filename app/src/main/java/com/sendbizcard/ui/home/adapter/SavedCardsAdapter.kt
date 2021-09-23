package com.sendbizcard.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sendbizcard.R
import com.sendbizcard.base.BaseAdapter
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.ui.home.viewHolder.SavedCardsViewHolder

class SavedCardsAdapter : BaseAdapter<SavedCards,SavedCardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCardsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_saved_card, parent, false)
        return SavedCardsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SavedCardsViewHolder, position: Int) {
        holder.loadData(list[position],position)
    }

    override fun getItemCount(): Int {
        return listSize
    }
}