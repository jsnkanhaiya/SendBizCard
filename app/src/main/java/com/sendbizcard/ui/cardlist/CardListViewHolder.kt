package com.sendbizcard.ui.cardlist

import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.RowOurServicesItemBinding
import com.sendbizcard.databinding.RowSavedCardItemBinding
import com.sendbizcard.models.request.addCard.ServicesItem
import com.sendbizcard.models.response.CardDetailsItem

class CardListViewHolder(private val binding: RowSavedCardItemBinding) : BaseViewHolder<CardDetailsItem>(binding) {

    override fun loadData(receivedData: CardDetailsItem, position: Int) {
        binding.tvName.text = receivedData.name
        binding.tvDesignation.text = receivedData.designation
        //binding.tvName.text = receivedData.name
    }
}