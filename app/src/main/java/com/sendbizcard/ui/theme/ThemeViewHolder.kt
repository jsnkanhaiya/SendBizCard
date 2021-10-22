package com.sendbizcard.ui.theme

import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.RowThemeItemBinding
import com.sendbizcard.models.response.theme.ListThemeItem
import com.sendbizcard.utils.loadImages

class ThemeViewHolder (private val binding: RowThemeItemBinding) : BaseViewHolder<ListThemeItem>(binding) {

    override fun loadData(receivedData: ListThemeItem, position: Int) {
        val themeImageUrl = "https://xapi.sendbusinesscard.com/storage/" + receivedData.cardImg
        binding.imgTheme.loadImages(themeImageUrl)
    }
}