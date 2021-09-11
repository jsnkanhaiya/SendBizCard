package com.sendbizcard.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var itemClickCallback: ItemClickedCallback<T>? = null
    var positionCallback: ItemClickedPositionCallback? = null
    var positionCallbackWithData: ItemClickedCallBackWithPosition<T>? = null

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


}