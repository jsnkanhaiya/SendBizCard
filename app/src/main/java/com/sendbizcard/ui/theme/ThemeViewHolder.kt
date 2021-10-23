package com.sendbizcard.ui.theme

import com.sendbizcard.R
import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.RowThemeItemBinding
import com.sendbizcard.models.response.theme.ListThemeItem
import com.sendbizcard.utils.loadImages

class ThemeViewHolder (private val binding: RowThemeItemBinding) : BaseViewHolder<ListThemeItem>(binding) {

    override fun loadData(receivedData: ListThemeItem, position: Int) {
        val themeImageUrl = "https://xapi.sendbusinesscard.com/storage/" + receivedData.cardImg
        binding.imgTheme.loadImages(themeImageUrl)
        binding.imgSelection.setOnClickListener {
            positionCallbackWithData?.onItemClicked(receivedData,position)
        }
        if (receivedData.isSelected){
            binding.imgSelection.setImageResource(R.drawable.ic_theme_selected)
        } else {
            binding.imgSelection.setImageResource(R.drawable.ic_theme_unselected)
        }
    }
}