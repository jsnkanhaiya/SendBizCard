package com.sendbizcard.ui.introScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sendbizcard.R
import com.sendbizcard.databinding.RowIntroItemBinding
import javax.inject.Inject

class IntroScreenAdapter @Inject constructor(): RecyclerView.Adapter<IntroScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowIntroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 4
    }

    class ViewHolder(private val binding: RowIntroItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            when(position) {
                0 -> {
                    binding.imgIntro.setImageResource(R.drawable.theme1)
                }
                1 -> {
                    binding.imgIntro.setImageResource(R.drawable.theme2)
                }
                2 -> {
                    binding.imgIntro.setImageResource(R.drawable.theme3)
                }
                3 -> {
                    binding.imgIntro.setImageResource(R.drawable.theme4)
                }
            }
        }
    }
}