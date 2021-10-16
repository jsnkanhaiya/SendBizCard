package com.sendbizcard.ui.ourServices

import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.RowItemOurServicesBinding

class OurServicesViewHolder(private val binding: RowItemOurServicesBinding) : BaseViewHolder<String>(binding) {

    override fun loadData(receivedData: String, position: Int) {
        binding.tvOurServiceItem.text = receivedData
    }
}