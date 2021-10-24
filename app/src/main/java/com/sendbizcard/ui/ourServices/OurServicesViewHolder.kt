package com.sendbizcard.ui.ourServices

import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.RowOurServicesItemBinding
import com.sendbizcard.models.request.addCard.ServicesItem

class OurServicesViewHolder(private val binding: RowOurServicesItemBinding) : BaseViewHolder<ServicesItem>(binding) {

    override fun loadData(receivedData: ServicesItem, position: Int) {
        binding.tvOurServiceItem.text = receivedData.name
        binding.imgRemove.setOnClickListener {
            positionCallback?.onItemOfPositionClicked(position)
        }
    }
}