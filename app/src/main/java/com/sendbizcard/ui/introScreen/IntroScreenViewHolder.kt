package com.sendbizcard.ui.introScreen

import androidx.viewbinding.ViewBinding
import com.sendbizcard.R
import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.RowIntroItemBinding

class IntroScreenViewHolder(private val binding: RowIntroItemBinding) : BaseViewHolder<Int>(binding) {
    override fun loadData(receivedData: Int, position: Int) {
        binding.imgIntro.setImageResource(receivedData)
    }
}