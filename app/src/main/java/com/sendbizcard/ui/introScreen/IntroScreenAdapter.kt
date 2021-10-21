package com.sendbizcard.ui.introScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sendbizcard.R
import com.sendbizcard.base.BaseAdapter
import com.sendbizcard.databinding.RowIntroItemBinding
import javax.inject.Inject

class IntroScreenAdapter @Inject constructor(): BaseAdapter<Int,IntroScreenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroScreenViewHolder {
        val binding = RowIntroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroScreenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntroScreenViewHolder, position: Int) {
        holder.loadData(list[position],position)
    }

    override fun getItemCount(): Int {
        return listSize
    }


}