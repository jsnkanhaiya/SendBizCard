package com.sendbizcard.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    var itemClickCallback: ItemClickedCallback<T>? = null
    var positionCallback: ItemClickedPositionCallback? = null
    var positionCallbackWithData: ItemClickedCallBackWithPosition<T>? = null
    var itemCardClickCallback: ItemCardClickCallback<T>? = null

    abstract fun loadData(receivedData: T,position: Int)

    interface ItemClickedCallback<T> {
        fun onItemClicked(t: T)
    }

    interface ItemClickedPositionCallback {
        fun onItemOfPositionClicked(t: Int)
    }

    interface ItemClickedCallBackWithPosition<T> {
        fun onItemClicked(data: T, pos: Int)
    }

    interface ItemCardClickCallback<T>{
        fun onEditClicked(data: T, pos: Int)
        fun onPreviewClicked(data: T, pos: Int)
        fun onShareClicked(data: T, pos: Int)
        fun onDeleteClicked(data: T, pos: Int)
    }


}