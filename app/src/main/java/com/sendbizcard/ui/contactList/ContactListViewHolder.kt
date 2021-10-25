package com.sendbizcard.ui.contactList

import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.RowContactListItemBinding
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.utils.AppConstants
import com.sendbizcard.utils.loadCircleImages

class ContactListViewHolder(val binding: RowContactListItemBinding) : BaseViewHolder<CardDetailsItem>(binding) {

    override fun loadData(receivedData: CardDetailsItem, position: Int) {
        binding.tvName.text = receivedData.name
        binding.tvDesignation.text = receivedData.designation
        binding.imgProfile.loadCircleImages(AppConstants.IMAGE_BASE_URL+receivedData.userImg)

        binding.imgShare.setOnClickListener {
            itemCardClickCallback?.onShareClicked(receivedData,position)
        }
        binding.imgPreview.setOnClickListener {
            itemCardClickCallback?.onPreviewClicked(receivedData,position)
        }

    }
}