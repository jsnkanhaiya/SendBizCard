package com.sendbizcard.ui.ourServices

import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.RowOurServicesItemBinding

class OurServicesViewHolder(private val binding: RowOurServicesItemBinding) : BaseViewHolder<String>(binding) {

    override fun loadData(receivedData: String, position: Int) {
        binding.tvOurServiceItem.text = receivedData
    }
}