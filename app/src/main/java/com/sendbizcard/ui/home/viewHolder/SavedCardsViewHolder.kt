package com.sendbizcard.ui.home.viewHolder

import android.view.View
import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.models.home.SavedCards
import kotlinx.android.synthetic.main.row_item_saved_card.view.*

class SavedCardsViewHolder(itemView: View) : BaseViewHolder<SavedCards>(itemView) {

    override fun loadData(receivedData: SavedCards, position: Int) {
        itemView.tv_name?.text = receivedData.name
        itemView.tv_designation?.text = receivedData.designation
    }
}