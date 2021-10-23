package com.sendbizcard.ui.theme

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sendbizcard.base.BaseAdapter
import com.sendbizcard.databinding.RowThemeItemBinding
import com.sendbizcard.models.response.theme.ListThemeItem
import javax.inject.Inject

class ThemeAdapter @Inject constructor() : BaseAdapter<ListThemeItem,ThemeViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val binding = RowThemeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ThemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.loadData(list[position],position)
        holder.positionCallbackWithData = clickListenerWithPosition
    }

    override fun getItemCount(): Int {
        return listSize
    }
}